package yandex.muratov.translator.network;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import yandex.muratov.translator.network.api.DictionaryApi;
import yandex.muratov.translator.network.api.DictionaryRepository;
import yandex.muratov.translator.network.data.DictionaryAnswer;

public class YandexDictionaryRepository implements DictionaryRepository {

    private DictionaryApi api;
    private String apiKey;
    private String uiLanguage = "";
    private OkHttpClient client;

    public YandexDictionaryRepository(ConnectionBuilder builder, String uiLanguage) {
        Retrofit retrofit = builder.getCommonRetrofitBuilder().build();
        api = retrofit.create(DictionaryApi.class);
        this.uiLanguage = uiLanguage;
        this.apiKey = builder.getApiKey();
        this.client = builder.getClient();
    }

    public void setUiLanguage(String uiLanguage) {
        this.uiLanguage = uiLanguage;
    }

    @Override
    public Call<DictionaryAnswer> dictionary(String lang, String text) {
        return api.dictionary(apiKey, lang, uiLanguage, text);
    }

    @Override
    public void closeConnection() {
        client.dispatcher().cancelAll();
        client.dispatcher().executorService().shutdown();
    }
}
