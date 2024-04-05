package algonquin.cst2335.finalproject.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.data.model.wordsapi.Word;
import algonquin.cst2335.finalproject.databinding.ActivityDetailBinding;
import algonquin.cst2335.finalproject.ui.adapter.ResultsAdapter;
import algonquin.cst2335.finalproject.ui.viewmodel.MainViewModelGurminder;
import com.google.gson.Gson;

/**
 * DetailActivity displays detailed information about a word.
 *
 * <p>This activity displays the word itself, its pronunciation,
 * and other details such as definitions, examples, and synonyms.</p>
 *
 * <p>Author: Gurminder Singh Badwal</p>
 * <p>Lab Section: 012</p>
 * <p>Creation Date: 4th of April 2024</p>
 */
public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private final static String TAG = "DetailActivity";
    private ResultsAdapter mAdapter;
    private Word mWord;
    private MainViewModelGurminder mViewModel;
    private Boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try {
            initValues();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Initializes values and sets up the UI.
     */
    void initValues() {
        mViewModel = new ViewModelProvider(this).get(MainViewModelGurminder.class);
        mViewModel.init(getApplication());
        Gson gson = new Gson();
        Intent i = getIntent();
        mWord = gson.fromJson(i.getStringExtra("wordItem"), Word.class);
        isFav = i.getBooleanExtra("favourite", false);
        mAdapter = new ResultsAdapter(getApplicationContext());
        setData(mWord);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final Boolean[] isfav = {isFav};
        binding.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isfav[0]) {
                    binding.ivStar.setImageResource(R.drawable.ic_baseline_star_36);
                    mViewModel.insertWord(mWord);
                    isfav[0] = true;
                } else {
                    binding.ivStar.setImageResource(R.drawable.ic_baseline_star_outline_36);
                    mViewModel.removeWord(mWord);
                    isfav[0] = false;
                }
            }
        });

    }

    /**
     * Sets data to the UI elements.
     *
     * @param w The Word object to display.
     */
    void setData(Word w) {
        binding.tvWord.setText(w.getWord());
        if (isFav) {
            binding.ivStar.setImageResource(R.drawable.ic_baseline_star_36);
        } else {
            binding.ivStar.setImageResource(R.drawable.ic_baseline_star_outline_36);
        }
        if (w.getPronunciation().getAll() != null) {
            TextView tv = createPronTextView("all:" + " /" + w.getPronunciation().getAll() + "/");
            binding.llPronunciation.addView(tv);
        }
        if (w.getPronunciation().getNoun() != null) {
            TextView tv = createPronTextView("noun:" + " /" + w.getPronunciation().getNoun() + "/");
            binding.llPronunciation.addView(tv);
        }
        if (w.getPronunciation().getVerb() != null) {
            TextView tv = createPronTextView("verb:" + " /" + w.getPronunciation().getVerb() + "/");
            binding.llPronunciation.addView(tv);
        }
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        binding.rvResults.setLayoutManager(linearLayout);
        binding.rvResults.setAdapter(mAdapter);
        mAdapter.setResultsList(mWord.getResults());
    }

    /**
     * Creates a TextView for displaying pronunciation information.
     *
     * @param text The text to display.
     * @return The created TextView.
     */
    TextView createPronTextView(String text) {
        TextView tv = new TextView(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(R.style.PronunciationTextStyle);
        } else {
            tv.setTextAppearance(getApplicationContext(), R.style.PronunciationTextStyle);
        }
        tv.setPadding(10, 5, 10, 5);
        tv.setText(text);
        return tv;
    }
}
