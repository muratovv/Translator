package yandex.muratov.translator.network.api;


import yandex.muratov.translator.network.data.TranslateAnswer;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public interface CallbackTranslateResponse {
    void onTranslateResponse(TranslateAnswer response);

    void onTranslateRequestFail(Throwable error);
}
