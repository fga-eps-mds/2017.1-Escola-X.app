package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
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

    public boolean isDbEmpty(){
        sqliteDatabase = database.getReadableDatabase();
        String query = "SELECT  1 FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery(query, null);
        boolean isEmpty = false;

        if(cursor != null) {
            if(cursor.getCount() <= 0) {
                cursor.moveToFirst();
                isEmpty = true;
            } else {
                /* Nothing to do.*/
            }
        } else {
            isEmpty = true;
        }

        return isEmpty;
    }

    public boolean insertStrike (Strike strike) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], strike.getIdStrike());
        values.put(TABLE_COLUMNS[1], strike.getDescription_strike());
        values.put(TABLE_COLUMNS[2], strike.getDate_strike());
        values.put(TABLE_COLUMNS[3], strike.getAlumn().getIdAlumn());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public List<Strike> getStrike() {

        Strike strike = new Strike();
        List<Strike> strikeList = new ArrayList<Strike>();

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + "WHERE IDAlumn = " + DatabaseHelper.ALUMN_ID;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        while(cursor.moveToFirst()) {
            strike.setDescription_strike(cursor.getString(cursor.getColumnIndex("descriptionStrike")));
            strike.setDate_strike(cursor.getString(cursor.getColumnIndex("dateStrike")));
            strikeList.add(strike);
        }
        return strikeList;
    }
}
