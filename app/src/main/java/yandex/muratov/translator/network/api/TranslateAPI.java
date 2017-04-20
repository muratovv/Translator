package yandex.muratov.translator.network.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.TranslateAnswer;

public interface TranslateAPI {

    @FormUrlEncoded
    @POST("https://translate.yandex.net/api/v1.5/tr.json/translate")
    Call<TranslateAnswer> translate(@Field("key") String key,
                                    @Field("lang") String lang,
                                    @Field("text") String text
    );

    @GET("https://dictionary.yandex.net/api/v1/dicservice.json/lookup")
    Call<DictionaryAnswer> dictionary(@Query("key") String key,
                                      @Query("lang") String lang,
                                      @Query("ui") String ui,
                                      @Query(value = "text", encoded = true) String text);

}
