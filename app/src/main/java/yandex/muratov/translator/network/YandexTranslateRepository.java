package yandex.muratov.translator.network;

import retrofit2.Call;
import yandex.muratov.translator.BuildConfig;
import yandex.muratov.translator.network.api.BaseNetworkRepository;
import yandex.muratov.translator.network.api.TranslateApi;
import yandex.muratov.translator.network.api.TranslateRepository;
import yandex.muratov.translator.network.data.TranslateAnswer;

public class YandexTranslateRepository extends BaseNetworkRepository implements TranslateRepository {

    private TranslateApi api;

    public YandexTranslateRepository(ConnectionBuilder builder) {
        super(builder);
        api = retrofit.create(TranslateApi.class);
    }

    @Override
    protected String initApiKey() {
        return BuildConfig.YANDEX_TRANSLATOR_API_TOKEN;
    }

    @Override
    protected String initBaseUrl() {
        return "https://translate.yandex.net/api/v1.5/tr.json/translate";
    }

    @Override
    public Call<TranslateAnswer> translate(String lang, String text) {
        return api.translate(getApiKey(), lang, text);
    }

    @Override
    public void closeConnection() {
        getClient().dispatcher().cancelAll();
        getClient().dispatcher().executorService().shutdown();
    }
}
