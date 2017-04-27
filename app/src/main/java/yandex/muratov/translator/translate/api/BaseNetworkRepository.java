package yandex.muratov.translator.translate.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import yandex.muratov.translator.translate.ConnectionBuilder;

/**
 * Class represents common context for network repositories
 */
public abstract class BaseNetworkRepository {

    private String apiKey;
    private OkHttpClient client;

    protected Retrofit retrofit;

    public BaseNetworkRepository(ConnectionBuilder builder) {
        apiKey = initApiKey();
        retrofit = builder
                .getCommonRetrofitBuilder()
                .baseUrl(initBaseUrl())
                .build();
        client = builder.getClient();

    }

    /**
     * @return access key for API
     */
    protected abstract String initApiKey();

    /**
     * @return host path of url
     */
    protected abstract String initBaseUrl();

    public String getApiKey() {
        return apiKey;
    }

    /**
     * @return {@link OkHttpClient} associated with repository
     */
    public OkHttpClient getClient() {
        return client;
    }
}
