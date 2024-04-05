package algonquin.cst2335.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper class that manages database creation and version management.
 *
 * @author Parth Patel
 * @version 1.0
 * @since 3 April 2024
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DeezerSongDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";
    private static final String TABLE_SONG_DETAILS = "songdetails";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_ARTIST_NAME = "artistName";
    private static final String COLUMN_TRACKLIST_URL = "tracklistUrl";
    private static final String COLUMN_SONG_TITLE = "songTitle";
    private static final String COLUMN_DURATION = "duration";

    /**
     * Constructor for creating a instance of database helper.
     *
     * @param context the context in which to operate
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ARTIST_NAME + " TEXT,"
                + COLUMN_TRACKLIST_URL + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);

        String CREATE_SONG_DETAILS_TABLE = "CREATE TABLE " + TABLE_SONG_DETAILS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ARTIST_NAME + " TEXT,"
                + COLUMN_SONG_TITLE + " TEXT,"
                + COLUMN_DURATION + " INTEGER" + ")";
        db.execSQL(CREATE_SONG_DETAILS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     * This method will only be called if a database already exists on disk with the same DATABASE_NAME,
     * but the DATABASE_VERSION is different than the version of the database that exists on disk.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG_DETAILS);
        onCreate(db);
    }

    /**
     * Adds a favorite artist along with their tracklist URL to the database.
     *
     * @param artistName   The name of the artist.
     * @param tracklistUrl The URL to the artist's tracklist.
     */
    public void addFavorite(String artistName, String tracklistUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ARTIST_NAME, artistName);
        values.put(COLUMN_TRACKLIST_URL, tracklistUrl);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    /**
     * Retrieves all favorite artists from the database.
     *
     * @return A Cursor object, which is positioned before the first entry.
     */
    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
    }

    /**
     * Deletes a favorite artist from the database based on the artist's name.
     *
     * @param artistName The name of the artist to delete.
     */
    public void deleteFavorite(String artistName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, COLUMN_ARTIST_NAME + "=?", new String[]{artistName});
        db.close();
    }
}
