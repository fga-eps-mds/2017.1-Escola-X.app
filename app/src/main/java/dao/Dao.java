package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import helper.DatabaseHelper;

public class Dao {

    protected static DatabaseHelper database;
    protected static SQLiteDatabase sqliteDatabase;
    protected static Context context;

    protected long insertAndClose(SQLiteDatabase sqLiteDatabase, String table,
                                  ContentValues values) {
        assert (table != null) : "Table name never be null.";
        assert (table.length() >= 1) : "Table name must have at least one character.";

        sqLiteDatabase.insert(table, null, values);
        long resultInsert = 1;
        sqLiteDatabase.close();

        return resultInsert;
    }
}
