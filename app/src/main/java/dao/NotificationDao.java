package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;
import model.Notification;
import model.Parent;
import model.ParentAlumn;
import model.Strike;
import model.Suspension;

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

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], notification.getIdNotification());
        values.put(TABLE_COLUMNS[1], notification.getNotification_text());
        values.put(TABLE_COLUMNS[2], notification.getMotive());
        values.put(TABLE_COLUMNS[3], notification.getNotificaton_date());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public Integer deleteNotification (Notification notification) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME, "[notificationID] = " + notification.getIdNotification(),null);
    }

    /*public List<Notification> getNotification() {
        List<Notification> notificationList = new ArrayList<Notification>();
        sqliteDatabase = database.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery( query, null );

        while(cursor.moveToNext()) {
            Notification notification = new Notification();

            notification.setMotive(cursor.getString(cursor.getColumnIndex("motive")));
            notification.setNotification_text(cursor.getString(cursor.getColumnIndex("notificationText")));
            notification.setNotificaton_date(cursor.getString(cursor.getColumnIndex("notificationDate")));

            notificationList.add(notification);
        }
        return notificationList;
    }*/

    public List<ParentAlumn> getNotification () {

        List<ParentAlumn> parentAlumnList = new ArrayList<ParentAlumn>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        String query = "SELECT * FROM Parent LEFT JOIN Notification" ;

        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        while(cursor.moveToNext()) {
            ParentAlumn parentAlumn = new ParentAlumn();

            parentAlumn.setNameParent(cursor.getString(cursor.getColumnIndex("nameParent")));
            parentAlumn.setPhoneParent(cursor.getString(cursor.getColumnIndex("phoneParent")));
            parentAlumn.setNotificationDate(cursor.getString(cursor.getColumnIndex("notificationDate")));
            parentAlumn.setNotificationText(cursor.getString(cursor.getColumnIndex("notificationText")));
        }

        return parentAlumnList;
    }
}
