package anand.android.applockcloneminapi23.views.fragments;

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

import anand.android.applockcloneminapi23.R;
import anand.android.applockcloneminapi23.utils.AppConstants;
import anand.android.applockcloneminapi23.utils.AppToast;
import anand.android.applockcloneminapi23.utils.SharedPref;
import anand.android.applockcloneminapi23.utils.ValidatePass;
import anand.android.applockcloneminapi23.views.activities.MainActivity;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by anandm on 09/02/17.
 *
 */

public class AccessPatternViewFragment extends Fragment {

    private static final String TAG = InitialSetupFragment.class.getSimpleName();
    private Context context;
    private String enteredPattern = "";
    private String savedPattern;

    @BindView(R.id.connectPattern) ConnectPatternView _connectPattern;
    @BindView(R.id.txtPatternLabel) TextView _txtPatternLabel;
    @BindView(R.id.btnRetry) Button _btnRetry;
    @BindView(R.id.btnConfirm) Button _btnConfirm;
    @BindView(R.id.btnForgot) Button _btnForgot;
    @BindView(R.id.txtSmallLabel) TextView _txtSmallLabel;

    @BindString(R.string.txt_label_draw_pattern) String txt_label_draw_pattern;
    @BindString(R.string.toast_error_wrong_pattern) String toast_error_wrong_pattern;

    public static AccessPatternViewFragment newInstance() {
        return new AccessPatternViewFragment();
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

        setConfirmView();

        _connectPattern.setOnConnectPatternListener(new ConnectPatternView.OnConnectPatternListener() {
            @Override
            public void onPatternEntered(ArrayList<Integer> result) {
                savedPattern = SharedPref.getStringFromPref(getActivity(), AppConstants.SP_PATTERN);

                for (int i = 0;  i < result.size(); i++) {
                    enteredPattern = enteredPattern +result.get(i);
                }
                Log.e(TAG, savedPattern + " final " + enteredPattern);

                if (isPatternMatch()) {
                    context.startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                } else {
                    enteredPattern = "";
                    AppToast.showToast(getActivity(), toast_error_wrong_pattern);
                }
            }

            @Override
            public void onPatternAbandoned() {
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
        setConfirmView();
    }

    private boolean isPatternMatch() {
        return enteredPattern.equals(savedPattern);
    }

    private void setConfirmView() {
        //Set views
        _txtPatternLabel.setText(txt_label_draw_pattern);
        _btnForgot.setVisibility(View.VISIBLE);
        _txtSmallLabel.setVisibility(View.GONE);
        _btnRetry.setVisibility(View.GONE);
        _btnConfirm.setVisibility(View.GONE);
    }
}
