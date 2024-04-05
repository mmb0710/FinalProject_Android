package algonquin.cst2335.finalproject.data.repository;

import android.util.Log;

import algonquin.cst2335.finalproject.data.model.wordquiz.WordQuiz;
import algonquin.cst2335.finalproject.data.remote.wordquiz.WordQuizApiService;
import algonquin.cst2335.finalproject.util.QuizResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordQuizRepo {
    private static WordQuizRepo instance;
    private static final String TAG = "WordQuizRepo";
    private WordQuizApiService wordQuizApiService = new WordQuizApiService();
    private WordQuiz wordQuiz;
    public static WordQuizRepo getInstance(){
        if(instance == null){
            instance = new WordQuizRepo();
        }
        return instance;
    }
    public void getWordQuizFromApi(String level, String area, QuizResponseListener quizListener){
        Call<WordQuiz> wordQuizCall = wordQuizApiService.getQuiz(level, area);
        wordQuizCall.enqueue(new Callback<WordQuiz>() {
            @Override
            public void onResponse(Call<WordQuiz> call, Response<WordQuiz> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG,"Code " + response.code());
                    quizListener.onFail("Code " + response.code());
                } else {
                    wordQuiz = response.body();
                    quizListener.onSuccess(wordQuiz);
                }
            }

            @Override
            public void onFailure(Call<WordQuiz> call, Throwable t) {
                Log.d(TAG,"onFailure:" + t.getMessage());
            }
        });
    }
}
