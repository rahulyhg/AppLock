package anand.android.applockclone.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bcgdv.asia.lib.connectpattern.ConnectPatternView;

import java.util.ArrayList;

import anand.android.applockclone.R;
import anand.android.applockclone.utils.AppToast;
import anand.android.applockclone.utils.Constants;
import anand.android.applockclone.utils.SharedPref;
import anand.android.applockclone.ui.activities.MainActivity;
import anand.android.applockclone.utils.ValidatePassword;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anandm on 08/02/17.
 */

public class InitialSetupFragment extends Fragment {

    private static final String TAG = InitialSetupFragment.class.getSimpleName();
    private Context context;
    private ArrayList<Integer> newPattern = new ArrayList<>();
    private ArrayList<Integer> confirmPattern = new ArrayList<>();
    private Boolean validatePattern = false;
    private int entryCount = 0;
    private String pass = "";
    private SharedPref sharedPref = new SharedPref();

    @BindView(R.id.connectPattern) ConnectPatternView _connectPattern;
    @BindView(R.id.txtPatternLabel) TextView _txtPatternLabel;
    @BindView(R.id.btnRetry) Button _btnRetry;
    @BindView(R.id.btnConfirm) Button _btnConfirm;

    @BindString(R.string.txt_label_pattern_once_again) String txt_label_pattern_once_again;
    @BindString(R.string.txt_label_confirm_pattern) String txt_label_confirm_pattern;
    @BindString(R.string.txt_label_draw_pattern) String txt_label_draw_pattern;
    @BindString(R.string.btn_confirm) String btn_confirm;
    @BindString(R.string.btn_cancel) String btn_cancel;
    @BindString(R.string.btn_retry) String btn_retry;
    @BindString(R.string.toast_error_pattern_differ) String toast_error_pattern_differ;


    public static InitialSetupFragment newInstnace() {
        return new InitialSetupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        View rootView = inflater.inflate(R.layout.fragment_initial_setup, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _connectPattern.setOnConnectPatternListener(new ConnectPatternView.OnConnectPatternListener() {
            @Override
            public void onPatternEntered(ArrayList<Integer> result) {
                Log.e(TAG, result.size() + " ");
                if (entryCount < 1) {
                    newPattern = result;
                    Log.e(TAG, newPattern.size() + " " + entryCount);
                    entryCount++;

                    //Set draw again view
                    setDrawAgainView();
                    return;
                }

                //Get confirmed patter array
                confirmPattern =result;

                for (int i = 0;  i < result.size(); i++) {
                    pass = pass+result.get(i);
                }
                Log.e(TAG, result.size() + " final " + pass);


                //Check if patterns match
                if (!isPatternMatch()) {
                    AppToast.showToast(getActivity(), toast_error_pattern_differ);
                    return;
                }

                //Set confirmation view
                setConfirmView();
            }

            @Override
            public void onPatternAbandoned() {
                Log.e(TAG, "Abandoned");
            }

            @Override
            public void animateInStart() {

            }

            @Override
            public void animateInEnd() {

            }

            @Override
            public void animateOutStart() {

            }

            @Override
            public void animateOutEnd() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setInitialView();
    }

    private boolean isPatternMatch() {
        return ValidatePassword.isTwoArrayListsWithSameValues(newPattern, confirmPattern);
    }

    @OnClick(R.id.btnConfirm)
    public void validatePattern(Button button) {
        if (isPatternMatch()) {
            Log.e(TAG, pass + "");
            sharedPref.saveStringInPref(getActivity(), Constants.SP_PATTERN, pass);
            sharedPref.saveBooleanInPref(getActivity(), Constants.SP_PATTERN_EXISTS, true);
            context.startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        } else {
            setDrawAgainView();
            AppToast.showToast(getActivity(), toast_error_pattern_differ);
        }
    }

    @OnClick(R.id.btnRetry)
    public void negativeButton(Button button) {
        if (entryCount == 1) {
            //If retry option clicked
            setInitialView();
            return;
        }
        //If cancel option clicked
        getActivity().finish();
    }

    private void setInitialView() {

        //Set views
        _txtPatternLabel.setText(txt_label_draw_pattern);
        _btnConfirm.setTextColor(getResources().getColor(android.R.color.darker_gray));
        _btnConfirm.setClickable(false);
        _btnRetry.setText(btn_cancel);

        //Clear arrays
        newPattern.clear();
        confirmPattern.clear();

        //Set flags
        validatePattern = false;
        entryCount = 0;
        pass = "";
    }

    private void setDrawAgainView() {

        //Set views
        _txtPatternLabel.setText(txt_label_pattern_once_again);
        _btnConfirm.setTextColor(getResources().getColor(android.R.color.darker_gray));
        _btnConfirm.setClickable(false);
        _btnRetry.setText(btn_retry);

        //Clear arrays
        confirmPattern.clear();

        //set flags
        validatePattern = true;
        entryCount++;

    }

    private void setConfirmView() {

        //Set views
        _txtPatternLabel.setText(txt_label_confirm_pattern);
        _btnConfirm.setTextColor(getResources().getColor(android.R.color.white));
        _btnConfirm.setClickable(true);
        _btnRetry.setText(btn_retry);

        //Set flags
        validatePattern = true;
    }
}
