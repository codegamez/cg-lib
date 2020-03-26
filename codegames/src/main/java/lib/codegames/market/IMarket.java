package lib.codegames.market;

import android.content.Context;
import androidx.annotation.NonNull;

public interface IMarket {

    public static final String CAFE_BAZAAR = "Cafe Bazaar";
    public static final String MYKET = "Myket";
    public static final String IRAN_APPS = "Iran Apps";

    public String getName();
    public void openAppPage(@NonNull Context context);
    public void openAppPage(@NonNull Context context, String packageName);
    public void openVotePage(@NonNull Context context);
    public void openVotePage(@NonNull Context context, String packageName);
    public void openPublisherPage(@NonNull Context context);
    public void openPublisherPage(@NonNull Context context, String packageName);

}
