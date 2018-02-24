package toong.vn.androidstatetextview;

import android.content.res.ColorStateList;
import android.graphics.Color;

public final class ColorUtils {

    private static final int ENABLE_ATTR = android.R.attr.state_enabled;
    private static final int CHECKED_ATTR = android.R.attr.state_checked;
    private static final int PRESSED_ATTR = android.R.attr.state_pressed;

    private ColorUtils() {
        // No-op
    }

    /**
     * Transform color to darker or lighter
     *
     * @param color input color
     * @param factor < 1: darker, > 1: lighter
     */
    public static int addColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a, Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
    }

    /**
     * Transform color to darker or lighter (alpha)
     *
     * @param color input color
     * @param factor < 1: opacity, > 1: transparent
     */
    public static int addAlpha(int color, float factor) {
        int a = Math.round(Color.alpha(color) * factor);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(Math.min(a, 255), r, g, b);
    }

    public static ColorStateList generateThumbColorWithTintColor(final int tintColor) {
        int[][] states = new int[][] {
                { -ENABLE_ATTR, CHECKED_ATTR }, { -ENABLE_ATTR }, { PRESSED_ATTR, -CHECKED_ATTR },
                { PRESSED_ATTR, CHECKED_ATTR }, { CHECKED_ATTR }, { -CHECKED_ATTR }
        };

        int[] colors = new int[] {
                tintColor - 0xAA000000, 0xFFBABABA, tintColor - 0x99000000, tintColor - 0x99000000,
                tintColor | 0xFF000000, 0xFFEEEEEE
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList generateBackColorWithTintColor(final int tintColor) {
        int[][] states = new int[][] {
                { -ENABLE_ATTR, CHECKED_ATTR }, { -ENABLE_ATTR }, { CHECKED_ATTR, PRESSED_ATTR },
                { -CHECKED_ATTR, PRESSED_ATTR }, { CHECKED_ATTR }, { -CHECKED_ATTR }
        };

        int[] colors = new int[] {
                tintColor - 0xE1000000, 0x10000000, tintColor - 0xD0000000, 0x20000000,
                tintColor - 0xD0000000, 0x20000000
        };
        return new ColorStateList(states, colors);
    }
}
