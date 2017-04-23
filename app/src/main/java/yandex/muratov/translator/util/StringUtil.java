package yandex.muratov.translator.util;


import java.util.List;

public class StringUtil {
    public static String join(String separator, List<String> collection) {
        if(collection == null) return "";
        int size = collection.size();
        StringBuilder builder = new StringBuilder();
        for (int pos = 0; pos < size - 1; pos++) {
            builder.append(collection.get(pos))
                    .append(separator);
        }
        if(size > 0){
            builder.append(collection.get(size - 1));
        }
        return builder.toString();
    }

    public static String wrap(String left, String body, String right){
        if(body == null || body.length() == 0)
            return "";
        return left + body + right;
    }
}
