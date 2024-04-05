package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * Data Access Object (DAO) interface for interacting with the Song entity in the database.
 * Author: Parth Patel
 * Lab Section: 012
 * Date: 3 April 2024
 */
@Dao
public interface SongDao {
    /**
     * Inserts a song into the database.
     * @param song The song to be inserted.
     */
    @Insert
    void insert(Song song);
}
