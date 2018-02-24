package toong.vn.androidstatetextview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AnyRes;
import android.util.TypedValue;

/**
 * Created by PhanVanLinh on 23/02/2018.
 * phanvanlinh.94vn@gmail.com
 */

public class AppUtils {
    public static int addAlpha(int color, float factor) {
        int a = Math.round(Color.alpha(color) * factor);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(Math.min(a, 255), r, g, b);
    }


    public static float getFloatResource(Context context, @AnyRes int id) {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(id, typedValue, true);
        return typedValue.getFloat();
    }

}
