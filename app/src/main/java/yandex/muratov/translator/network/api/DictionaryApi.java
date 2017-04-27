package yandex.muratov.translator.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yandex.muratov.translator.network.data.DictionaryAnswer;

/**
 * Class represents yandex dictionary api
 */
public interface DictionaryApi {

    /**
     * Get method for retrieve information about text
     *
     * @param key  API key for API
     * @param lang string representation of language
     * @param ui   ui language of response
     * @param text text for definition
     * @return callback with answer
     */
    @GET("/api/v1/dicservice.json/lookup")
    Call<DictionaryAnswer> dictionary(@Query("key") String key,
                                      @Query("lang") String lang,
                                      @Query("ui") String ui,
                                      @Query(value = "text", encoded = true) String text);
}
