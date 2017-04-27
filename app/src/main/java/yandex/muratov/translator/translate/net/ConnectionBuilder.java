package yandex.muratov.translator.translate.net;

import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yandex.muratov.translator.BuildConfig;

public class ConnectionBuilder {
    private static int CACHE_SIZE_MB = 1;

    private OkHttpClient okHttpClient;

    private Retrofit.Builder commonRetrofitBuilder = new Retrofit.Builder();

    public ConnectionBuilder(File cacheDir) {
        makeOkHttpClient(cacheDir);
        makeBuilder();
    }

    private void makeOkHttpClient(File cacheDir) {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .setLevel(Level.BASIC)
                        .log(Platform.INFO)
                        .request("request")
                        .response("response")
                        .build())
                .cache(new Cache(cacheDir, CACHE_SIZE_MB * 1024 * 1024))
                .build();
    }

    private void makeBuilder() {
        commonRetrofitBuilder
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(okHttpClient);

    }

    public Retrofit.Builder getCommonRetrofitBuilder() {
        return commonRetrofitBuilder;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

}
