package dao;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;
import model.Notification;
import model.ParentAlumn;
import model.Strike;
import model.Suspension;

public class StrikeDao extends Dao {

    private static final String TABLE_COLUMNS[] = {"IDStrike","descriptionStrike","dateStrike",
            "IDAlumn"};

    private static StrikeDao instance = null;
    private static String TABLE_NAME = "Strike";

    private StrikeDao(Context context) {
        StrikeDao.database = new DatabaseHelper(context);
    }

    public static StrikeDao getInstance(Context context) {

        if(StrikeDao.instance != null) {

        } else {
            StrikeDao.instance = new StrikeDao(context);
        }

        return StrikeDao.instance;
    }

    public boolean syncronStrike (List<Strike> strikeList) {

        boolean valid = true;

        for(int aux = 0;aux<strikeList.size();aux ++) {

            Strike strike = new Strike();

            strike.setIdStrike(strikeList.get(aux).getIdStrike());
            strike.setDescription_strike(strikeList.get(aux).getDescription_strike());
            strike.setDate_strike(strikeList.get(aux).getDate_strike());
            strike.setIdAlumn(strikeList.get(aux).getIdAlumn());

            if(existsStrike(strike) == true ) {
                if(verifEqualsStrikes(strike) == false) {
                    updateStrike(strike);
                    //getParentAlumn(strike);
                    //sendMessage(strike);
                    valid = true;
                } else {
                    valid = false;
                }
            } else {
                insertStrike(strike);
                //getParentAlumn(strike);
                valid = true;
            }
        }

        return valid;
    }

    public boolean insertStrike (Strike strike) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], strike.getIdStrike());
        values.put(TABLE_COLUMNS[1], strike.getDescription_strike());
        values.put(TABLE_COLUMNS[2], strike.getDate_strike());
        values.put(TABLE_COLUMNS[3], strike.getIdAlumn());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean verifEqualsStrikes (Strike strike) {

        List<Strike> strikeList;
        boolean valid = true;

        strikeList = getAllStrikes();

        for (int aux = 0;aux<strikeList.size();aux ++) {
            if(strike.getDescription_strike().equals(strikeList.get(aux).getDescription_strike()) &&
                    strike.getDate_strike().equals(strikeList.get(aux).getDate_strike())) {
                Log.d("Advertência repetida","");
                valid = true;
            } else {
                valid = false;
            }
        }
        return valid;
    }

    public List<Strike> getAllStrikes () {
        List<Strike> strikeList = new ArrayList<Strike>();
        sqliteDatabase = database.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery( query, null );

        while(cursor.moveToNext()) {
            Strike strike = new Strike();

            strike.setIdStrike(cursor.getInt(cursor.getColumnIndex("IDStrike")));
            strike.setDescription_strike(cursor.getString(cursor.getColumnIndex("descriptionStrike")));
            strike.setDate_strike(cursor.getString(cursor.getColumnIndex("dateStrike")));
            strikeList.add(strike);
        }
        return strikeList;
    }

    private boolean existsStrike(Strike strike) {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        String exists = "SELECT IDStrike FROM Strike WHERE IDStrike =? LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(exists, new String[]{
                String.valueOf(strike.getIdStrike())});
        int quantaty = cursor.getCount();
        boolean valid = true;

        if(quantaty > 0) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void updateStrike(Strike strike) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[1], strike.getDescription_strike());
        values.put(TABLE_COLUMNS[2], strike.getDate_strike());

        sqLiteDatabase.update(TABLE_NAME, values, "[IDStrike] = ? ",new String[]{
                String.valueOf(strike.getIdStrike())});
        sqLiteDatabase.close();
        database.close();
    }

    public List<ParentAlumn> getParentAlumn () {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();

        String query = "SELECT * FROM Alumn " +
                "LEFT JOIN Parent ON Alumn.IDParent = Parent.IDParent " +
                "LEFT JOIN Strike ON Strike.IDAlumn = Alumn.IDAlumn " +
                "WHERE Alumn.IDAlumn =  4" ;


        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        while(cursor.moveToNext()) {
            ParentAlumn parentAlumn = new ParentAlumn();

            parentAlumn.setNameAlumn(cursor.getString(cursor.getColumnIndex("nameAlumn")));
            parentAlumn.setDescriptionStrike(cursor.getString(cursor.getColumnIndex("descriptionStrike")));
            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentAlumnList.add(parentAlumn);
        }
        return parentAlumnList;
    }
}
