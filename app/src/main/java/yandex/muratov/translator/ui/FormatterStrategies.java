package yandex.muratov.translator.ui;


import java.util.List;

import yandex.muratov.translator.util.StringUtil;

public class FormatterStrategies {
    interface Formatter {
        String toText();
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
        public WrapList(){
            this(null, DEFAULT_SEPARATOR);
        }

        public String toText(List<String> list){
            this.list = list;
            return toText();
        }

        @Override
        public String toText() {
            return StringUtil.join(separator, list);
        }
    }


    public static WrapList wrapList = new WrapList();
}
