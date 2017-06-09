package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import helper.DatabaseHelper;
import model.Alumn;
import model.Parent;


public class ParentDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"IDParent","nameParent","phoneParent"};

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

    public boolean insertParent (Parent parent) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], parent.getIdParent());
        values.put(TABLE_COLUMNS[1], parent.getNameParent());
        values.put(TABLE_COLUMNS[2], parent.getPhoneParent());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}
