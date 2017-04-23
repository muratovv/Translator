package yandex.muratov.translator.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import yandex.muratov.translator.network.ConnectionBuilder;
import yandex.muratov.translator.network.NetworkUIConnector;
import yandex.muratov.translator.network.YandexDictionaryRepository;
import yandex.muratov.translator.network.YandexTranslateRepository;
import yandex.muratov.translator.network.YandexTranslator;
import yandex.muratov.translator.network.data.Language;
import yandex.muratov.translator.ui.translator.TranslationContext;

public class ContextHolderFragment extends Fragment {

    public static String TAG = ContextHolderFragment.class.getSimpleName();

    private TranslationContext context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initContext(getActivity());
    }

    private void initContext(Activity activity) {
        context = initTranslationContext(activity.getExternalCacheDir(), Language.RU);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    private static TranslationContext initTranslationContext(File cache, Language uiLanguage) {
        NetworkUIConnector networkConnector = initConnector(cache, Language.EN, Language.RU, uiLanguage);
        return new TranslationContext(networkConnector);
    }

    private static NetworkUIConnector initConnector(File cache,
                                                    Language sourceLang,
                                                    Language targetLang,
                                                    Language uiLanguage) {
        ConnectionBuilder connectionBuilder = new ConnectionBuilder(cache);
        YandexTranslateRepository translateRepo =
                new YandexTranslateRepository(connectionBuilder);
        YandexDictionaryRepository dictionaryRepo =
                new YandexDictionaryRepository(connectionBuilder, uiLanguage);
        YandexTranslator net = new YandexTranslator(translateRepo, dictionaryRepo);
        return new NetworkUIConnector(net, sourceLang, targetLang);
    }

    public TranslationContext getTranslationContext() {
        return context;
    }

    public static ContextHolderFragment findContextFragment(Fragment current) {
        return ((ContextHolderFragment) current.getFragmentManager().findFragmentByTag(ContextHolderFragment.TAG));
    }
}
