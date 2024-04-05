package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for the RecyclerView to display a list of Artist objects.
 * It binds the Artist data to the views defined in the artist_item layout.
 *
 * @author Parth Patel
 * @version 1.0
 * @since 3 April 2024
 * @lab_section 012
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private List<Artist> artists;

    /**
     * Constructor for the ArtistsAdapter.
     *
     * @param artists The list of Artist objects to display in the RecyclerView.
     */
    public ArtistsAdapter(List<Artist> artists) {
        this.artists = artists;
    }

    /**
     * Called when RecyclerView needs a new {@link ArtistViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ArtistViewHolder that holds a View of the given view type.
     */
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.nameTextView.setText(artist.getName());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return artists.size();
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        /**
         * Constructor for the ArtistViewHolder.
         *
         * @param itemView The view inflated from the artist_item layout.
         */
        public ArtistViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewArtistName);
        }
    }
}
