package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import helper.DatabaseHelper;
import model.Notification;
import model.Suspension;

public class NotificationDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"notificationText","motive","notificationDate"};

    private static NotificationDao instance = null;
    private static String TABLE_NAME = "Notification";

    private NotificationDao(Context context) {
        NotificationDao.database = new DatabaseHelper(context);
    }

    public static NotificationDao getInstance(Context context) {

        if(NotificationDao.instance != null) {

        } else {
            NotificationDao.instance = new NotificationDao(context);
        }

        return NotificationDao.instance;
    }

    public boolean insertNotification (Notification notification) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], notification.getNotification_text());
        values.put(TABLE_COLUMNS[1], notification.getMotive());
        values.put(TABLE_COLUMNS[2], notification.getNotificaton_date());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}
