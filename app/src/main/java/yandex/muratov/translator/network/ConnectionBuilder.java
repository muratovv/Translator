package yandex.muratov.translator.network;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionBuilder {
    private String apiKey;
    private String baseUrl;
    private OkHttpClient sharedClient;

    private Retrofit.Builder commonRetrofitBuilder = new Retrofit.Builder();
    private OkHttpClient client;

    public ConnectionBuilder(String apiKey, String baseUrl, OkHttpClient sharedClient) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.sharedClient = sharedClient;
        this.client = sharedClient;
        makeBuilder();
    }

    private void makeBuilder() {
        commonRetrofitBuilder
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .baseUrl(baseUrl)
                .client(sharedClient);

    }

    public String getApiKey() {
        return apiKey;
    }

    public Retrofit.Builder getCommonRetrofitBuilder() {
        return commonRetrofitBuilder;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
