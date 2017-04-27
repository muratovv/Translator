package yandex.muratov.translator.translate.api;


import retrofit2.Call;
import yandex.muratov.translator.translate.data.TranslateAnswer;

/**
 * Abstract interface for retrieving {@link TranslateAnswer}
 */

public interface TranslateRepository {

    /**
     * Request for retrieve {@link TranslateAnswer}
     *
     * @param lang raw representation of language
     * @param text text for translation
     * @return callback with answer
     */
    Call<TranslateAnswer> translate(String lang, String text);

    /**
     * Close connection with api
     */
    void closeConnection();
}
