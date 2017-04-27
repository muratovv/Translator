package yandex.muratov.translator.network.api;

/**
 *
 */
public interface NetworkTranslatorModel extends NetworkModelObserver, Droppable {

    /**
     * Request for dictionary API
     *
     * @param rawLang pair make source and target language, ex: \"en-fr\"
     * @param text    source text for translation
     */
    void translateRequest(String rawLang, String text);

    /**
     * Request for dictionary API
     *
     * @param rawLang pair make source and target language, ex: \"en-fr\"
     * @param text    source text for translation
     */
    void dictionaryRequest(String rawLang, String text);
}
