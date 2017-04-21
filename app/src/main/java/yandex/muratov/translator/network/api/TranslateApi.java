package yandex.muratov.translator.network.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yandex.muratov.translator.network.data.TranslateAnswer;

public interface TranslateApi {

    @FormUrlEncoded
    @POST
    Call<TranslateAnswer> translate(@Field("key") String key,
                                    @Field("lang") String lang,
                                    @Field("text") String text);

}
