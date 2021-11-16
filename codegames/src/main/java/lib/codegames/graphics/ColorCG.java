package lib.codegames.graphics;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class ColorCG extends Color {

    public static int parseColor(String color, int def) {
        try {
            return Color.parseColor(color);
        }catch (Exception e) {
            return def;
        }
    }

    public static int parseColorResource(Context context, @ColorRes int resid) {
        return ContextCompat.getColor(context, resid);
    }

    public static int parseTransparentColor(@ColorInt int color, float alpha) {
        int a = (int) ((alpha * 255) % 256);
        int r = (color>>16) & 0xff;
        int g = (color>>8) & 0xff;
        int b = color & 0xff;
        return (a<<24) | (r<<16) | (g<<8) | b;
    }

    public static int parseTransparentColorResource(Context context, @ColorRes int resid, float visibility) {
        int color = parseColorResource(context, resid);
        return parseTransparentColor(color, visibility);
    }

}
