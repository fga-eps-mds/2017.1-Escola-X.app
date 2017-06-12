package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import helper.DatabaseHelper;
import model.Alumn;
import model.Parent;
import model.ParentAlumn;


public class ParentDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"IDParent","nameParent","phoneParent","IDAlumn"};

    private static ParentDao instance = null;
    private static String TABLE_NAME = "Parent";

    private ParentDao(Context context) {
        ParentDao.database = new DatabaseHelper(context);
    }

    public static ParentDao getInstance(Context context) {

        if(ParentDao.instance != null) {

        } else {
            ParentDao.instance = new ParentDao(context);
        }

        return ParentDao.instance;
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

    public boolean insertParent (Parent parent) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], parent.getIdParent());
        values.put(TABLE_COLUMNS[1], parent.getName());
        values.put(TABLE_COLUMNS[2], parent.getPhone());
        values.put(TABLE_COLUMNS[3], parent.getAlumn().getIdAlumn());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public void syncronParent (List<Parent> parentList, List<Alumn> alumnList) {

        /*Map<String,List> rel_pai = new Map<String, List>;

        for (int aux = 0;aux<alumnList.size();aux ++) {
            Parent parent = new Parent();

            //rel_pai.put(id,list);
        }*/


        /*for(int aux = 0; aux < alumnList.size();aux ++) {
            Parent parent = new Parent();
            Alumn alumn = new Alumn();

            parent.setIdParent(parentList.get(aux).getIdParent());
            parent.setName(parentList.get(aux).getName());
            parent.setPhone(parentList.get(aux).getPhone());
            parent.setAlumn(alumnList.get(aux).getIdAlumn());
        }*/

        /*for(int aux = 0;aux<parentList.size();aux++) {
            Parent parent = new Parent();

            for(int alumnParent = 0; alumnParent < alumnList.size(); alumnParent ++) {

            }

            parent.setIdParent();

            alumn.setIdAlumn(alumns.get(aux).getIdAlumn());
            alumn.setName(alumns.get(aux).getName());
            alumn.setRegistry(alumns.get(aux).getRegistry());

            if(existsAlumn(alumn) == true ) {
                updateAlumn(alumn);
            } else {
                insertAlumn(alumn);
            }
        }*/
    }

    public List<ParentAlumn> getParent () {

        ParentAlumn parentAlumn = new ParentAlumn();
        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " LEFT JOIN " + DatabaseHelper.ALUMN_TABLE +
                " ON " + TABLE_NAME +".IDAlumn = " + DatabaseHelper.ALUMN_TABLE + ".IDAlumn "; /*+
                "LEFT JOIN " + DatabaseHelper.STRIKE_TABLE + "ON " + TABLE_NAME + ".IDAlumn = " +
                DatabaseHelper.STRIKE_TABLE + ".IDAlumn " + " LEFT JOIN " +
                DatabaseHelper.SUSPENSION_TABLE + " ON " + TABLE_NAME + ".IDAlumn = " +
                DatabaseHelper.SUSPENSION_TABLE + ".IDAlumn";*/

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        while (cursor.moveToFirst()) {
            parentAlumn.setAlumnId(cursor.getInt(cursor.getColumnIndex("IDAlumn")));
            parentAlumn.setNameAlumn(cursor.getString(cursor.getColumnIndex("nameAlumn")));
            parentAlumn.setRegistryAlumn(cursor.getString(cursor.getColumnIndex("registryAlumn")));
            parentAlumn.setIdParent(cursor.getInt(cursor.getColumnIndex("IDParent")));
            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            /*parentAlumn.setIdStrike(cursor.getInt(cursor.getColumnIndex("IDStrike")));
            parentAlumn.setDescription_strike(cursor.getString(cursor.getColumnIndex("descriptionStrike")));
            parentAlumn.setDate_strike(cursor.getString(cursor.getColumnIndex("dateStrike")));
            parentAlumn.setIdSuspension(cursor.getInt(cursor.getColumnIndex("IDSuspension")));
            parentAlumn.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            parentAlumn.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            parentAlumn.setQuantity_days(cursor.getInt(cursor.getColumnIndex("quantityDays")));*/

            parentAlumnList.add(parentAlumn);
        }
        return parentAlumnList;
    }
}
