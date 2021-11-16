package lib.codegames.debug;

import android.util.Log;

public final class LogCG {

    private static final String default_tag = "CODEGAMES";
    private static String t = default_tag;

    public static String getTag() {
        return t;
    }

    public static void setTag(String tag) {
        t = (t == null || t.isEmpty()) ? default_tag : tag;
    }

    public static void d(String msg) {
        Log.d(getTag(), msg);
    }

    public static void d(Object msg) {
        Log.d(getTag(), String.valueOf(msg));
    }

}
