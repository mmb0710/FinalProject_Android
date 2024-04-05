package algonquin.cst2335.finalproject.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.*;
import algonquin.cst2335.finalproject.data.model.wordquiz.WordQuiz;
import algonquin.cst2335.finalproject.data.model.wordsapi.Word;
import algonquin.cst2335.finalproject.ui.adapter.FavouritesAdapter;
import algonquin.cst2335.finalproject.ui.viewmodel.MainViewModelGurminder;
import algonquin.cst2335.finalproject.util.QuizResponseListener;
import algonquin.cst2335.finalproject.util.WordResponseListener;

import algonquin.cst2335.finalproject.databinding.ActivityMainGurminderBinding;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * MainActivity serves as the main entry point of the application.
 *
 * <p>This activity allows users to search for words, view their favorites,
 * and take quizzes. It also provides functionalities like removing favorites
 * and checking for internet connection.</p>
 *
 * <p>Author: Gurminder Singh Badwal</p>
 * <p>Lab Section: 012</p>
 * <p>Creation Date: 4th April 2024</p>
 */
public class MainActivityGurminder extends AppCompatActivity {
    private ActivityMainGurminderBinding binding;
    private static final String TAG = "MainActivity";
    private MainViewModelGurminder mViewModel;
    private FavouritesAdapter mFavAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainGurminderBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try {
            initValues();
        } catch(Exception e){
            Log.e(TAG,e.toString());
        }	Button help =findViewById(R.id.Help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpInstructions();
            }
        });
    } private void showHelpInstructions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help Instructions");
        builder.setMessage("1. Search for a word in the search tab.\n\n"
                + "2. Take a vocabulary test by pressing the button at the bottom.\n\n"
                + "3. Check the results of the vocabulary test.\n\n"
                + "4. Save your words for future reference even without an Internet connection by clicking the star button at the top left corner after entering the word for search.\n\n"
                + "5. Delete your favorites by long-pressing the word listed and selecting 'remove' to remove that word. Select 'remove all' if you want to remove all of them.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Close the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Initializes values and sets up the UI.
     */
    private void initValues() {
        mViewModel = new ViewModelProvider(this).get(MainViewModelGurminder.class);
        mViewModel.init(getApplication());
        createSearchBarEvent();
        createFavouritesRV();
        createQuizEvent();
        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Word> listW = mFavAdapter.getSelectedWords();
                if (listW.size() > 0){
                    mViewModel.removeListofWords(listW, new OnRemoveSelectedWords() {
                        @Override
                        public void clear(boolean result) {
                            if(result){
                                mFavAdapter.clearSelectedWords();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"Please select word you want to remove", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Initializes and configures the RecyclerView for displaying favorites.
     */
    private void createFavouritesRV(){
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getApplicationContext());
        binding.rvFavourites.setLayoutManager(layoutManager);
        mFavAdapter = new FavouritesAdapter(getApplicationContext(),
                new FavouritesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Word item) {
                        openDetailActivity(item, true);
                    }
                });
        binding.rvFavourites.setAdapter(mFavAdapter);
        mViewModel.getFavWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                if(words != null){
                    mFavAdapter.setFavWordsList(words);
                } else {
                    Log.d(TAG, "words == null");
                }
            }
        });
    }

    /**
     * Sets up the search bar event listener.
     */
    private void createSearchBarEvent(){
        binding.svSearchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Single<Word> w = mViewModel.findWord(query);
                w.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Word>() {
                            @Override
                            public void onSuccess(@NonNull Word word) {
                                if (word != null){
                                    openDetailActivity(word, true);
                                } else {
                                    Log.e(TAG, "onSuccess " + query);
                                }
                            }
                            @Override
                            public void onError(@NonNull Throwable e) {
                                if (isConnected()){
                                    getWordFromApi(query);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Please check your internet connection and try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mFavAdapter.filter(newText);
                return true;
            }
        });
    }

    /**
     * Opens the DetailActivity with the provided word.
     * @param word The Word object to display.
     * @param isFav Indicates whether the word is a favorite.
     */
    private void openDetailActivity(Word word, Boolean isFav){
        Gson gson = new Gson();
        String myJson = gson.toJson(word);
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("wordItem", myJson);
        if (isFav){
            intent.putExtra("favourite", true);
        }
        startActivity(intent);
    }

    /**
     * Retrieves a word from the API.
     * @param text The word to search for.
     */
    private void getWordFromApi(String text){
        mViewModel.getWord(text, new WordResponseListener(){
            @Override
            public void onSuccess(Word word) {
                if (word.getResults() != null){
                    try {
                        openDetailActivity(word, false);
                    } catch(Exception e){
                        Log.e(TAG, e.toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Word not found", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFail(String s) {
                Log.d(TAG,s);
                Toast.makeText(getApplicationContext(),"Word not found", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(Throwable t) {
                Log.e(TAG,t.getMessage());
                Toast.makeText(getApplicationContext(),"Error " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Checks for internet connection.
     * @return True if there's an active internet connection, otherwise false.
     */
    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Interface for removing selected words.
     */
    public interface OnRemoveSelectedWords{
        void clear(boolean result);
    }

    /**
     * Sets up the event listener for starting a quiz.
     */
    private void createQuizEvent(){
        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()){
                    openQuizDialog();
                } else {
                    Toast.makeText(getApplicationContext(),"Please check your internet connection and try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Displays the quiz dialog for selecting quiz parameters.
     */
    public void openQuizDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivityGurminder.this);
        View mView = getLayoutInflater().inflate(R.layout.quiz_dialog, null);
        final Spinner mLevelSpinner = mView.findViewById(R.id.spn_level);
        final Spinner mAreaSpinner = mView.findViewById(R.id.spn_area);
        mBuilder.setTitle("Choose level and area for your test");
        ArrayAdapter<String> mlevelAdapter = new ArrayAdapter<>(MainActivityGurminder.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.levelList));
        mlevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevelSpinner.setAdapter(mlevelAdapter);
        ArrayAdapter<String> mAreaAdapter = new ArrayAdapter<>(MainActivityGurminder.this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.areaList));
        mAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAreaSpinner.setAdapter(mAreaAdapter);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String level = mLevelSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
                String area = mAreaSpinner.getSelectedItem().toString().toLowerCase(Locale.ROOT);
                mViewModel.getWordQuizFromApi(level, area, new QuizResponseListener() {
                    @Override
                    public void onSuccess(WordQuiz wordQuiz) {
                        Gson gson = new Gson();
                        String myJson = gson.toJson(wordQuiz);
                        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                        intent.putExtra("quiz", myJson);
                        startActivity(intent);
                    }
                    @Override
                    public void onFail(String e) {
                        Log.d(TAG, e);
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG,t.getMessage());
                    }
                });
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
