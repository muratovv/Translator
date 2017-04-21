package yandex.muratov.translator.network;

import retrofit2.Call;
import yandex.muratov.translator.BuildConfig;
import yandex.muratov.translator.network.api.BaseNetworkRepository;
import yandex.muratov.translator.network.api.DictionaryApi;
import yandex.muratov.translator.network.api.DictionaryRepository;
import yandex.muratov.translator.network.data.DictionaryAnswer;
import yandex.muratov.translator.network.data.Language;

public class YandexDictionaryRepository extends BaseNetworkRepository implements DictionaryRepository {

    private DictionaryApi api;
    private String uiLanguage = "";

    public YandexDictionaryRepository(ConnectionBuilder builder, Language uiLanguage) {
        super(builder);
        api = retrofit.create(DictionaryApi.class);
        this.uiLanguage = uiLanguage.getCode();
    }

    public void setUiLanguage(String uiLanguage) {
        this.uiLanguage = uiLanguage;
    }

    @Override
    public Call<DictionaryAnswer> dictionary(String lang, String text) {
        return api.dictionary(getApiKey(), lang, uiLanguage, text);
    }

    @Override
    public void closeConnection() {
        getClient().dispatcher().cancelAll();
        getClient().dispatcher().executorService().shutdown();
    }

    @Override
    protected String initApiKey() {
        return BuildConfig.YANDEX_DICTIONARY_API_TOKEN;
    }

    @Override
    protected String initBaseUrl() {
        return "https://dictionary.yandex.net/api/v1/dicservice.json/lookup";
    }
}
