package helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "escolaX.db";
    private static final int VERSION = 42;

    private static final String ALUMN_TABLE = "Alumn";
    private static final String DROP_TABLE_ALUMN = "DROP TABLE IF EXISTS " + ALUMN_TABLE;

    private static final String NAME_ALUMN = "[nameAlumn]";
    private static final String ALUMN_ID = "[IDAlumn]";
    private static final String REGISTRY_ALUMN = "[registryAlumn]";

    private static final String PARENT = "Parent";
    private static final String NAME_PARENT = "[nameParent]";
    private static final String PHONE_PARENT = "[phoneParent]";
    private static final String PARENT_ID = "[IDParent]";

    private static final String CREATE_ALUMN = "CREATE TABLE IF NOT EXISTS " + ALUMN_TABLE+ " (" +
            ALUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NAME_ALUMN + " VARCHAR(64) NOT NULL, "+
            REGISTRY_ALUMN + " VARCHAR(6) NOT NULL );";

    private static final String TABLE_PARENT = "CREATE TABLE IF NOT EXISTS " + PARENT+ " (" +
            PARENT_ID + "INTEGER NOT NULL," +
            NAME_PARENT + " VARCHAR(64) NOT NULL, "+
            PHONE_PARENT + " VARCHAR(13) NOT NULL, "+
            ALUMN_ID + " INTEGER, " +
            "FOREIGN KEY ("+ALUMN_ID+") REFERENCES "+ ALUMN_TABLE + "("+ALUMN_ID+"));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_ALUMN);
        database.execSQL(TABLE_PARENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
