package yandex.muratov.translator.network.api;


import retrofit2.Call;
import yandex.muratov.translator.network.data.TranslateAnswer;

public interface TranslateRepository {
    Call<TranslateAnswer> translate(String lang, String text);

    void closeConnection();
}
