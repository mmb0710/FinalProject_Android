package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a search entry in the database.
 * Author: Parth Patel
 * Lab Section: 012
 * Date: 3 April 2024
 */
@Entity(tableName = "search_entries")
public class SearchEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "search_term")
    public String searchTerm;

    @ColumnInfo(name = "definitions")
    public String definitions;
}
