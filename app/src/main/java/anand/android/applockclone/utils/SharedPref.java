package anand.android.applockclone.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SharedPref {

    private SharedPreferences sharedPref;

    public void SharedPref(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    private SharedPreferences.Editor editor;

    //Get app shared pref instance in private mode
    private SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(Constants.SP_APP_LOCK_CLONE, MODE_PRIVATE);
    }

    private SharedPreferences.Editor getEditor(Context context) {
        return getSharedPref(context).edit();
    }

    /**
     * Save string to the shared pref
     * @param context
     * @param key
     * @param value
     */
    public void saveStringInPref(Context context, String key, String value) {
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
    public String getStringFromPref(Context context, String key) {
        return sharedPref.getString(key, null);
    }


    /**
     * Save boolean value to shared pref
     * @param context
     * @param key
     * @param value
     */
    public void saveBooleanInPref(Context context, String key, Boolean value) {
        editor = getEditor(context);
        editor.putBoolean(key, value).apply();
    }

    /**
     * Get boolean value from shared pref
     * @param context
     * @param key
     * @return
     */
    public Boolean getBooleanFromPref(Context context, String key) {
        return getSharedPref(context).getBoolean(key, false);
    }

    private String LOCKED_APP = "locked_app";

    // This four methods are used for maintaining favorites.
    private void saveLocked(Context context, List<String> lockedApp) {
        Gson gson = new Gson();
        String jsonLockedApp = gson.toJson(lockedApp);
        getEditor(context).putString(LOCKED_APP, jsonLockedApp).commit();
    }

    public void addLocked(Context context, String app) {
        List<String> lockedApp = getLocked(context);
        if (lockedApp == null)
            lockedApp = new ArrayList<String>();
        lockedApp.add(app);
        saveLocked(context, lockedApp);
    }

    public void removeLocked(Context context, String app) {
        List<String> locked = getLocked(context);
        if (locked != null) {
            locked.remove(app);
            saveLocked(context, locked);
        }
    }

    public List<String> getLocked(Context context) {
        SharedPreferences settings;
        List<String> locked;

        settings = context.getSharedPreferences(LOCKED_APP, Context.MODE_PRIVATE);

        if (settings.contains(LOCKED_APP)) {
            String jsonLocked = settings.getString(LOCKED_APP, null);
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
