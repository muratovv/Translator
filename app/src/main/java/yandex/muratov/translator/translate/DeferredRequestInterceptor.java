package yandex.muratov.translator.translate;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DeferredRequestInterceptor implements Interceptor {

    private double deferredSeconds = 1.;
    private long deferredMillis = (Double.valueOf(deferredSeconds * 1000)).longValue();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        waitBeforeRequest();

        return chain.proceed(request);
    }

    private void waitBeforeRequest() {
        try {
            Thread.sleep(deferredMillis);
        } catch (InterruptedException ignored) {
        }
    }
}
