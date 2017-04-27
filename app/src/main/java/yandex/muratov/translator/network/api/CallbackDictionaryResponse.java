package yandex.muratov.translator.network.api;


import yandex.muratov.translator.network.data.DictionaryAnswer;

/**
 * Class represents response from {@link DictionaryApi}
 */
public interface CallbackDictionaryResponse {

    /**
     * Callback called on correct response from API
     *
     * @param response retrieved object
     */
    void onDictionaryResponse(DictionaryAnswer response);

    /**
     * Callback called when request was failed
     *
     * @param error request cause
     */
    void onDictionaryRequestFail(Throwable error);
}
