package helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Suspension;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "escolaX.db";
    private static final int VERSION = 42;

    public static final String ALUMN_TABLE = "Alumn";
    private static final String NAME_ALUMN = "[nameAlumn]";
    public static final String ALUMN_ID = "[IDAlumn]";
    private static final String REGISTRY_ALUMN = "[registryAlumn]";

    private static final String PARENT_TABLE = "Parent";
    private static final String NAME_PARENT = "[nameParent]";
    private static final String PHONE_PARENT = "[phoneParent]";
    private static final String PARENT_ID = "[IDParent]";

    public static final String STRIKE_TABLE = "Strike";
    private static final String STRIKE_ID = "[IDStrike]";
    private static final String DESCRIPTION_STRIKE = "[descriptionStrike]";
    private static final String DATE_STRIKE = "[dateStrike]";

    public static final String SUSPENSION_TABLE = "Suspension";
    private static final String SUSPENSION_ID = "[IDSuspension]";
    private static final String DESCRIPTION_SUSPENSION = "[description]";
    private static final String QUANTITY_DAYS = "[quantityDays]";
    private static final String Suspension_TITLE = "[title]";

    private static final String NOTIFICATION_TABLE = "Notification";
    private static final String NOTIFICATION_TEXT = "[notificationText]";
    private static final String NOTIFICATION_MOTIVE = "[motive]";
    private static final String NOTIFICATION_DATE = "[notificationDate]";

    private static final String CREATE_ALUMN = "CREATE TABLE IF NOT EXISTS " + ALUMN_TABLE + " (" +
            ALUMN_ID + " INTEGER PRIMARY KEY NOT NULL," +
            NAME_ALUMN + " VARCHAR(64) NOT NULL, " +
            REGISTRY_ALUMN + " VARCHAR(6) NOT NULL, " +
            PARENT_ID + " INTEGER, " +
            "FOREIGN KEY ("+PARENT_ID+") REFERENCES "+ PARENT_ID + "("+PARENT_ID+"));";

    private static final String CREATE_PARENT = "CREATE TABLE IF NOT EXISTS " + PARENT_TABLE + " (" +
            PARENT_ID + " INTEGER NOT NULL," +
            NAME_PARENT + " VARCHAR(64) NOT NULL, " +
            PHONE_PARENT + " VARCHAR(13) NOT NULL);";

    private static final String CREATE_STRIKE = "CREATE TABLE IF NOT EXISTS " + STRIKE_TABLE + " (" +
            STRIKE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
            DESCRIPTION_STRIKE + " VARCHAR(150) NOT NULL, " +
            DATE_STRIKE + "VARCHAR(10) NOT NULL, " +
            ALUMN_ID + " INTEGER, " +
            "FOREIGN KEY ("+ALUMN_ID+") REFERENCES "+ ALUMN_TABLE + "("+ALUMN_ID+"));";

    private static final String CREATE_SUSPENSION = "CREATE TABLE IF NOT EXISTS " +
            SUSPENSION_TABLE+ " (" +
            SUSPENSION_ID + " INTEGER PRIMARY KEY NOT NULL, " +
            Suspension_TITLE + "VARCHAR(20) NOT NULL, " +
            DESCRIPTION_SUSPENSION + " VARCHAR(150) NOT NULL, " +
            QUANTITY_DAYS + " INTEGER NOT NULL, " +
            ALUMN_ID + " INTEGER, " +
            "FOREIGN KEY ("+ALUMN_ID+") REFERENCES "+ ALUMN_TABLE + "("+ALUMN_ID+"));";

    private static final String CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " +
            NOTIFICATION_TABLE + " (" +
            NOTIFICATION_TEXT + " VARCHAR(150) NOT NULL, " +
            NOTIFICATION_MOTIVE + "VARCHAR(30) NOT NULL, " +
            NOTIFICATION_DATE + "VARCHAR(10) NOT NULL );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_ALUMN);
        database.execSQL(CREATE_PARENT);
        database.execSQL(CREATE_STRIKE);
        database.execSQL(CREATE_SUSPENSION);
        database.execSQL(CREATE_NOTIFICATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
