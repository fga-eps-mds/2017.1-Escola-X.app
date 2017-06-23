package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.ParentAlumn;
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

        sqliteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], suspension.getIdSuspension());
        values.put(TABLE_COLUMNS[1], suspension.getTitle());
        values.put(TABLE_COLUMNS[2], suspension.getDescription());
        values.put(TABLE_COLUMNS[3], suspension.getQuantity_days());
        values.put(TABLE_COLUMNS[4], suspension.getIdAlumn());


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

    public List<ParentAlumn> getParentAlumnSuspension () {

        sqliteDatabase = database.getWritableDatabase();
        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();

        String query = "SELECT * FROM Alumn " +
                       "LEFT JOIN Parent ON Alumn.IDParent = Parent.IDParent " +
                       "LEFT JOIN Suspension ON Suspension.IDAlumn = " +
                       "Alumn.IDAlumn WHERE Suspension.IDSuspension NOT NULL ;";

        Cursor cursor = sqliteDatabase.rawQuery(query,null);
        while(cursor.moveToNext()) {
            ParentAlumn parentAlumn = new ParentAlumn();

            parentAlumn.setIdNotification(cursor.getInt(cursor.getColumnIndex("IDSuspension")));
            parentAlumn.setNameAlumn(cursor.getString(cursor.getColumnIndex("nameAlumn")));
            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentAlumn.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            parentAlumn.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            parentAlumn.setQuantityDays(cursor.getInt(cursor.getColumnIndex("quantityDays")));

            parentAlumnList.add(parentAlumn);
        }
        sqliteDatabase.close();
        database.close();
        return parentAlumnList;
    }

    public boolean deleteSuspension (Suspension suspension) {

        sqliteDatabase = database.getWritableDatabase();
        boolean sucess = true;

        long result = sqliteDatabase.delete(TABLE_NAME, "[IDSuspension] = " +
                suspension.getIdSuspension(),null);

        if( result == -1) {
            sucess = false;
        } else {
            sucess = true;
        }
        sqliteDatabase.close();
        database.close();
        return sucess;
    }
}
