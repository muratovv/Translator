package yandex.muratov.translator.translate.api;

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
