package yandex.muratov.translator.translate.net;

import retrofit2.Call;
import yandex.muratov.translator.BuildConfig;
import yandex.muratov.translator.translate.api.DictionaryApi;
import yandex.muratov.translator.translate.api.DictionaryRepository;
import yandex.muratov.translator.translate.data.DictionaryAnswer;
import yandex.muratov.translator.translate.data.Language;

public class YandexDictionaryRepository extends BaseNetworkRepository implements DictionaryRepository {

    private DictionaryApi api;
    private String uiLanguage = "";

    public YandexDictionaryRepository(ConnectionBuilder builder, Language uiLanguage) {
        super(builder);
        api = retrofit.create(DictionaryApi.class);
        this.uiLanguage = uiLanguage.getCode();
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
        return "https://dictionary.yandex.net";
    }
}
