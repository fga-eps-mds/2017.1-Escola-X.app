package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;
import model.Parent;
import model.Person;

public class AlumnDao extends Dao {

    private static final String TABLE_COLUMNS[] = {"IDAlumn","nameAlumn","registryAlumn","IDParent"};

    private static AlumnDao instance = null;
    private static String TABLE_NAME = "Alumn";

    private AlumnDao(Context context) {
        AlumnDao.database = new DatabaseHelper(context);
    }

    public static AlumnDao getInstance(Context context) {

        if(AlumnDao.instance != null) {

        } else {
            AlumnDao.instance = new AlumnDao(context);
        }

        return AlumnDao.instance;
    }

    public boolean isDbEmpty(){
        sqliteDatabase = database.getReadableDatabase();
        String query = "SELECT  1 FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery(query, null);
        boolean isEmpty = false;

        if(cursor != null) {
            if(cursor.getCount() <= 0) {
                cursor.moveToFirst();
                isEmpty = true;
            } else {
                /* Nothing to do.*/
            }
        } else {
            isEmpty = true;
        }

        return isEmpty;
    }

    public boolean insertAlumn (Alumn alumn, Parent parent) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], alumn.getIdAlumn());
        values.put(TABLE_COLUMNS[1], alumn.getName());
        values.put(TABLE_COLUMNS[2], alumn.getRegistry());
        values.put(TABLE_COLUMNS[3], parent.getIdParent());

        long result = insertAndClose(sqLiteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    public List<Alumn> getAllAlumns() {
        List<Alumn> alumnList = new ArrayList<Alumn>();
        sqliteDatabase = database.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqliteDatabase.rawQuery( query, null );

        while(cursor.moveToNext()) {
            Alumn alumn = new Alumn();

            alumn.setIdAlumn(cursor.getInt(cursor.getColumnIndex("IDAlumn")));
            alumn.setName(cursor.getString(cursor.getColumnIndex("nameAlumn")));
            alumn.setRegistry(cursor.getInt(cursor.getColumnIndex("registryAlumn")));
            alumnList.add(alumn);
        }
        return alumnList;
    }

    public void syncronAlumn (List<Alumn> alumns, List<Person> personList) {

        for(int aux = 0;aux<alumns.size();aux++) {
            Alumn alumn = new Alumn();
            Parent parent = new Parent();

            alumn.setIdAlumn(alumns.get(aux).getIdAlumn());
            alumn.setName(alumns.get(aux).getName());
            alumn.setRegistry(alumns.get(aux).getRegistry());
            parent.setIdParent(personList.get(aux).getIdPerson());

            if(existsAlumn(alumn) == true ) {
                updateAlumn(alumn);
            } else {
                insertAlumn(alumn,parent);
            }
        }
    }

    private boolean existsAlumn(Alumn alumn) {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        String existe = "SELECT IDAlumn FROM Alumn WHERE IDAlumn =? LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(existe, new String[]{String.valueOf(alumn.getIdAlumn())});
        int quantidade = cursor.getCount();
        boolean valid = true;

        if(quantidade > 0) {
            valid = true;
        } else {
            valid = false;
        }
        return valid;
    }

    private void updateAlumn(Alumn alumn) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], alumn.getIdAlumn());
        values.put(TABLE_COLUMNS[1], alumn.getName());
        values.put(TABLE_COLUMNS[2], alumn.getRegistry());

        sqLiteDatabase.update(TABLE_NAME, values, "[IDAlumn] = ? ",new String[]
                                            {String.valueOf(alumn.getIdAlumn())});
        sqLiteDatabase.close();
        database.close();
    }
}
