package lib.codegames.market;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;

class Myket implements IMarket {

    public static final String name = "Myket";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void openAppPage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openAppPage(context, packageName);
    }

    @Override
    public void openAppPage(@NonNull Context context, String packageName) {
        String url= "myket://details?id=" + packageName;
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
        String url= "myket://comment?id=" + packageName;
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
        String url= "myket://developer/" + packageName;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
