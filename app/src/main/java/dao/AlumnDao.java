package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import helper.DatabaseHelper;
import model.Alumn;

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

    public void syncronAlumn (List<Alumn> alumns) {

        for(int aux = 0;aux<alumns.size();aux++) {
            Alumn alumn = new Alumn();

            alumn.setIdAlumn(alumns.get(aux).getIdAlumn());
            alumn.setName(alumns.get(aux).getName());
            alumn.setRegistry(alumns.get(aux).getRegistry());
            alumn.setIdParent(alumns.get(aux).getIdParent());

            if(existsAlumn(alumn) == true ) {
                if(verifEqualsAlumns(alumn) == false ) {
                    updateAlumn(alumn);
                }
            } else {
                insertAlumn(alumn);
            }
        }
    }

    public boolean insertAlumn (Alumn alumn) {

        sqliteDatabase = database.getWritableDatabase();
        boolean valid = true;

        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[0], alumn.getIdAlumn());
        values.put(TABLE_COLUMNS[1], alumn.getName());
        values.put(TABLE_COLUMNS[2], alumn.getRegistry());
        values.put(TABLE_COLUMNS[3], alumn.getIdParent());

        long result = insertAndClose(sqliteDatabase, TABLE_NAME, values);

        if (result == -1) {
            valid = false;
        } else {
            valid = true;
        }
        database.close();
        sqliteDatabase.close();
        return valid;
    }

    private void updateAlumn(Alumn alumn) {
        sqliteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_COLUMNS[1], alumn.getName());
        values.put(TABLE_COLUMNS[2], alumn.getRegistry());

        sqliteDatabase.update(TABLE_NAME, values, "[IDAlumn] = ? ",new String[]{
                String.valueOf(alumn.getIdAlumn())});
        sqliteDatabase.close();
        database.close();
    }

    private boolean verifEqualsAlumns (Alumn alumn) {

        List<Alumn> alumnList;
        boolean valid = true;

        alumnList = getAllAlumns();

        for(int aux = 0; aux < alumnList.size();aux ++) {
            if (alumn.getName().equals(alumnList.get(aux).getName())  &&
                    alumn.getRegistry().equals(alumnList.get(aux).getRegistry())) {
                valid = true;
            } else {
                valid = false;
            }
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
        sqliteDatabase.close();
        database.close();
        return alumnList;
    }

    private boolean existsAlumn(Alumn alumn) {
        sqliteDatabase = database.getReadableDatabase();
        String exists = "SELECT IDAlumn FROM Alumn WHERE IDAlumn =? LIMIT 1";
        Cursor cursor = sqliteDatabase.rawQuery(exists, new String[]{
                                                            String.valueOf(alumn.getIdAlumn())});
        int quantaty = cursor.getCount();
        boolean valid = true;

        if(quantaty > 0) {
            valid = true;
        } else {
            valid = false;
        }
        sqliteDatabase.close();
        database.close();
        return valid;
    }
}
