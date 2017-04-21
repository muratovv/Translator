package yandex.muratov.translator.network;

import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionBuilder {
    private static int CACHE_SIZE_MB = 10;

    private String apiKey;
    private String baseUrl;
    private OkHttpClient sharedClient;

    private Retrofit.Builder commonRetrofitBuilder = new Retrofit.Builder();

    public ConnectionBuilder(File cahceDir, String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        makeOkHttpClient(cahceDir);
        makeBuilder();
    }

    private void makeOkHttpClient(File cacheDir) {
        sharedClient = new OkHttpClient.Builder()
                .addInterceptor(new DeferredRequestInterceptor())
                .cache(new Cache(cacheDir, CACHE_SIZE_MB * 1024 * 1024))
                .build();
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
        return sharedClient;
    }

}
