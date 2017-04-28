package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import yandex.muratov.translator.ui.LanguagePickerActivity;
import yandex.muratov.translator.R;
import yandex.muratov.translator.translate.TranslationController;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.storage.HistoryRow;
import yandex.muratov.translator.ui.ContextHolderFragment;
import yandex.muratov.translator.ui.TranslationContext;

import static yandex.muratov.translator.ui.ContextHolderFragment.findContextFragment;

public class TranslatorScreenFragment extends Fragment {
    private static String TAG = TranslatorScreenFragment.class.getSimpleName();

    private TranslatorInputView input;
    private TranslatorToolbarView toolbar;
    private TranslatorOutputView output;

    private ContextHolderFragment contextHolderFragment;

    private SendTextToTranslateTextWatcher textWatcher;

    public static OnChangeLanguage sourceLanguageChange;
    public static OnChangeLanguage targetLanguageChange;
    private OnReceiveTranslationSubscriber translationSubscriber;
    private TranslatorHistoryChangeSubscriber historySubscriber;
    private View.OnClickListener changeState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translator_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        getInputArguments(getArguments());
        initInternalState();
        initInternalViews(view);
        stateAfterViews();


    }

    private void stateAfterViews() {
        historySubscriber.setOutputView(output);

        translationSubscriber = new OnReceiveTranslationSubscriber(getContext(),
                contextHolderFragment.getHistoryContext(),
                input, output);
    }


    @Override
    public void onResume() {
        super.onResume();
        contextHolderFragment.getTranslationContext().getConnector().subscribe(translationSubscriber);
        contextHolderFragment.getHistoryContext().getConnector().subscribe(historySubscriber);
    }

    @Override
    public void onPause() {
        super.onPause();
        contextHolderFragment.getTranslationContext().getConnector().unsubscribe();
        contextHolderFragment.getHistoryContext().getConnector().unSubscribe();
    }

    private void initInternalState() {
        contextHolderFragment = findContextFragment(this);
        textWatcher = new SendTextToTranslateTextWatcher(contextHolderFragment.getTranslationContext());
        historySubscriber = new TranslatorHistoryChangeSubscriber();
        changeState = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryRow last = historySubscriber.getLast();
                if (last != null) {
                    contextHolderFragment
                            .getHistoryContext()
                            .getConnector()
                            .setFavorite(last, !last.inFavorites());
                } else {
                    // TODO: 25.04.17 add ui notification about missing answers
                }
            }
        };
    }

    private void initInternalViews(View screenView) {
        initToolbarCallbacks();
        View.OnClickListener swapCallback = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Language sourceLang = contextHolderFragment.getTranslationContext().getConnector().getSourceLang();
                Language targetLang = contextHolderFragment.getTranslationContext().getConnector().getTargetLang();
                sourceLanguageChange.notify(targetLang);
                targetLanguageChange.notify(sourceLang);
                immediateTranslate();
            }
        };
        toolbar = initToolbar(this,
                contextHolderFragment.getTranslationContext(),
                screenView,
                getResources(),
                swapCallback);
        input = initInputView(screenView, textWatcher);
        output = initOutputView(getContext(), screenView, changeState);
    }

    private void immediateTranslate() {
        Editable text = input.getSourceEditText().getText();
        Log.d(TAG, String.format("immediateTranslate: %s", text));
        textWatcher.afterTextChanged(text);
    }

    private void getInputArguments(Bundle state) {
        if (state != null) {
            Log.d(TAG, "getInputArguments: load from bundle");
        }
    }

    private static TranslatorInputView initInputView(View rootView, TextWatcher textWatcher) {
        TranslatorInputView input = (TranslatorInputView) rootView.findViewById(R.id.input);
        input.getSourceEditText().addTextChangedListener(textWatcher);
        return input;
    }

    private static TranslatorToolbarView initToolbar(final Fragment appliedFragment,
                                                     final TranslationContext translationContext,
                                                     View rootView,
                                                     final Resources resources,
                                                     View.OnClickListener swapCallback) {
        TranslatorToolbarView toolbar = (TranslatorToolbarView) rootView.findViewById(R.id.toolbar_translator);
        initToolbarTitle(translationContext, toolbar);
        toolbar.getSwapButton().setOnClickListener(swapCallback);

        toolbar.getPickSourceLang().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = resources.getString(R.string.title_picker_source_text);
                Intent intent = initLanguagePickerActivityIntent(appliedFragment,
                        titleText,
                        LanguagePickerActivity.SOURCE_LANG_IDENTIFIER);
                appliedFragment.startActivity(intent);
            }
        });
        toolbar.getPickTargetLang().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = resources.getString(R.string.title_picker_target_text);
                Intent intent = initLanguagePickerActivityIntent(appliedFragment,
                        titleText,
                        LanguagePickerActivity.TARGET_LANG_IDENTIFIER);
                appliedFragment.startActivity(intent);
            }
        });
        return toolbar;
    }

    private void initToolbarCallbacks() {
        sourceLanguageChange = new OnChangeLanguage() {
            @Override
            public void notify(Language language) {
                changeLanguage(language, SelectedLanguage.SOURCE);
                immediateTranslate();
            }
        };

        targetLanguageChange = new OnChangeLanguage() {
            @Override
            public void notify(Language language) {
                changeLanguage(language, SelectedLanguage.TARGET);
                immediateTranslate();
            }
        };
    }

    private static void initToolbarTitle(TranslationContext translationContext, TranslatorToolbarView toolbar) {
        TranslationController connector = translationContext.getConnector();
        if (connector != null) {
            toolbar.getPickSourceLang().setText(connector.getSourceLang().getUiName());
            toolbar.getPickTargetLang().setText(connector.getTargetLang().getUiName());
        }
    }

    private static Intent initLanguagePickerActivityIntent(Fragment appContext,
                                                           String tagIdentifier,
                                                           String callbackIdentifier) {
        Intent languageSourcePickerIntent =
                new Intent(appContext.getContext(), LanguagePickerActivity.class);
        languageSourcePickerIntent.putExtra(LanguagePickerActivity.TITLE_TAG, tagIdentifier);
        languageSourcePickerIntent.putExtra(LanguagePickerActivity.CALLBACK_TAG, callbackIdentifier);
        return languageSourcePickerIntent;
    }

    private void changeLanguage(Language language, SelectedLanguage choose) {
        TranslationController connector = contextHolderFragment.getTranslationContext().getConnector();
        if (connector != null) {
            if (choose == SelectedLanguage.SOURCE) {
                connector.setSourceLanguage(language);
            } else {
                connector.setTargetLanguage(language);
            }
        }
        Button view = (choose == SelectedLanguage.SOURCE ?
                toolbar.getPickSourceLang() : toolbar.getPickTargetLang());
        if (view != null) {
            view.setText(language.getUiName());
        }

    }

    private static TranslatorOutputView initOutputView(Context appContext, View rootView,
                                                       View.OnClickListener notification) {
        TranslatorOutputView translatorOutputView = (TranslatorOutputView) rootView.findViewById(R.id.output);
        ListView listView = translatorOutputView.getDictionaryListView();
        listView.setAdapter(new DictionaryAdapter(appContext, R.layout.item_dictionary_definition));
        translatorOutputView.getSaveInBookmarksButton().setOnClickListener(notification);
        return translatorOutputView;
    }

    private enum SelectedLanguage {
        SOURCE,
        TARGET
    }
}
