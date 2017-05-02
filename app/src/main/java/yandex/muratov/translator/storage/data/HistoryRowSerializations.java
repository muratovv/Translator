package yandex.muratov.translator.storage.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Class aimed on serialize and deserialize {@link HistoryRow} objects based on JSON format
 */
public class HistoryRowSerializations {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static String serialize(HistoryRow object) {
        return gson.toJson(object);
    }

    public static HistoryRow deserialize(String rawRepresentation) {
        return gson.fromJson(rawRepresentation, HistoryRow.class);
    }
}
