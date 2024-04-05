package algonquin.cst2335.finalproject.data.remote.wordquiz;

import algonquin.cst2335.finalproject.data.model.wordquiz.WordQuiz;
import algonquin.cst2335.finalproject.util.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordQuizApiService {
    private WordQuizApi api;
    public WordQuizApiService(){
        api = new Retrofit.Builder()
                .baseUrl(Constants.WORDQUIZ_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WordQuizApi.class);
    }
    public Call<WordQuiz> getQuiz(String level, String area){
        return api.getQuiz(level, area);
    }
}
