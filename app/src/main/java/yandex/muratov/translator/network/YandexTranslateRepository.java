package yandex.muratov.translator.network;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import yandex.muratov.translator.network.api.TranslateApi;
import yandex.muratov.translator.network.api.TranslateRepository;
import yandex.muratov.translator.network.data.TranslateAnswer;

public class YandexTranslateRepository implements TranslateRepository {

    private TranslateApi api;
    private String apiKey;
    private OkHttpClient client;

    public YandexTranslateRepository(ConnectionBuilder builder, String uiLanguage) {
        Retrofit retrofit = builder.getCommonRetrofitBuilder().build();
        api = retrofit.create(TranslateApi.class);
        this.apiKey = builder.getApiKey();
        this.client = builder.getClient();
    }

    @Override
    public Call<TranslateAnswer> translate(String lang, String text) {
        return api.translate(apiKey, lang, text);
    }

    @Override
    public void closeConnection() {
        client.dispatcher().cancelAll();
        client.dispatcher().executorService().shutdown();
    }
}
