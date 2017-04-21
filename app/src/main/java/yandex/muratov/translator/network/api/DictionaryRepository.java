package yandex.muratov.translator.network.api;


import retrofit2.Call;
import yandex.muratov.translator.network.data.DictionaryAnswer;

public interface DictionaryRepository {
    Call<DictionaryAnswer> dictionary(String lang, String text);

    void closeConnection();
}
