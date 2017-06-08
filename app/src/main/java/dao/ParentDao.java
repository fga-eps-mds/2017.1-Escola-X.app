package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Parent;


public class ParentDao extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "treeHouse";
    private static final int VERSION = 42;
    private static ParentDao instance = null;

    public static final String PARENT = "Parent";
    private static final String DROP_TABLE_PARENT = "DROP TABLE IF EXISTS " + PARENT;

    private static final String NAME_PARENT = "[nameParent]";
    private static final String PHONE_PARENT = "[phoneParent]";
    private static final String PARENT_ID = "[IDParent]";

    public ParentDao(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String TABLE_PARENT = "CREATE TABLE IF NOT EXISTS " + PARENT+ " (" +
            PARENT_ID + "INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT ," +
            NAME_PARENT + " VARCHAR(64) NOT NULL, "+
            PHONE_PARENT + " VARCHAR(13) NOT NULL ); ";

    public static ParentDao getInstance(Context context){
        if(ParentDao.instance != null){

        }else{
            ParentDao.instance = new ParentDao(context);
        }
        return ParentDao.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_PARENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public boolean insertParent (Parent parent) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PARENT_ID, parent.getIdParent());
        contentValues.put(NAME_PARENT, parent.getName());
        contentValues.put(PHONE_PARENT, parent.getPhone());

        long result = database.insert(PARENT,null,contentValues);
        database.close();
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }*/
}
