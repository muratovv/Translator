package yandex.muratov.translator.network.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import yandex.muratov.translator.network.ConnectionBuilder;

public abstract class BaseNetworkRepository {

    private String apiKey;

    protected Retrofit retrofit;
    private OkHttpClient client;

    public BaseNetworkRepository(ConnectionBuilder builder) {
        apiKey = initApiKey();
        retrofit = builder
                .getCommonRetrofitBuilder()
                .baseUrl(initBaseUrl())
                .build();
        client = builder.getClient();

    }

    protected abstract String initApiKey();

    protected abstract String initBaseUrl();

    public String getApiKey() {
        return apiKey;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
