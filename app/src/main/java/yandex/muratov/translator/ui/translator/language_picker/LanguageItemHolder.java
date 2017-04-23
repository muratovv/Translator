package yandex.muratov.translator.ui.translator.language_picker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import yandex.muratov.translator.R;

@SuppressWarnings("WeakerAccess")
public class LanguageItemHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public LanguageItemHolder(View itemView) {
        super(itemView);
        textView = ((TextView) itemView.findViewById(R.id.item_language_text));
    }
}
