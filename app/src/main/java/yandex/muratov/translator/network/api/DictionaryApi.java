package yandex.muratov.translator.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yandex.muratov.translator.network.data.DictionaryAnswer;

public interface DictionaryApi {
    @GET("/api/v1/dicservice.json/lookup")
    Call<DictionaryAnswer> dictionary(@Query("key") String key,
                                      @Query("lang") String lang,
                                      @Query("ui") String ui,
                                      @Query(value = "text", encoded = true) String text);
}
