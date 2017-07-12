package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.ParentAlumn;
import model.Strike;

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

    public boolean insertStrike (Strike strike) {

        sqliteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], strike.getIdStrike());
        values.put(TABLE_COLUMNS[1], strike.getDescription_strike());
        values.put(TABLE_COLUMNS[2], strike.getDate_strike());
        values.put(TABLE_COLUMNS[3], strike.getIdAlumn());

        long result = insertAndClose(sqliteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        sqliteDatabase.close();
        database.close();
        return valid;
    }

    public List<ParentAlumn> getParentAlumnStrike () {

        sqliteDatabase = database.getWritableDatabase();
        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();

        String query = "SELECT * FROM Alumn " +
                "LEFT JOIN Parent ON Alumn.IDParent = Parent.IDParent " +
                "LEFT JOIN Strike ON Strike.IDAlumn = Alumn.IDAlumn " +
                "WHERE Strike.IDStrike NOT NULL ;";

        Cursor cursor = sqliteDatabase.rawQuery(query,null);
        while(cursor.moveToNext()) {
            ParentAlumn parentAlumn = new ParentAlumn();

            parentAlumn.setIdStrike(cursor.getInt(cursor.getColumnIndex("IDStrike")));
            parentAlumn.setNameAlumn(cursor.getString(cursor.getColumnIndex("nameAlumn")));
            parentAlumn.setDescriptionStrike(cursor.getString(cursor.getColumnIndex("descriptionStrike")));
            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentAlumn.setDateStrike(cursor.getString(cursor.getColumnIndex("dateStrike")));
            parentAlumnList.add(parentAlumn);
        }
        sqliteDatabase.close();
        database.close();
        return parentAlumnList;
    }

    public boolean deleteStrike (Strike strike) {

        sqliteDatabase = database.getWritableDatabase();
        boolean sucess = true;

        long result = sqliteDatabase.delete(TABLE_NAME, "[IDStrike] = " +
                strike.getIdStrike(),null);

        if( result == -1) {
            sucess = false;
        } else {
            sucess = true;
        }
        database.close();
        sqliteDatabase.close();
        return sucess;
    }
}
