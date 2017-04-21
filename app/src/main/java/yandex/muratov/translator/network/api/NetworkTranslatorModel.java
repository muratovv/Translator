package yandex.muratov.translator.network.api;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public interface NetworkTranslatorModel extends ModelObserver, Droppable {

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
