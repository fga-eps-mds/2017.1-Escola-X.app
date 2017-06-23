package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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

    public void syncronParent (List<Parent> parentList) {

        for(int aux = 0;aux<parentList.size();aux ++) {

            Parent parent = new Parent();

            parent.setIdParent(parentList.get(aux).getIdParent());
            parent.setName(parentList.get(aux).getName());
            parent.setPhone(parentList.get(aux).getPhone());

            if(existsParent(parent) == true ) {
                if(verifEqualsParents(parent) == false ) {
                    updateParent(parent);
                } else {
                    /*Nothing to do */
                }
            } else {
                insertParent(parent);
            }
        }
    }

    private boolean insertParent (Parent parent) {

        sqliteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], parent.getIdParent());
        values.put(TABLE_COLUMNS[1], parent.getName());
        values.put(TABLE_COLUMNS[2], parent.getPhone());

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

    private void updateParent(Parent parent) {
        sqliteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], parent.getIdParent());
        values.put(TABLE_COLUMNS[1], parent.getName());
        values.put(TABLE_COLUMNS[2], parent.getPhone());

        sqliteDatabase.update(TABLE_NAME, values, "[IDParent] = ? ",new String[]{
                String.valueOf(parent.getIdParent())});
        sqliteDatabase.close();
        database.close();
    }

    private List<Parent> getAllParents() {
        List<Parent> parentList = new ArrayList<Parent>();
        sqliteDatabase = database.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery( query, null );

        while(cursor.moveToNext()) {
            Parent parent = new Parent();

            parent.setIdParent(cursor.getInt(cursor.getColumnIndex("IDParent")));
            parent.setName(cursor.getString(cursor.getColumnIndex("nameParent")));
            parent.setPhone(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentList.add(parent);
        }
        sqliteDatabase.close();
        database.close();
        return parentList;
    }

    private boolean verifEqualsParents (Parent parent) {

        List<Parent> parentList;
        boolean valid = true;

        parentList = getAllParents();

        for(int aux = 0; aux < parentList.size();aux ++) {
            if (parent.getName().equals(parentList.get(aux).getName())  &&
                    parent.getPhone().equals(parentList.get(aux).getPhone())) {
                valid = true;
            } else {
                valid = false;
            }
        }
        return valid;
    }

    private boolean existsParent(Parent parent) {
        sqliteDatabase = database.getReadableDatabase();
        String exists = "SELECT IDParent FROM Parent WHERE IDParent =? LIMIT 1";
        Cursor cursor = sqliteDatabase.rawQuery(exists, new String[]{
                                                            String.valueOf(parent.getIdParent())});
        int quantaty = cursor.getCount();
        boolean valid = true;

        if(quantaty > 0) {
            valid = true;
        } else {
            valid = false;
        }
        sqliteDatabase.close();
        database.close();
        return valid;
    }
}
