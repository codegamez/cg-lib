package lib.codegames.market;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;

class IranApps implements IMarket {

    public static final String name = "Iran Apps";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void openAppPage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openAppPage(context);
    }

    @Override
    public void openAppPage(@NonNull Context context, String packageName) {
        String url= "iranapps://app/" + packageName;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    @Override
    public void openVotePage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openVotePage(context, packageName);
    }

    @Override
    public void openVotePage(@NonNull Context context, String packageName) {
        String url= "iranapps://app/" + packageName + "?a=comment&r=5";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    @Override
    public void openPublisherPage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openPublisherPage(context, packageName);
    }

    @Override
    public void openPublisherPage(@NonNull Context context, String packageName) {
        String url= "iranapps://user/name";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
