package yandex.muratov.translator.ui;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import yandex.muratov.translator.R;

public class OneLineSearchBar extends View {

    private ImageButton findButton;
    private EditText textLine;
    private ImageButton eraseButton;

    public OneLineSearchBar(Context context) {
        super(context);
    }

    public OneLineSearchBar(View baseView) {
        super(baseView.getContext());
        initElements(baseView);
    }

    private void initElements(View rootView){
        findButton = initFindButton(rootView);
        textLine = initTextLine(rootView);
        eraseButton = initEraseButton(rootView);
    }

    private static ImageButton initFindButton(View rootView) {
        return ((ImageButton) rootView.findViewById(R.id.view_search));
    }

    private static EditText initTextLine(View rootView) {
        return ((EditText) rootView.findViewById(R.id.edit_search_line));
    }

    private static ImageButton initEraseButton(View rootView) {
        return ((ImageButton) rootView.findViewById(R.id.button_erase));
    }

    public ImageButton getFindButton() {
        return findButton;
    }

    public EditText getTextLine() {
        return textLine;
    }

    public ImageButton getEraseButton() {
        return eraseButton;
    }
}
