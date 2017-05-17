package anand.android.applockclone.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import anand.android.applockclone.R;
import anand.android.applockclone.utils.AppConstants;
import anand.android.applockclone.utils.SharedPref;
import anand.android.applockclone.ui.fragments.AccessPatternViewFragment;
import anand.android.applockclone.ui.fragments.InitialSetupFragment;
import butterknife.ButterKnife;

import static anand.android.applockclone.utils.AppConstants.SP_PATTERN_EXISTS;
import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;

/**
 * Created by anandm on 08/02/17.
 */

public class InitialSetupScreen extends AppCompatActivity {

    public static final String TAG = InitialSetupScreen.class.getSimpleName();

    //Overlay permission request code
    public final static int REQUEST_CODE = 999;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setup);
        ButterKnife.bind(this);

        //App lock needs overlay permissions to show lock screen over other app on opening of the app
        //For API 23 and above this permission is not provided on install,
        //user needs to be guided to provide the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
                return;
            }
        }

        validateInitialSetUp();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //Check once again if the permission is granted
            if (Settings.canDrawOverlays(this)) {
                Log.d(TAG, "Permission granted");

            }
        }
    }

    //This check the initial set up of pattern for the lock
    //If patter already exists, show frag for entering patter to access this app
    //If no pattern exists, allow user to create on. This step is mandatory
    private void validateInitialSetUp() {
        if (isPatternExists())
            showAccessPatternView();
        else
            showPatternSetupView();

    }

    //Check if pattern already exists
    private boolean isPatternExists() {
        return SharedPref.getBooleanFromPref(this, SP_PATTERN_EXISTS) &&
                null != SharedPref.getStringFromPref(this, AppConstants.SP_PATTERN);
    }

    //Show normal pattern access view ( when pattern exists )
    private void showAccessPatternView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AccessPatternViewFragment fr = AccessPatternViewFragment.newInstance();
        ft.replace(R.id.containerView, fr, InitialSetupScreen.class.getSimpleName());
        ft.commit();
    }

    //Show pattern setup view ( when pattern not exists )
    private void showPatternSetupView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        InitialSetupFragment fr = InitialSetupFragment.newInstnace();
        ft.replace(R.id.containerView, fr, InitialSetupScreen.class.getSimpleName());
        ft.commit();
    }
}
