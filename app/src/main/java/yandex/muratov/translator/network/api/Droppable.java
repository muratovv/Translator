package yandex.muratov.translator.network.api;

public interface Droppable {
    /**
     * Drop last request
     */
    void dropLastRequest();

    /**
     * Close all connections for closing
     */
    void dropConnection();
}
