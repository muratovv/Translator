package yandex.muratov.translator.util;


import java.util.List;

public class StringUtil {
    public static String join(String separator, List<String> collection) {
        int size = collection.size();
        StringBuilder builder = new StringBuilder();
        for (int pos = 0; pos < size - 1; pos++) {
            builder.append(collection.get(pos))
                    .append(separator);
        }
        if(size > 1){
            builder.append(collection.get(size - 1));
        }
        return builder.toString();
    }
}
