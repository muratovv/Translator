package yandex.muratov.translator.network;

import retrofit2.Call;
import retrofit2.Callback;

class CallHolder<T> {
    private Call<T> call;

    public void dropPreviousCall() {
        if (call != null) {
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
