package algonquin.cst2335.finalproject;

/**
 * Represents an artist with their ID, name, and tracklist URL.
 * Used to encapsulate the data for an artist fetched from an API or a database.
 *
 * @author Parth Patel
 * @version 1.0
 * @since 3 April 2024
 * @lab_section 012
 */
public class Artist {
    private int id;           // Unique identifier for the artist
    private String name;      // Name of the artist
    private String tracklist; // URL to the artist's songs tracklist

    /**
     * Constructs an Artist with the specified details.
     *
     * @param id        the unique identifier of the artist
     * @param name      the name of the artist
     * @param tracklist the URL to the artist's songs tracklist
     */
    public Artist(int id, String name, String tracklist) {
        this.id = id;
        this.name = name;
        this.tracklist = tracklist;
    }

    /**
     * Gets the unique identifier of the artist.
     *
     * @return the artist's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the artist.
     *
     * @return the artist's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the URL to the artist's songs tracklist.
     *
     * @return the URL to the artist's tracklist
     */
    public String getTracklist() {
        return tracklist;
    }
}
