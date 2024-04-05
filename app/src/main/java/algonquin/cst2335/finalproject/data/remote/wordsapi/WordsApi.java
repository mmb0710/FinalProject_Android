package algonquin.cst2335.finalproject.data.remote.wordsapi;

import algonquin.cst2335.finalproject.data.model.wordsapi.Word;
import algonquin.cst2335.finalproject.util.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WordsApi {
    @Headers({
            "X-RapidAPI-Host: " + Constants.WORDS_X_RAPID_API_HOST,
            "X-RapidAPI-Key: " + Constants.X_RAPID_API_KEY,
    })
    @GET("{word}")
    Call<Word> getWord(@Path("word") String word);
}
