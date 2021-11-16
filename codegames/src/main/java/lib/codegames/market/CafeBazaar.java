package lib.codegames.market;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;

class CafeBazaar implements IMarket {

    public static final String name = "Cafe Bazaar";

    public String getName() {
        return name;
    }

    public void openAppPage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openAppPage(context, packageName);
    }

    @Override
    public void openAppPage(@NonNull Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url= "bazaar://details?id=" + packageName;
        intent.setData(Uri.parse(url));
        intent.setPackage("com.farsitel.bazaar");
        context.startActivity(intent);
    }

    public void openVotePage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openVotePage(context, packageName);
    }

    @Override
    public void openVotePage(@NonNull Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        String url= "bazaar://details?id=" + packageName;
        intent.setData(Uri.parse(url));
        intent.setPackage("com.farsitel.bazaar");
        context.startActivity(intent);
    }

    public void openPublisherPage(@NonNull Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        openPublisherPage(context, packageName);
    }

    @Override
    public void openPublisherPage(@NonNull Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url= "bazaar://collection?slug=by_author&aid=" + packageName;
        intent.setData(Uri.parse(url));
        intent.setPackage("com.farsitel.bazaar");
        context.startActivity(intent);
    }

}
