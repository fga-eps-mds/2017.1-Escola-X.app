package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Notification;
import model.ParentAlumn;

public class NotificationDao extends Dao{

    private static final String TABLE_COLUMNS[] = {"notificationID","notificationText","motive",
                                                   "notificationDate"};

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

        sqliteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], notification.getIdNotification());
        values.put(TABLE_COLUMNS[1], notification.getNotification_text());
        values.put(TABLE_COLUMNS[2], notification.getMotive());
        values.put(TABLE_COLUMNS[3], notification.getNotificaton_date());

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

    public boolean deleteNotification (Notification notification) {

        sqliteDatabase = database.getWritableDatabase();
        boolean sucess = true;

        long result = sqliteDatabase.delete(TABLE_NAME, "[notificationID] = " +
                                                            notification.getIdNotification(),null);

        if( result == -1) {
            sucess = false;
        } else {
            sucess = true;
        }
        sqliteDatabase.close();
        database.close();
        return sucess;
    }

    /*public Cursor getNotification() {

        sqliteDatabase = database.getReadableDatabase();
        String query = "SELECT * FROM Parent LEFT JOIN Notification;";

        Cursor cursor = sqliteDatabase.rawQuery(query,null);
        return cursor;
    }*/

    public List<ParentAlumn> getNotification () {

        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();
        sqliteDatabase = database.getWritableDatabase();

        String query = "SELECT * FROM Parent LEFT JOIN Notification;" ;

        Cursor cursor = sqliteDatabase.rawQuery(query,null);
        while(cursor.moveToNext()) {
            ParentAlumn parentAlumn = new ParentAlumn();

            parentAlumn.setIdNotification(cursor.getInt(cursor.getColumnIndex("notificationID")));
            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentAlumn.setNotificationDate(cursor.getString(cursor.getColumnIndex("notificationDate")));
            parentAlumn.setNotificationText(cursor.getString(cursor.getColumnIndex("notificationText")));
            parentAlumnList.add(parentAlumn);
        }
        sqliteDatabase.close();
        database.close();
        return parentAlumnList;
    }
}
