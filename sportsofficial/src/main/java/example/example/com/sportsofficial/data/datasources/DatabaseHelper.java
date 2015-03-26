package example.example.com.sportsofficial.data.datasources;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database version
    public static final int DATABASE_VERSION = 1;

    // Database name
    public static final String DATABASE_NAME = "sports";

    // Sports table
    public static final String TABLE_SPORTS = "sports";
    public static final String SPORTS_KEY_ID = "id";
    public static final String SPORTS_KEY_NAME = "name";
    public static final String SPORTS_KEY_SINGLE_CLICK = "single";
    public static final String SPORTS_KEY_DOUBLE_CLICK = "double";
    public static final String SPORTS_KEY_LONG_CLICK = "long";

    // Leagues table
    public static final String TABLE_LEAGUES = "leagues";
    public static final String LEAGUES_KEY_ID = "id";
    public static final String LEAGUES_KEY_NAME = "name";
    public static final String LEAGUES_KEY_COUNTRY = "country";
    public static final String LEAGUES_KEY_STATE = "state";
    public static final String LEAGUES_KEY_CITY = "city";

    // Tournaments table
    public static final String TABLE_TOURNAMENTS = "tournaments";
    public static final String TOURNAMENTS_KEY_ID = "id";
    public static final String TOURNAMENTS_KEY_NAME = "name";
    public static final String TOURNAMENTS_KEY_VENUE = "venue";
    public static final String TOURNAMENTS_KEY_DATE = "date";
    public static final String TOURNAMENTS_KEY_COUNTRY = "country";
    public static final String TOURNAMENTS_KEY_STATE = "state";
    public static final String TOURNAMENTS_KEY_CITY = "city";

    // Matches table
    public static final String TABLE_MATCHES = "matches";
    public static final String MATCHES_KEY_ID = "id";
    public static final String MATCHES_KEY_SPORT_ID = "sport_id";
    public static final String MATCHES_KEY_HOME_TEAM_NAME = "home_team_name";
    public static final String MATCHES_KEY_AWAY_TEAM_NAME = "away_team_name";
    public static final String MATCHES_KEY_HOME_TEAM_SCORE = "home_team_score";
    public static final String MATCHES_KEY_AWAY_TEAM_SCORE = "away_team_score";
    public static final String MATCHES_KEY_DATETIME = "datetime";
    private Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the sports table
        String CREATE_SPORTS_TABLE = "CREATE TABLE " + TABLE_SPORTS + "("
                + SPORTS_KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + SPORTS_KEY_NAME + " TEXT,"
                + SPORTS_KEY_SINGLE_CLICK + " INTEGER,"
                + SPORTS_KEY_DOUBLE_CLICK + " INTEGER,"
                + SPORTS_KEY_LONG_CLICK + " INTEGER" + ")";
        db.execSQL(CREATE_SPORTS_TABLE);

        // Create the leagues table
        String CREATE_LEAGUES_TABLE = "CREATE TABLE " + TABLE_LEAGUES + "("
                + LEAGUES_KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + LEAGUES_KEY_NAME + " TEXT,"
                + LEAGUES_KEY_COUNTRY + " TEXT,"
                + LEAGUES_KEY_STATE + " TEXT,"
                + LEAGUES_KEY_CITY + " TEXT" + ")";
        db.execSQL(CREATE_LEAGUES_TABLE);

        // Create the tournaments table
        String CREATE_TOURNAMENTS_TABLE = "CREATE TABLE " + TABLE_TOURNAMENTS + "("
                + TOURNAMENTS_KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + TOURNAMENTS_KEY_NAME + " TEXT,"
                + TOURNAMENTS_KEY_VENUE + " TEXT,"
                + TOURNAMENTS_KEY_COUNTRY + " TEXT,"
                + TOURNAMENTS_KEY_STATE + " TEXT,"
                + TOURNAMENTS_KEY_CITY + " TEXT,"
                + TOURNAMENTS_KEY_DATE + " DATE" + ")";
        db.execSQL(CREATE_TOURNAMENTS_TABLE);

        // Create the matches table
        String CREATE_MATCHES_TABLE = "CREATE TABLE " + TABLE_MATCHES + "("
                + MATCHES_KEY_ID + " INTEGER NOT NULL PRIMARY KEY,"
                + MATCHES_KEY_SPORT_ID + " INTEGER,"
                + MATCHES_KEY_HOME_TEAM_NAME + " TEXT,"
                + MATCHES_KEY_AWAY_TEAM_NAME + " TEXT,"
                + MATCHES_KEY_HOME_TEAM_SCORE + " INTEGER,"
                + MATCHES_KEY_AWAY_TEAM_SCORE + " INTEGER,"
                + MATCHES_KEY_DATETIME + " DATETIME,"
                + "FOREIGN KEY(" + MATCHES_KEY_SPORT_ID + ") REFERENCES " + TABLE_SPORTS + "("
                + SPORTS_KEY_ID + ")" + ")";
        db.execSQL(CREATE_MATCHES_TABLE);

        createInitialSports(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURNAMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCHES);
        // Create tables again
        onCreate(db);
    }

    private void createInitialSports(SQLiteDatabase db) {
        // Create Football
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.SPORTS_KEY_NAME, "Football");
        values.put(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK, 1);
        values.put(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK, -1);
        values.put(DatabaseHelper.SPORTS_KEY_LONG_CLICK, 6);
        db.insert(DatabaseHelper.TABLE_SPORTS, null, values);


        // Create Basketball
        values = new ContentValues();
        values.put(DatabaseHelper.SPORTS_KEY_NAME, "Basketball");
        values.put(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK, 1);
        values.put(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK, 2);
        values.put(DatabaseHelper.SPORTS_KEY_LONG_CLICK, 3);
        db.insert(DatabaseHelper.TABLE_SPORTS, null, values);


        // Create Baseball
        values = new ContentValues();
        values.put(DatabaseHelper.SPORTS_KEY_NAME, "Baseball");
        values.put(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK, 1);
        values.put(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK, -1);
        values.put(DatabaseHelper.SPORTS_KEY_LONG_CLICK, 0);
        db.insert(DatabaseHelper.TABLE_SPORTS, null, values);

        // Create Soccer
        values = new ContentValues();
        values.put(DatabaseHelper.SPORTS_KEY_NAME, "Soccer");
        values.put(DatabaseHelper.SPORTS_KEY_SINGLE_CLICK, 1);
        values.put(DatabaseHelper.SPORTS_KEY_DOUBLE_CLICK, -1);
        values.put(DatabaseHelper.SPORTS_KEY_LONG_CLICK, 0);
        db.insert(DatabaseHelper.TABLE_SPORTS, null, values);
    }
}
