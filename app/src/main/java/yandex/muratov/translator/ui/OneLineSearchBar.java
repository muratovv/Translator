package yandex.muratov.translator.ui;


import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import yandex.muratov.translator.R;
import yandex.muratov.translator.util.DefaultTextWatcher;
import yandex.muratov.translator.util.KeyboardUtil;

public class OneLineSearchBar extends RelativeLayout {
    private static String TAG = OneLineSearchBar.class.getSimpleName();

    private ImageButton findButton;
    private EditText queryLine;
    private ImageButton eraseButton;

    public OneLineSearchBar(Context context) {
        super(context);
        initElements(context);
    }

    public OneLineSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initElements(context);
    }

    public OneLineSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initElements(context);
    }

    public OneLineSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initElements(context);
    }

    private void initElements(Context context) {
        Log.d(TAG, "initElements: ");
        View rootView = inflate(context, R.layout.view_search, this);
        findButton = initFindButton(rootView);
        queryLine = initTextLine(rootView);
        eraseButton = initEraseButton(rootView);

        getFindButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        getQueryLine().addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                hideEraseButtonOnEmptyEdit(s);
            }
        });

        getEraseButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                eraseSearchQuery();
            }
        });
    }

    private void hideKeyboard() {
        getQueryLine().onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    private void eraseSearchQuery() {
        getQueryLine().setText("");
        KeyboardUtil.showKeyboard(getContext(), getQueryLine());
    }

    private void hideEraseButtonOnEmptyEdit(Editable s) {
        if (s.length() == 0) {
            getEraseButton().setVisibility(INVISIBLE);
        } else {
            getEraseButton().setVisibility(VISIBLE);
        }
    }

    private static ImageButton initFindButton(View rootView) {
        return ((ImageButton) rootView.findViewById(R.id.view_search));
    }

    private static EditText initTextLine(View rootView) {
        return ((EditText) rootView.findViewById(R.id.edit_search_line));
    }

    private static ImageButton initEraseButton(View rootView) {
        ImageButton eraseButton = (ImageButton) rootView.findViewById(R.id.button_erase);
        eraseButton.setVisibility(INVISIBLE);
        return eraseButton;
    }

    public ImageButton getFindButton() {
        return findButton;
    }

    public EditText getQueryLine() {
        return queryLine;
    }

    public ImageButton getEraseButton() {
        return eraseButton;
    }

}
