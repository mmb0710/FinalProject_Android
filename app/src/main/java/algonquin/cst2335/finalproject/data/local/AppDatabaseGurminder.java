package algonquin.cst2335.finalproject.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import algonquin.cst2335.finalproject.data.model.wordsapi.Word;

/**
 * Room database class for the application.
 *
 * <p>This class defines the Room database using the RoomDatabase annotation.
 * It includes the DAO interface for accessing the Word entities and follows the singleton pattern
 * for accessing the database instance.</p>
 *
 * <p>Author: Gurminder Singh Badwal</p>
 * <p>Lab Section: 012</p>
 * <p>Creation Date: 4th of April 2024</p>
 */
@Database(entities = {Word.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabaseGurminder extends RoomDatabase {

    /**
     * Returns the DAO interface for accessing the Word entities.
     *
     * @return The WordsDao object.
     */
    public abstract WordsDao wordsDao();

    /**
     * Singleton pattern for accessing the database instance.
     *
     * @param context The application context.
     * @return The instance of the AppDatabase.
     */
    public static AppDatabaseGurminder getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseGurminder.class, "dictionaryApp").build();
        }
        return instance;
    }

    private static AppDatabaseGurminder instance;
}
