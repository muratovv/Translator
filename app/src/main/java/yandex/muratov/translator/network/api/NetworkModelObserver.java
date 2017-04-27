package yandex.muratov.translator.network.api;

/**
 * Observer of network model
 */
public interface NetworkModelObserver {

    /**
     * Subscribe for network model notifications
     *
     * @param subscription methods of objects will call on model response
     */
    void subscribe(TranslatorModelSubscriber subscription);

    /**
     * Unsubscribe all subscribers from notifications
     */
    void unsubscribe();
}
