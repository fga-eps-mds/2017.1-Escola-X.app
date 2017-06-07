package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Alumn;
import model.Parent;

/**
 * Created by victor on 07/06/17.
 */


public class ParentDao extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "treeHouse";
    private static final int VERSION = 42;
    private static ParentDao instance = null;

    public static final String PARENT = "Parent";
    private static final String DROP_TABLE_PARENT = "DROP TABLE IF EXISTS " + PARENT;

    private static final String NAME_PARENT = "[nameParent]";
    private static final String PHONE_PARENT = "[phoneParent]";
    private static final String PARENT_ID = "[IDParent]";
    Alumn alumn;

    public ParentDao(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String TABLE_PARENT = "CREATE TABLE IF NOT EXISTS " + PARENT+ " (" +
            PARENT_ID + "INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT ," +
            NAME_PARENT + " VARCHAR(64) NOT NULL, "+
            PHONE_PARENT + " VARCHAR(13) NOT NULL, " +
            "FOREIGN KEY("+AlumnDao.ALUMN_ID+") " +
            "REFERENCES "+AlumnDao.ALUMN_TABLE+"("+AlumnDao.ALUMN_ID+"));";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PARENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertParent (Parent parent) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARENT_ID, parent.getIdParent());
        contentValues.put(NAME_PARENT, parent.getName());
        contentValues.put(PHONE_PARENT, parent.getPhone());
        contentValues.put(AlumnDao.ALUMN_ID, alumn.getIdAlumn());

        long result = database.insert(PARENT,null,contentValues);
        database.close();
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
}
