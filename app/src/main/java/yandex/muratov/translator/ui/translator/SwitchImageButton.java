package yandex.muratov.translator.ui.translator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SwitchImageButton extends android.support.v7.widget.AppCompatImageButton {

    private State state = State.OFF;
    private ChangeStateNotification changeStateNotification;

    public SwitchImageButton(Context context) {
        super(context);
        init();
    }

    public SwitchImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChangeState();
            }
        });
    }

    public SwitchImageButton setChangeStateNotification(ChangeStateNotification notification) {
        changeStateNotification = notification;
        return this;
    }

    private void applyChangeState() {
        state = state == State.ON ? State.OFF : State.ON;
        if (changeStateNotification != null) {
            changeStateNotification.onChange(state);
        }
    }

    public interface ChangeStateNotification {
        void onChange(State newState);
    }

    public enum State {
        OFF,
        ON
    }

}
