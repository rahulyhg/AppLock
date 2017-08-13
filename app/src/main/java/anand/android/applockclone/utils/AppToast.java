package anand.android.applockclone.utils;

import android.content.Context;
import android.widget.Toast;

public class AppToast {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
