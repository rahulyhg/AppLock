package anand.android.applockcloneminapi23.views.adapters.modle;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by amitshekhar on 28/04/15.
 */
public class AppInfo {
    private String name;
    private String packageName;
    private String versionName;
    private int versionCode = 0;
    private Drawable icon;
    private Boolean isLocked = false;

    public AppInfo() {
    }

    public Intent getLaunchIntent(Context context) {
        Intent intentLaunch = context.getPackageManager().getLaunchIntentForPackage(this.packageName);
        return intentLaunch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }
}

