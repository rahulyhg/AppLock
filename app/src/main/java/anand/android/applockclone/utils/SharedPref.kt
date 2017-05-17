package anand.android.applockclone.utils

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson

import java.util.ArrayList
import java.util.Arrays

import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

/**
 * Created by anandm on 08/02/17.
 */

object SharedPref {

    var sharedPref: SharedPreferences? = null;

    @Inject
    public fun SharedPref(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPref;
    }

    private var editor: SharedPreferences.Editor? = null

    //Get app shared pref instance in private mode
    private fun getSharedPref(context: Context): SharedPreferences = context.getSharedPreferences(AppConstants.SP_APP_LOCK_CLONE, MODE_PRIVATE)


    private fun getEditor(context: Context): SharedPreferences.Editor {
        return getSharedPref(context).edit()
    }

    /**
     * Save string to the shared pref
     * @param context
     * *
     * @param key
     * *
     * @param value
     */
    fun saveStringInPref(context: Context, key: String, value: String) {
        //get editor
        editor = getEditor(context)
        //Save the value
        editor!!.putString(key, value).apply()
    }

    /**
     * Get string from the shared pref
     * @param context
     * *
     * @param key
     * *
     * @return
     */
    fun getStringFromPref(context: Context, key: String): String = sharedPref!!.getString(key, null)


    /**
     * Save boolean value to shared pref
     * @param context
     * *
     * @param key
     * *
     * @param value
     */
    fun saveBooleanInPref(context: Context, key: String, value: Boolean?) {
        editor = getEditor(context)
        editor!!.putBoolean(key, value!!).apply()
    }

    /**
     * Get boolean value from shared pref
     * @param context
     * *
     * @param key
     * *
     * @return
     */
    fun getBooleanFromPref(context: Context, key: String): Boolean = getSharedPref(context).getBoolean(key, false)

    private val LOCKED_APP = "locked_app"

    // This four methods are used for maintaining favorites.
    private fun saveLocked(context: Context, lockedApp: List<String>) {
        val gson = Gson()
        val jsonLockedApp = gson.toJson(lockedApp)
        getEditor(context).putString(LOCKED_APP, jsonLockedApp).commit()
    }

    fun addLocked(context: Context, app: String) {
        var lockedApp: MutableList<String>? = getLocked(context)
        if (lockedApp == null)
            lockedApp = ArrayList<String>()
        lockedApp.add(app)
        saveLocked(context, lockedApp)
    }

    fun removeLocked(context: Context, app: String) {
        val locked = getLocked(context)
        if (locked != null) {
            locked.remove(app)
            saveLocked(context, locked)
        }
    }

    fun getLocked(context: Context): ArrayList<String>? {
        var locked: List<String>
        if (getSharedPref(context).contains(LOCKED_APP)) {
            val jsonLocked = getSharedPref(context).getString(LOCKED_APP, null)
            val gson = Gson()
            val lockedItems = gson.fromJson(jsonLocked,
                    Array<String>::class.java)

            locked = Arrays.asList(*lockedItems)
            locked = ArrayList(locked)
        } else
            return null
        return locked
    }

}
