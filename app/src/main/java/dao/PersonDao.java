package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Person;

public class PersonDao extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "escolaX.db";
    private static final int VERSION = 42;
    private static PersonDao instance = null;
    private static final String ESCOLAX_TABLE = "EscolaX";

    private static final String NOTIFICATION_ID = "[notification_id]";
    private static final String NAME_PARENT = "[nameParent]";
    private static final String PHONE_PARENT = "[phoneParent]";
    private static final String NAME_ALUMN = "[nameAlumn]";
    private static final String REGISTRY_ALUMN = "[registry_alumn]";
    private static final String DESCRIPTION_STRIKE = "[description_strike]";
    private static final String TITLE_SUSPENSION = "[title_suspension]";
    private static final String DESCRIPTION_SUSPENSION = "[description_suspension]";
    private static final String QUANTITY_DAYS_SUSPENSION = "[quantity_days_suspension]";
    private static final String NOTIFICATION_TITLE = "[notification_title]";
    private static final String NOTIFICATION_DESCRIPTION = "[notification_description]";

    public PersonDao(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static PersonDao getInstance(Context context){
        if(PersonDao.instance != null){

        }else{
            PersonDao.instance = new PersonDao(context);
        }
        return PersonDao.instance;
    }


    private static final String PERSON = "CREATE TABLE IF NOT EXISTS " + ESCOLAX_TABLE + " (" +
            NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NAME_ALUMN + " VARCHAR(42) NOT NULL, "+
            REGISTRY_ALUMN + " INTEGER NOT NULL, " +
            NAME_PARENT + " VARCHAR(42) NOT NULL, " +
            PHONE_PARENT + " VARCHAR(13) NOT NULL, " +
            DESCRIPTION_STRIKE + " VARCHAR(100) NOT NULL, " +
            TITLE_SUSPENSION + " VARCHAR(30) NOT NULL, " +
            DESCRIPTION_SUSPENSION + " VARCHAR(100) NOT NULL, " +
            QUANTITY_DAYS_SUSPENSION + "INTEGER NOT NULL, " +
            NOTIFICATION_TITLE + " VARCHAR(30) NOT NULL, " +
            NOTIFICATION_DESCRIPTION + " VARCHAR(100) NOT NULL);";

    public PersonDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public boolean insertPerson (Person person) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_ALUMN, person.getNameAlumn());
        contentValues.put(REGISTRY_ALUMN, person.getRegistryAlumn());
        contentValues.put(NAME_PARENT, alumn.getRegistry());

        long result = database.insert(ALUMN_TABLE,null,contentValues);
        database.close();
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }*/
}
