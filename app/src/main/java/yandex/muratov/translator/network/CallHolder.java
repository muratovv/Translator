package yandex.muratov.translator.network;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author @muratovv
 * @date 20.04.17
 */
class CallHolder<T> {
    private Call<T> call;

    public void dropPreviousCall() {
        if (call != null) {
            if (call.isExecuted()) {
                call = null;
                return;
            }
            if (!call.isCanceled()) {
                call.cancel();
                call = null;
            }
        }
    }

    public void enqueue(Call<T> newCall, Callback<T> callback) {
        dropPreviousCall();
        call = newCall;
        call.enqueue(callback);
    }

}
