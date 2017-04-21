package yandex.muratov.translator.network.data;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Language {
    private String code;
    private String uiName;
    private boolean leftToRight;

    private Language(String code, String uiName, boolean leftToRight) {
        this.code = code;
        this.uiName = uiName;
        this.leftToRight = leftToRight;
    }

    public String getCode() {
        return code;
    }

    public String getUiName() {
        return uiName;
    }

    public boolean isLeftToRight() {
        return leftToRight;
    }

    public static Language make(String code, String uiName) {
        Language lang = new Language(code, uiName, true);
        register(lang);
        return lang;
    }

    public static Language make(String code, String uiName, boolean leftToRight) {
        Language lang = new Language(code, uiName, leftToRight);
        register(lang);
        return lang;
    }

    private static void register(Language lang) {
        availableLanguages.add(lang);
    }


    public static List<Language> availableLanguages = new ArrayList<>();


    public static final Language EN = make("en", "English");
    public static final Language RU = make("ru", "Русский");
    public static final Language TR = make("tr", "Türk");
    public static final Language UK = make("uk", "Український");
    public static final Language SW = make("sw", "Kiswahili");
    public static final Language AZ = make("az", "Azərbaycan");
    public static final Language HY = make("hy", "Հայերեն");
    public static final Language ID = make("id", "Indonesia");
    public static final Language KO = make("ko", "한국어");
    public static final Language BE = make("be", "Беларуский");
    public static final Language JA = make("ja", "日本語");
    public static final Language KA = make("ka", "ქართული");
    public static final Language CY = make("cy", "Cymru");
    public static final Language KK = make("kk", "Қазақ");
    public static final Language IT = make("it", "Italiano");
    public static final Language RO = make("ro", "Română");
    public static final Language HU = make("hu", "Magyar");
    public static final Language MS = make("ms", "Melayu");
    public static final Language MK = make("mk", "Македонски");
    public static final Language FA = make("fa", "زبان فارسی", false);
    public static final Language DA = make("da", "Dansk");
    public static final Language ES = make("es", "Español");
    public static final Language FR = make("fr", "Français");
    public static final Language LY = make("ly", "Latviešu");
    public static final Language GA = make("ga", "Gaeilge");
    public static final Language SR = make("sr", "Руски");
    public static final Language TT = make("tt", "Татар");
    public static final Language SQ = make("sq", "Shqiptare");
    public static final Language MT = make("mt", "Malti");
    public static final Language PL = make("pl", "Polski");
    public static final Language HR = make("hr", "Hrvatski");
    public static final Language TH = make("th", "ไทย");
    public static final Language NO = make("no", "Norsk");
    public static final Language KY = make("ky", "Кыргыздар");
    public static final Language GL = make("gl", "Галисийский тили");
    public static final Language FI = make("fi", "Suomen");
    public static final Language TG = make("tg", "Забони тоҷикӣ");
    public static final Language EU = make("eu", "Euskal");
    public static final Language AR = make("ar", "اللغة العربية", false);
    public static final Language CA = make("ca", "Llengua catalana");
    public static final Language NL = make("nl", "Nederlandse taal");
    public static final Language BG = make("bg", "Български");
    public static final Language AF = make("af", "Afrikaans");
    public static final Language MN = make("mn", "Монгол хэл");
    public static final Language HT = make("ht", "Ayisyen nan lang");
    public static final Language PT = make("pt", "Língua portuguesa");
    public static final Language DE = make("de", "Deutsche");
    public static final Language TL = make("tl", "Tagalog");
    public static final Language BS = make("bs", "Engleskom jeziku");
    public static final Language VI = make("vi", "Tiếng việt");
    public static final Language CS = make("cs", "Český jazyk");
    public static final Language EL = make("el", "Ελληνική γλώσσα");
    public static final Language MG = make("mg", "Fiteny Malagasy");
    public static final Language SK = make("sk", "Slovenský jazyk");
    public static final Language BA = make("ba", "Башҡорт теле");
    public static final Language LT = make("lt", "Lietuvių kalba");
    public static final Language ET = make("et", "Eesti keel");
    public static final Language ZH = make("zh", "汉语");
    public static final Language HE = make("he", "השפה העברית", false);
    public static final Language UZ = make("uz", "O'zbek tili");
    public static final Language LA = make("la", "Latina lingua");
    public static final Language SL = make("sl", "Slovenski jezik");
    public static final Language SV = make("sv", "Svenska språket");
    public static final Language IS = make("is", "Íslensku");
}
