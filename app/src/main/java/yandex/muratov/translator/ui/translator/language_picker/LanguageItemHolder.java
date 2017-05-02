package yandex.muratov.translator.ui.translator.language_picker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import yandex.muratov.translator.R;
import yandex.muratov.translator.translate.data.Language;
import yandex.muratov.translator.util.AndroidUtil;

@SuppressWarnings("WeakerAccess")
public class LanguageItemHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    private ItemOnClickListener onItemClickListener;

    public LanguageItemHolder(View itemView, final AndroidUtil.OnRecyclerViewItemClickListener listener) {
        super(itemView);
        textView = ((TextView) itemView.findViewById(R.id.item_language_text));
        onItemClickListener = new ItemOnClickListener(listener);
        textView.setOnClickListener(onItemClickListener);
    }

    public void bind(Language language) {
        onItemClickListener.setLanguage(language);
        textView.setText(language.getUiName());
    }

    private static class ItemOnClickListener implements View.OnClickListener {
        private AndroidUtil.OnRecyclerViewItemClickListener listener;
        private Language language;

        public ItemOnClickListener(AndroidUtil.OnRecyclerViewItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(language);
        }

        public void setLanguage(Language language) {
            this.language = language;
        }
    }
}
