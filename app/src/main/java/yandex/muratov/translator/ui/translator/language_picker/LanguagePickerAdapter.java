package yandex.muratov.translator.ui.translator.language_picker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yandex.muratov.translator.R;
import yandex.muratov.translator.translate.data.Language;

public class LanguagePickerAdapter extends RecyclerView.Adapter<LanguageItemHolder> {

    private List<Language> languages;

    public LanguagePickerAdapter(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public LanguageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_language, parent, false);
        return new LanguageItemHolder(item);
    }

    @Override
    public void onBindViewHolder(LanguageItemHolder holder, int position) {
        holder.textView.setText(languages.get(position).getUiName());
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }
}
