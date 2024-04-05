package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter class for the RecyclerView to display saved search entries.
 * Author: Parth Patel
 * Lab Section: 012
 * Date: 3 April 2024
 */
public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.SearchEntryViewHolder> {
    private List<SearchEntry> searchEntries;
    private LayoutInflater mInflater;

    /**
     * Constructor for the SavedSearchAdapter.
     * @param context The context of the activity or fragment.
     * @param searchEntries The list of search entries to be displayed.
     */
    public SavedSearchAdapter(Context context, List<SearchEntry> searchEntries) {
        this.mInflater = LayoutInflater.from(context);
        this.searchEntries = searchEntries;
    }

    @NonNull
    @Override
    public SearchEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_entry_item, parent, false);
        return new SearchEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchEntryViewHolder holder, int position) {
        // Bind data to the views in each ViewHolder
        holder.searchTermTextView.setText(searchEntries.get(position).searchTerm);
        // You can also set onClickListener here if you want to add interaction
    }

    @Override
    public int getItemCount() {
        return searchEntries.size();
    }

    /**
     * ViewHolder class to hold the views for each search entry.
     */
    public static class SearchEntryViewHolder extends RecyclerView.ViewHolder {
        TextView searchTermTextView;

        public SearchEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            searchTermTextView = itemView.findViewById(R.id.tvSearchTerm);
        }
    }
}
