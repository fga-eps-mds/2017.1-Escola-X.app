package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import helper.DatabaseHelper;
import model.Notification;
import model.Parent;
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

    public void syncronNotification (List<Notification> notificationList) {

        for(int aux = 0;aux<notificationList.size();aux ++) {

            Notification notification = new Notification();

            notification.setIdNotification(notificationList.get(aux).getIdNotification());
            notification.setNotification_text(notificationList.get(aux).getNotification_text());
            notification.setTitle(notificationList.get(aux).getTitle());
            notification.setNotificaton_date(notificationList.get(aux).getNotificaton_date());
            notification.setMotive(notificationList.get(aux).getMotive());

            if(existsNotification(notification) == true ) {
                updateNotification(notification);
            } else {
                insertNotification(notification);
            }
        }
    }

    private boolean existsNotification(Notification notification) {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        String exists = "SELECT notificationID FROM Notification WHERE notificationID =? LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(exists, new String[]{
                String.valueOf(notification.getIdNotification())});
        int quantaty = cursor.getCount();
        boolean valid = true;

        if(quantaty > 0) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void updateNotification(Notification notification) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], notification.getNotification_text());
        values.put(TABLE_COLUMNS[1], notification.getMotive());
        values.put(TABLE_COLUMNS[2], notification.getNotificaton_date());

        sqLiteDatabase.update(TABLE_NAME, values, "[notificationID] = ? ",new String[]{
                                                String.valueOf(notification.getIdNotification())});
        sqLiteDatabase.close();
        database.close();
    }
}
