package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import helper.DatabaseHelper;
import model.Parent;
import model.Suspension;

public class SuspensionDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"IDSuspension","title","description",
                                                   "quantityDays","IDAlumn"};

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

    public boolean insertSuspension (Suspension suspension) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], suspension.getIdSuspension());
        values.put(TABLE_COLUMNS[1], suspension.getTitle());
        values.put(TABLE_COLUMNS[2], suspension.getDescription());
        values.put(TABLE_COLUMNS[3], suspension.getQuantity_days());
        values.put(TABLE_COLUMNS[4], suspension.getAlumn().getIdAlumn());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}
