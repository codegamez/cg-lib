package lib.codegames.graphics;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class SizeCG {

    private static final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    private static final float density = displayMetrics.density;

    public static int dp2px(int dp) {
        return (int) (dp * density);
    }

    public static int px2dp(int px) {
        return (int) (px / density);
    }

    public static int getScreenWidthPX() {
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeightPX() {
        return displayMetrics.heightPixels;
    }

}
