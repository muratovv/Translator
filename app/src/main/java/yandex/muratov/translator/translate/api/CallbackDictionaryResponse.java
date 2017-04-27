package yandex.muratov.translator.translate.api;


import yandex.muratov.translator.translate.data.DictionaryAnswer;

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
