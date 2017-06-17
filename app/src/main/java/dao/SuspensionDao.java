package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;
import model.Strike;
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

    public boolean insertSuspension (Suspension suspension, Alumn alumn) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], suspension.getIdSuspension());
        values.put(TABLE_COLUMNS[1], suspension.getTitle());
        values.put(TABLE_COLUMNS[2], suspension.getDescription());
        values.put(TABLE_COLUMNS[3], suspension.getQuantity_days());
        values.put(TABLE_COLUMNS[4], alumn.getIdAlumn());


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

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        while(cursor.moveToFirst()) {
            suspension.setIdSuspension(cursor.getInt(cursor.getColumnIndex("IDSuspension")));
            suspension.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            suspension.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            suspension.setQuantity_days(cursor.getInt(cursor.getColumnIndex("quantityDays")));
            suspensionList.add(suspension);
        }
        return suspensionList;
    }

    private boolean verifEqualsSuspension (Suspension suspension) {

        List<Suspension> suspensionList;
        boolean valid = true;

        suspensionList = getSuspension();

        for(int aux = 0; aux < suspensionList.size();aux ++) {
            if (suspension.getDescription().equals(suspensionList.get(aux).getDescription()) &&
                suspension.getTitle().equals(suspensionList.get(aux).getTitle()) &&
                suspension.getQuantity_days().equals(suspensionList.get(aux).getQuantity_days()) &&
                suspension.getDateSuspension().equals(suspensionList.get(aux).getDateSuspension())){
                Log.d("Suspensões iguais","");
                valid = true;
            } else {
                valid = false;
            }
        }
        return valid;
    }

    public void syncronSuspension (List<Suspension> suspensionList, Alumn alumnID) {

        for(int aux = 0; aux < suspensionList.size(); aux ++) {
            Suspension suspension = new Suspension();
            Alumn alumn = new Alumn();

            suspension.setIdSuspension(suspensionList.get(aux).getIdSuspension());
            suspension.setDescription(suspensionList.get(aux).getDescription());
            suspension.setTitle(suspensionList.get(aux).getTitle());
            suspension.setQuantity_days(suspensionList.get(aux).getQuantity_days());
            suspension.setDateSuspension(suspensionList.get(aux).getDateSuspension());
            alumn.setIdAlumn(alumnID.getIdAlumn());

            if(existsSuspension(suspension) == true) {
                if(verifEqualsSuspension(suspension) == false) {
                    updateSuspension(suspension);
                }
            } else {
                insertSuspension(suspension,alumn);
            }
        }
    }

    private boolean existsSuspension(Suspension suspension) {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        String exists = "SELECT IDSuspension FROM Suspension WHERE IDSuspension =? LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(exists, new String[]{
                String.valueOf(suspension.getIdSuspension())});
        int quantaty = cursor.getCount();
        boolean valid = true;

        if(quantaty > 0) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void updateSuspension(Suspension suspension) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[1], suspension.getTitle());
        values.put(TABLE_COLUMNS[2], suspension.getDescription());
        values.put(TABLE_COLUMNS[3], suspension.getQuantity_days());

        sqLiteDatabase.update(TABLE_NAME, values, "[IDSuspension] = ? ",new String[]{
                String.valueOf(suspension.getIdSuspension())});
        sqLiteDatabase.close();
        database.close();
    }
}
