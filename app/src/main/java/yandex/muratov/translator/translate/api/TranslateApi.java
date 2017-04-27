package yandex.muratov.translator.translate.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yandex.muratov.translator.translate.data.TranslateAnswer;

/**
 * Class represents yandex dictionary api
 */

public interface TranslateApi {

    /**
     * POST method for retrieve translation of text
     *
     * @param key  API key for API
     * @param lang string representation of language
     * @param text text for definition
     * @return callback with answer
     */
    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<TranslateAnswer> translate(@Field("key") String key,
                                    @Field("lang") String lang,
                                    @Field("text") String text);

}
