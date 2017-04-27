package yandex.muratov.translator.network.api;

/**
 * Interface represents cancellations for connections
 */
public interface Droppable {

    /**
     * Drop last operation
     */
    void dropLast();

    /**
     * Close all operations and close connection
     */
    void dropConnection();
}
