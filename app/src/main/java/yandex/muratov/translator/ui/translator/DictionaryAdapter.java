package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yandex.muratov.translator.R;
import yandex.muratov.translator.network.data.DefinitionEntry;
import yandex.muratov.translator.network.data.ExampleEntry;
import yandex.muratov.translator.network.data.TranslationEntry;
import yandex.muratov.translator.util.DefaultTextWatcher;
import yandex.muratov.translator.util.ListsUtil;
import yandex.muratov.translator.util.StringUtil;

import static yandex.muratov.translator.util.AndroidUtil.findViewById;

public class DictionaryAdapter extends ArrayAdapter<DefinitionEntry> {
    public static String TAG = DictionaryAdapter.class.getSimpleName();

    private List<DefinitionEntry> dataset = new ArrayList<>();

    public DictionaryAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void setAnswer(List<DefinitionEntry> definitionEntries) {
        this.dataset = definitionEntries;
        clear();
        for (DefinitionEntry entry : definitionEntries) {
            insert(entry, getCount());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.item_dictionary_definition, null);
        }
        DefinitionEntry entry = dataset.get(position);

        TextView generalTranslationTextView = findViewById(view, R.id.text_translation_main);
        TextView partOfSpeechTextView = findViewById(view, R.id.text_view_part_of_speech);
        LinearLayout translationsListView = findViewById(view, R.id.list_of_dictionary_translations);


        generalTranslationTextView.setText(entry.getText());
        partOfSpeechTextView.setText(entry.getPartOfSpeech());
        TranslationsFiller.fillTranslationsLayout(getContext(), translationsListView, entry.getTranslations());
        return view;

    }

    private static class TranslationsFiller {

        private static void fillTranslationsLayout(Context appContext,
                                                   LinearLayout listOfTranslations,
                                                   List<TranslationEntry> translations) {
            listOfTranslations.removeAllViews();
            for (int pos = 0; pos < translations.size(); pos++) {
                TranslationEntry translation = translations.get(pos);
                View elem = inflateTranslation(appContext, listOfTranslations);
                elem = fillTranslation(appContext, elem, translation, pos);
                listOfTranslations.addView(elem);
            }
        }

        private static View fillTranslation(Context appContext, View view, TranslationEntry entry, int position) {
            TextView numberTextView = findViewById(view, R.id.text_number);
            TextView translationTextView = findViewById(view, R.id.text_translation_variants);
            final TextView explanationTextView = getExplanationTextView(view);
            LinearLayout examplesList = findViewById(view, R.id.list_of_examples);

            numberTextView.setText(String.valueOf(position + 1));
            List<String> mergedTranslations =
                    ListsUtil.of(entry.getText(), ListsUtil.extractSynonyms(entry.getSynonyms()));
            translationTextView.setText(StringUtil.join(", ", mergedTranslations));
            String means = StringUtil.wrap("(", StringUtil.join(", ", ListsUtil.extractMeans(entry.getMeans())), ")");
            explanationTextView.setText(means);
            ExamplesFiller.fillExamplesLayout(appContext, examplesList, entry.getExamples());

            return view;
        }

        private static View inflateTranslation(Context appContext, ViewGroup root) {
            LayoutInflater inflater = getInflater(appContext);
            return inflater.inflate(R.layout.item_dictionary_translation, root, false);
        }

        private static TextView getExplanationTextView(View view) {
            final TextView text = findViewById(view, R.id.text_explanation);
            text.addTextChangedListener(new DefaultTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s == null || s.length() == 0) {
                        text.setVisibility(View.GONE);
                    }
                }
            });
            return text;
        }

    }

    private static class ExamplesFiller {


        private static void fillExamplesLayout(Context appContext,
                                               LinearLayout examplesLayout,
                                               List<ExampleEntry> examples) {
            examplesLayout.removeAllViews();
            for (ExampleEntry entry : examples) {
                View view = inflateExample(appContext, examplesLayout);
                view = fillExample(view, entry);
                examplesLayout.addView(view);
            }
        }

        private static View fillExample(View exampleView, ExampleEntry entry) {
            TextView explanationTextView = findViewById(exampleView, R.id.text_dictionary_explanation);
            explanationTextView.setText(String.format("%s - %s", entry.getText(),
                    StringUtil.join(", ", ListsUtil.extractTranslations(entry.getTranslations()))));
            return exampleView;

        }

        private static View inflateExample(Context appContext, ViewGroup examplesLayout) {
            LayoutInflater inflater = getInflater(appContext);
            return inflater.inflate(R.layout.item_dictionary_explanation, examplesLayout, false);
        }

    }

    private static LayoutInflater getInflater(Context appContext) {
        return (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


}
