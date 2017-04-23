package yandex.muratov.translator.network.api;

/**
 * @author @muratovv
 * @date 20.04.17
 */
public interface NetworkModelObserver {
    void subscribe(TranslatorModelSubscriber subscription);

    void unSubscribe();
}
