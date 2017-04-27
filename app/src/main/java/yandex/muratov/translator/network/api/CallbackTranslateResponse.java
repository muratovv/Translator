package yandex.muratov.translator.network.api;


import yandex.muratov.translator.network.data.TranslateAnswer;

/**
 * Class represents response from {@link TranslateApi}
 */
public interface CallbackTranslateResponse {

    /**
     * Callback called on correct response from API
     *
     * @param response retrieved object
     */
    void onTranslateResponse(TranslateAnswer response);

    /**
     * Callback called when request was failed
     *
     * @param error request cause
     */
    void onTranslateRequestFail(Throwable error);
}
