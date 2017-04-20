package yandex.muratov.translator.network.api;


import yandex.muratov.translator.network.data.DictionaryAnswer;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public interface CallbackDictionaryResponse {
    void onDictionaryResponse(DictionaryAnswer response);

    void onDictionaryRequestFail(Throwable error);
}
