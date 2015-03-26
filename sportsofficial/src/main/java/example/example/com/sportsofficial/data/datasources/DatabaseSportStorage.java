package example.example.com.sportsofficial.data.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import example.example.com.sportsofficial.presentation.models.Sport;

public class DatabaseSportStorage {
    private DatabaseHelper mDatabaseHelper;

    public DatabaseSportStorage(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public Sport getSport(int id) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        Cursor res = database.rawQuery( "select * from sports where id="+id+"", null );
        //return res;
        Sport sport = new Sport();
        sport.setId(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_ID)));
        sport.setName(res.getString(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_NAME)));
        sport.setSingleClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK)));
        sport.setDoubleClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK)));
        sport.setLongClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_LONG_CLICK)));
        res.close();
        return sport;
    }

    public List<Sport> getSportList() {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        List list = new ArrayList();
        Cursor res = database.rawQuery("select * from sports", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Sport sport = new Sport();
            sport.setId(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_ID)));
            sport.setName(res.getString(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_NAME)));
            sport.setSingleClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK)));
            sport.setDoubleClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK)));
            sport.setLongClick(res.getInt(res.getColumnIndex(DatabaseHelper.SPORTS_KEY_LONG_CLICK)));
            //Parse date_time
            list.add(sport);
            res.moveToNext();
        }
        res.close();
        return list;
    }

    public void addSport(Sport sport) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SPORTS_KEY_NAME, sport.getName());
        values.put(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK, sport.getSingleClick());
        values.put(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK, sport.getDoubleClick());
        values.put(DatabaseHelper.SPORTS_KEY_LONG_CLICK, sport.getLongClick());
        // Date and time
        database.insert(DatabaseHelper.TABLE_SPORTS, null, values);
    }

    public void removeSport(int id) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.delete("sports",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void updateSport(Sport sport) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
    }
}
