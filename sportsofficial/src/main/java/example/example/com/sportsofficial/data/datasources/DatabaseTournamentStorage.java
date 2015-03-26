package example.example.com.sportsofficial.data.datasources;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class DatabaseTournamentStorage {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public DatabaseTournamentStorage(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }

}
