package toong.vn.androidstatetextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * This class use for handling {@link TextView} states color <br>
 * By default :<br>
 * <li> If none of state is specific, we will handle all states </li>
 * <li> If color of a state is not specific, we will add the suitable alpha for its color </li>
 */
public class StateTextView extends AppCompatTextView {
    private static final int PRESSED_STATE = 1;
    private static final int SELECTED_STATE = 2;
    private static final int DISABLED_STATE = 4;

    private int selectedColorRes;
    private int pressedColorRes;
    private int disabledColorRes;
    private int statesRes;

    public StateTextView(Context context) {
        this(context, null);
    }

    public StateTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * This handle for case change text color at runtime
     */
    @Override
    public void setTextColor(int color) {
        if (getCurrentTextColor() == color) {
            return;
        }
        configTextColor(color);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.StateTextView);
        if (!isDuplicateParentStateEnabled()) {
            setClickable(true);
        }
        try {
            statesRes = ta.getInt(R.styleable.StateTextView_stv_state, -1);
            pressedColorRes = ta.getResourceId(R.styleable.StateTextView_stv_colorPressed, -1);
            selectedColorRes = ta.getResourceId(R.styleable.StateTextView_stv_colorSelected, -1);
            disabledColorRes = ta.getResourceId(R.styleable.StateTextView_stv_colorDisabled, -1);
            configTextColor(getCurrentTextColor());
        } finally {
            ta.recycle();
        }
    }

    private void configTextColor(int currentColor) {
        int pressedColor = getPressedColor(currentColor);
        int selectedColor = getSelectedColor(currentColor);
        int disabledColor = getDisableColor(currentColor);

        int[][] states;
        int[] colors;
        if (statesRes == -1) {
            // if user don't specific any states
            states = new int[][] {
                    new int[] { android.R.attr.state_pressed },
                    new int[] { android.R.attr.state_selected },
                    new int[] { -android.R.attr.state_enabled }, new int[] {}
            };
            colors = new int[] {
                    pressedColor, selectedColor, disabledColor, currentColor
            };
        } else {
            boolean hasPressedState = containsFlag(statesRes, PRESSED_STATE);
            boolean hasSelectedState = containsFlag(statesRes, SELECTED_STATE);
            boolean hasDisabledState = containsFlag(statesRes, DISABLED_STATE);

            int index = 0;
            int size = 1; // we always have normal state as default
            if (hasPressedState) {
                size++;
            }
            if (hasSelectedState) {
                size++;
            }
            if (hasDisabledState) {
                size++;
            }
            states = new int[size][1];
            colors = new int[size];
            if (hasPressedState) {
                states[index] = new int[] { android.R.attr.state_pressed };
                colors[index] = pressedColor;
                index++;
            }
            if (hasSelectedState) {
                states[index] = new int[] { android.R.attr.state_selected };
                colors[index] = selectedColor;
                index++;
            }
            if (hasDisabledState) {
                states[index] = new int[] { -android.R.attr.state_enabled };
                colors[index] = disabledColor;
                index++;
            }
            states[index] = new int[] {};
            colors[index] = currentColor; // must add it at last of all state
        }
        setTextColor(new ColorStateList(states, colors));
    }

    private boolean containsFlag(int flagSet, int flag) {
        return (flagSet | flag) == flagSet;
    }

    private int getPressedColor(int currentColor) {
        return pressedColorRes == -1 ? ColorUtils.addAlpha(currentColor,
                AppUtils.getFloatResource(getContext(), R.integer.alpha_default_pressed))
                : ContextCompat.getColor(getContext(), pressedColorRes);
    }

    private int getSelectedColor(int currentColor) {
        return selectedColorRes == -1 ? ColorUtils.addAlpha(currentColor,
                AppUtils.getFloatResource(getContext(), R.integer.alpha_default_selected))
                : ContextCompat.getColor(getContext(), selectedColorRes);
    }

    private int getDisableColor(int currentColor) {
        return disabledColorRes == -1 ? ColorUtils.addAlpha(currentColor,
                AppUtils.getFloatResource(getContext(), R.integer.alpha_default_disabled))
                : ContextCompat.getColor(getContext(), disabledColorRes);
    }
}
