package anand.android.applockclone.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static anand.android.applockclone.utils.AppConstants.SP_APP_LOCK_CLONE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anandm on 08/02/17.
 */

@SuppressWarnings("ALL")
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

    private static final String LOCKED_APP = "locked_app";

    // This four methods are used for maintaining favorites.
    private static void saveLocked(Context context, List<String> lockedApp) {
        Gson gson = new Gson();
        String jsonLockedApp = gson.toJson(lockedApp);
        getEditor(context).putString(LOCKED_APP, jsonLockedApp).commit();
    }

    public static void addLocked(Context context, String app) {
        List<String> lockedApp = getLocked(context);
        if (lockedApp == null)
            lockedApp = new ArrayList<String>();
        lockedApp.add(app);
        saveLocked(context, lockedApp);
    }

    public static void removeLocked(Context context, String app) {
        ArrayList<String> locked = getLocked(context);
        if (locked != null) {
            locked.remove(app);
            saveLocked(context, locked);
        }
    }

    public static ArrayList<String> getLocked(Context context) {
        List<String> locked;
        if (getSharedPref(context).contains(LOCKED_APP)) {
            String jsonLocked = getSharedPref(context).getString(LOCKED_APP, null);
            Gson gson = new Gson();
            String[] lockedItems = gson.fromJson(jsonLocked,
                    String[].class);

            locked = Arrays.asList(lockedItems);
            locked = new ArrayList<String>(locked);
        } else
            return null;
        return (ArrayList<String>) locked;
    }

}
