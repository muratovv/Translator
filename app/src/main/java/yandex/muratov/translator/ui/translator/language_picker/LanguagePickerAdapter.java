package yandex.muratov.translator.ui.translator.language_picker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yandex.muratov.translator.R;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.util.AndroidUtil;

public class LanguagePickerAdapter extends RecyclerView.Adapter<LanguageItemHolder> {

    private List<Language> languages;
    private AndroidUtil.OnRecyclerViewItemClickListener listener;

    public LanguagePickerAdapter(List<Language> languages, AndroidUtil.OnRecyclerViewItemClickListener listener) {
        this.languages = languages;
        this.listener = listener;
    }

    @Override
    public LanguageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.item_language, parent, false);
        return new LanguageItemHolder(item);
    }

    @Override
    public void onBindViewHolder(final LanguageItemHolder holder, final int position) {
        holder.textView.setText(languages.get(position).getUiName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

}
