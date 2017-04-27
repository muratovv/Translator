package yandex.muratov.translator.network.api;


import retrofit2.Call;
import yandex.muratov.translator.network.data.DictionaryAnswer;

/**
 * Abstract interface for retrieving {@link DictionaryAnswer}
 */
public interface DictionaryRepository {

    /**
     * Request for retrieve {@link DictionaryAnswer}
     *
     * @param lang raw representation of language
     * @param text text for description
     * @return callback with answer
     */
    Call<DictionaryAnswer> dictionary(String lang, String text);

    /**
     * Close connection with api
     */
    void closeConnection();
}
