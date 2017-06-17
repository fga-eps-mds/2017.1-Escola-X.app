package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;
import model.Strike;
import model.Suspension;

public class SuspensionDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"IDSuspension","title","description",
                                                   "quantityDays","suspensionDate","IDAlumn"};

    private static SuspensionDao instance = null;
    private static String TABLE_NAME = "Suspension";

    private SuspensionDao(Context context) {
        SuspensionDao.database = new DatabaseHelper(context);
    }

    public static SuspensionDao getInstance(Context context) {

        if(SuspensionDao.instance != null) {

        } else {
            SuspensionDao.instance = new SuspensionDao(context);
        }

        return SuspensionDao.instance;
    }

    public boolean insertSuspension (Suspension suspension, Alumn alumn) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], suspension.getIdSuspension());
        values.put(TABLE_COLUMNS[1], suspension.getTitle());
        values.put(TABLE_COLUMNS[2], suspension.getDescription());
        values.put(TABLE_COLUMNS[3], suspension.getQuantity_days());
        values.put(TABLE_COLUMNS[4], suspension.getDateSuspension());
        values.put(TABLE_COLUMNS[5], alumn.getIdAlumn());


        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public List<Suspension> getSuspension() {

        Suspension suspension = new Suspension();
        List<Suspension> suspensionList = new ArrayList<Suspension>();

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + "WHERE IDAlumn = " + DatabaseHelper.ALUMN_ID;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        while(cursor.moveToFirst()) {
            suspension.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            suspension.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            suspension.setQuantity_days(cursor.getInt(cursor.getColumnIndex("quantityDays")));
            suspensionList.add(suspension);
        }
        return suspensionList;
    }
}
