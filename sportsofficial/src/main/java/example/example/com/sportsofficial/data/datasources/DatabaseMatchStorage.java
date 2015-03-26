package example.example.com.sportsofficial.data.datasources;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import example.example.com.sportsofficial.presentation.models.Match;


public class DatabaseMatchStorage {
    private DatabaseHelper mDatabaseHelper;

    public DatabaseMatchStorage(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public Match getMatch(int id) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        Cursor res = database.rawQuery( "select * from matches where id="+id+"", null );

        //return res;
        Match match = new Match();
        match.setMatchId(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_ID)));
        match.setSportId(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_SPORT_ID)));
        match.setHomeTeamName(res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_HOME_TEAM_NAME)));
        match.setHomeTeamScore(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_HOME_TEAM_SCORE)));
        match.setAwayTeamName(res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_NAME)));
        match.setAwayTeamScore(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_SCORE)));
        String date_time = res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_DATETIME));
        res.close();
        return match;
    }

    public List<Match> getMatchList(int sportId) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();
        List list = new ArrayList();
        Cursor res = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_MATCHES
                + " WHERE " + DatabaseHelper.MATCHES_KEY_SPORT_ID + "=" + sportId,
                null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Match match = new Match();
            match.setMatchId(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_ID)));
            match.setSportId(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_SPORT_ID)));
            match.setHomeTeamName(res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_HOME_TEAM_NAME)));
            match.setHomeTeamScore(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_HOME_TEAM_SCORE)));
            match.setAwayTeamName(res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_NAME)));
            match.setAwayTeamScore(res.getInt(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_SCORE)));
            String date_time = res.getString(res.getColumnIndex(DatabaseHelper.MATCHES_KEY_DATETIME));
            //Parse date_time
            list.add(match);
            res.moveToNext();
        }
        res.close();
        return list;
    }

    public void addMatch(Match match) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.MATCHES_KEY_SPORT_ID, match.getSportId());
        values.put(DatabaseHelper.MATCHES_KEY_HOME_TEAM_NAME, match.getHomeTeamName());
        values.put(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_NAME, match.getAwayTeamName());
        values.put(DatabaseHelper.MATCHES_KEY_HOME_TEAM_SCORE, match.getHomeTeamScore());
        values.put(DatabaseHelper.MATCHES_KEY_AWAY_TEAM_SCORE, match.getAwayTeamScore());
        // Date and time
        database.insert(DatabaseHelper.TABLE_MATCHES, null, values);
    }

    public void removeMatch(int id) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
        database.delete("matches",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public void updateMatch(Match match) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();
    }
}
