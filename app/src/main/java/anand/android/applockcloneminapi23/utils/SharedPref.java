package anand.android.applockcloneminapi23.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import anand.android.applockcloneminapi23.views.adapters.modle.AppInfo;

import static anand.android.applockcloneminapi23.utils.AppConstants.SP_APP_LOCK_CLONE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anandm on 08/02/17.
 */

public class SharedPref {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    //Get app shared pref instance in private mode
    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(SP_APP_LOCK_CLONE, MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPref(context).edit();
    }

    /**
     * Save string to the shared pref
     * @param context
     * @param key
     * @param value
     */
    public static void saveStringInPref(Context context, String key, String value) {
        //get editor
        editor = getEditor(context);
        //Save the value
        editor.putString(key, value).apply();
    }

    /**
     * Get string from the shared pref
     * @param context
     * @param key
     * @return
     */
    public static String getStringFromPref(Context context, String key) {
        //Get from shared preference
        return getSharedPref(context).getString(key, null);
    }

    /**
     * Save boolean value to shared pref
     * @param context
     * @param key
     * @param value
     */
    public static void saveBooleanInPref(Context context, String key, Boolean value) {
        editor = getEditor(context);
        editor.putBoolean(key, value).apply();
    }

    /**
     * Get boolean value from shared pref
     * @param context
     * @param key
     * @return
     */
    public static Boolean getBooleanFromPref(Context context, String key) {
        return getSharedPref(context).getBoolean(key, false);
    }

    public static final String LOCKED_APP = "locked_app";

    // This four methods are used for maintaining favorites.
    public static void saveLocked(Context context, List<AppInfo> lockedApp) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(SP_APP_LOCK_CLONE,
                MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonLockedApp = gson.toJson(lockedApp);
        editor.putString(LOCKED_APP, jsonLockedApp);
        editor.commit();
    }

    public static void addLocked(Context context, AppInfo app) {
        ArrayList<AppInfo> lockedApp = getLocked(context);
        if (lockedApp == null)
            lockedApp = new ArrayList<AppInfo>();
        lockedApp.add(app);
        saveLocked(context, lockedApp);
    }

    public static void removeLocked(Context context, String app) {
        ArrayList<AppInfo> locked = getLocked(context);
        AppInfo appInfo = null;
        if (locked != null) {
            for (int i = 0; i < locked.size(); i++) {
                if (app.equals(locked.get(i).getPackageName())) {
                    appInfo = locked.get(i);
                }
            }
            if (null!= appInfo) {
                locked.remove(appInfo);
            }
            saveLocked(context, locked);
        }
    }

    public static ArrayList<AppInfo> getLocked(Context context) {
        SharedPreferences settings;
        ArrayList<AppInfo> locked = new ArrayList<>();

        settings = context.getSharedPreferences(SP_APP_LOCK_CLONE,
                MODE_PRIVATE);

        if (settings.contains(LOCKED_APP)) {
            String jsonLocked = settings.getString(LOCKED_APP, null);
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppInfo>>(){}.getType();
            locked = gson.fromJson(jsonLocked, type);
        } else
            return null;
        return (ArrayList<AppInfo>) locked;
    }

}
