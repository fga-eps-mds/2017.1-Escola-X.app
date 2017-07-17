package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;

import model.Parent;

public class ParentDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"IDParent","nameParent","phoneParent"};

    private static ParentDao instance = null;
    private static String TABLE_NAME = "Parent";

    public ParentDao(Context context) {
        ParentDao.database = new DatabaseHelper(context);
    }

    public static ParentDao getInstance(Context context) {

        if(ParentDao.instance != null) {

        } else {
            ParentDao.instance = new ParentDao(context);
        }

        return ParentDao.instance;
    }

    public boolean insertParent (Parent parent) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], parent.getIdParent());
        values.put(TABLE_COLUMNS[1], parent.getName());
        values.put(TABLE_COLUMNS[2], parent.getPhone());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public List<Parent> getAllParents() {

        List<Parent> parentList = new ArrayList<Parent>();
        sqliteDatabase = database.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery( query, null );

        while(cursor.moveToNext()) {
            Parent parent = new Parent();

            parent.setIdParent(cursor.getInt(cursor.getColumnIndex("IDParent")));
            parentList.add(parent);
        }
        database.close();
        return parentList;
    }

    public boolean deleteParent (Parent parent) {

        sqliteDatabase = database.getWritableDatabase();
        boolean sucess = true;

        long result = sqliteDatabase.delete(TABLE_NAME,"[IDParent] = " + parent.getIdParent(),null);

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
