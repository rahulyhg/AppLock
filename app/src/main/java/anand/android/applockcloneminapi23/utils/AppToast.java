package anand.android.applockcloneminapi23.utils;

import android.content.Context;

/**
 * Created by anandm on 09/02/17.
 */

public class AppToast {

    public static void showToast(Context context, String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
    }

    public static void showDebugToast(Context context, String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_LONG).show();
    }

}
