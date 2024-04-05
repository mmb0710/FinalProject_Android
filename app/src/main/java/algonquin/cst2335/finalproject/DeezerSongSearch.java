package algonquin.cst2335.finalproject;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DeezerSongSearch is an activity class that handles searching for artists using the Deezer API.
 * It allows users to input a search term, display results in a RecyclerView, and manage favorites.
 * This activity handles user interactions, network requests, and data persistence.
 *
 * @author Parth Patel
 * @since 3 April 2024
 * @lab_section 012
 */
public class DeezerSongSearch extends AppCompatActivity {
    private List<Artist> artistList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArtistsAdapter adapter;
    private EditText searchEditText;
    private Button searchButton;
    private Button clearButton;

    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI,
     * setting up listeners, and initializing class scope variables.
     *
     * @param savedInstanceState If the activity is being re-initialized after being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_song_api);

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.artistsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArtistsAdapter(artistList);
        recyclerView.setAdapter(adapter);

        // Initialize views and set onClickListeners
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        clearButton = findViewById(R.id.clearButton);

        searchButton.setOnClickListener(view -> searchArtists(searchEditText.getText().toString()));
        clearButton.setOnClickListener(view -> clearSavedSongs());

        // Help button onClickListener
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(view -> showHelpDialog());

        // Load previous search term, if any
        loadPreviousSearchTerm();
    }

    /**
     * Searches for artists using the Deezer API and updates the RecyclerView with the results.
     *
     * @param query The search term used to find artists.
     */
    private void searchArtists(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.deezer.com/search/artist/?q=" + Uri.encode(query);

        // Create a JsonObjectRequest to retrieve artist data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Extract artist data from JSON response
                        JSONArray artistsArray = response.getJSONArray("data");
                        artistList.clear();
                        for (int i = 0; i < artistsArray.length(); i++) {
                            JSONObject artistObj = artistsArray.getJSONObject(i);
                            Artist artist = new Artist(
                                    artistObj.getInt("id"),
                                    artistObj.getString("name"),
                                    artistObj.getString("tracklist")
                            );
                            artistList.add(artist);
                        }
                        adapter.notifyDataSetChanged();
                        saveSearchTerm(query); // Save search term for future reference
                        Toast.makeText(DeezerSongSearch.this, "Search Completed", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Artist Search", "Error: " + error.toString())
        );

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    /**
     * Clears the saved search term, artist list, and search field.
     */
    private void clearSavedSongs() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("searchTerm"); // Remove saved search term
        editor.apply();

        artistList.clear(); // Clear artist list
        adapter.notifyDataSetChanged(); // Notify adapter of data change

        searchEditText.setText(""); // Clear search field
        Toast.makeText(DeezerSongSearch.this, "Saved songs cleared", Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads the previous search term from SharedPreferences and sets it in the search field.
     */
    private void loadPreviousSearchTerm() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String previousSearchTerm = sharedPreferences.getString("searchTerm", "");
        searchEditText.setText(previousSearchTerm);
    }

    /**
     * Saves the search term in SharedPreferences for future reference.
     *
     * @param searchTerm The search term to save.
     */
    private void saveSearchTerm(String searchTerm) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchTerm", searchTerm);
        editor.apply();
    }

    // Override onCreateOptionsMenu to inflate the menu_deezer_song_search.xml layout
    // Override onOptionsItemSelected to handle menu item clicks

    /**
     * Displays a help dialog to guide users on using the search functionality.
     */
    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help_button_label);
        builder.setMessage("Enter an artist's name in the search field and tap 'Search' to find music by that artist. " +
                "Use the 'Clear' button to reset the search and clear the results. " +
                "You can save your favorite songs by tapping the 'Save' button next to each result.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    // Other methods: onOptionsItemSelected, onCreateOptionsMenu, etc.
    // Please ensure to add JavaDoc comments for these methods as well.

}
