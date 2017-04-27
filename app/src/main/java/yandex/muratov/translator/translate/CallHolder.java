package yandex.muratov.translator.translate;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Holder of {@link Call}s, with replacement previous.
 *
 * @param <T>
 */
public class CallHolder<T> {
    private Call<T> call;

    /**
     * Cancel existed call
     */
    public void dropPreviousCall() {
        if (call != null) {
            if (!call.isCanceled()) {
                call.cancel();
                call = null;
            }
        }
    }

    /**
     * Enqueue new {@link Call} for processing
     *
     * @param newCall  new call for processing
     * @param callback that will attached to {@code newCall}
     */
    public void enqueue(Call<T> newCall, Callback<T> callback) {
        dropPreviousCall();
        call = newCall;
        call.enqueue(callback);
    }

}
