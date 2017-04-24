package yandex.muratov.translator.ui;


import java.util.List;

import yandex.muratov.translator.util.StringUtil;

public class FormatterStrategies {
    interface Formatter {
        String apply();
    }

    public static class WrapList implements Formatter {
        public static final String DEFAULT_SEPARATOR = ", ";
        private List<String> list;
        private final String separator;

        public WrapList(List<String> list, String separator) {
            this.list = list;
            this.separator = separator;
        }

        public WrapList(List<String> list) {
            this(list, DEFAULT_SEPARATOR);
        }

        public WrapList() {
            this(null, DEFAULT_SEPARATOR);
        }

        public String toText(List<String> list) {
            this.list = list;
            return apply();
        }

        @Override
        public String apply() {
            return StringUtil.join(separator, list);
        }
    }

    public static class Upper implements Formatter {
        private String source;

        public Upper(String source) {
            this.source = source;
        }


        @Override
        public String apply() {
            return source.toUpperCase();
        }
    }


    public static WrapList wrapList = new WrapList();
}
