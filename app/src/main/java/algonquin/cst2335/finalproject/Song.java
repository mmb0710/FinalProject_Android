package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a song in the database.
 * Author: Parth Patel
 * Lab Section: 012
 * Date: 3 April 2024
 */
@Entity(tableName = "songs")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "artistName")
    private String artistName;

    @ColumnInfo(name = "albumName")
    private String albumName;

    @ColumnInfo(name = "durationInSeconds")
    private int durationInSeconds;

    @ColumnInfo(name = "coverUrl")
    private String coverUrl;

    // Constructor
    public Song(int id, String title, String artistName, String albumName, int durationInSeconds, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.albumName = albumName;
        this.durationInSeconds = durationInSeconds;
        this.coverUrl = coverUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
