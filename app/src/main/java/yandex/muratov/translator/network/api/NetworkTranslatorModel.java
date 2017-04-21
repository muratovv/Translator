package yandex.muratov.translator.network.api;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public interface NetworkTranslatorModel extends ModelObserver {
    /**
     * Drop last request
     *
     */
    void denyLastRequest();

    /**
     * Close all connections for closing
     */
    void dropConnection();


    /**
     * Request for dictionary API
     * @param rawLang pair of source and target language, ex: \"en-fr\"
     * @param text source text for translation
     */
    void translateRequest(String rawLang, String text);

    /**
     * Request for dictionary API
     * @param rawLang pair of source and target language, ex: \"en-fr\"
     * @param uiLang language of data representation
     * @param text source text for translation
     */
    void dictionaryRequest(String rawLang, String uiLang, String text);
}
