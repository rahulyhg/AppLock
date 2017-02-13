package anand.android.applockcloneminapi23.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anand.android.applockcloneminapi23.R;
import anand.android.applockcloneminapi23.utils.AppConstants;
import anand.android.applockcloneminapi23.utils.SharedPref;
import anand.android.applockcloneminapi23.views.adapters.modle.AppInfo;
import anand.android.applockcloneminapi23.views.fragments.AppListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by amitshekhar on 28/04/15.
 *
 */

public class ApplicationListAdapter extends RecyclerView.Adapter<ApplicationListAdapter.ViewHolder> {

    private static final String TAG = ApplicationListAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<AppInfo> installedAppList;
    private String lockStatus;
    private List<AppInfo> lockedAppList = new ArrayList<AppInfo>();
    private List<AppInfo> unlockedAppList = new ArrayList<AppInfo>();

    public ApplicationListAdapter(ArrayList<AppInfo> installedAppList, Activity activity, String lockStatus) {
        this.installedAppList = installedAppList;
        this.context = activity;
        this.lockStatus = lockStatus;
        filterApps();
    }

    private void filterApps() {
        boolean flag = true;

        //Iterate through all installed apps
        for (int i = 0; i < installedAppList.size(); i++) {
            flag = true;

            //Get all locked apps
            if (SharedPref.getLocked(context) != null) {
                for (int j = 0; j < SharedPref.getLocked(context).size(); j++) {
                    AppInfo installedAppInfo = installedAppList.get(i);
                    AppInfo lockedAppInfo = SharedPref.getLocked(context).get(j);

                    //Check if the app has lock activated
                    if (installedAppInfo.getPackageName().matches(lockedAppInfo.getPackageName())) {
                        installedAppInfo.setLocked(true);
                        lockedAppList.add(installedAppInfo);
                        flag = false;
                    }
                }
            }

            if (flag) {
                unlockedAppList.add(installedAppList.get(i));
            }
        }

        if (lockStatus.matches(AppConstants.LOCKED)) {
            installedAppList.clear();
            installedAppList.addAll(lockedAppList);
        } else if (lockStatus.matches(AppConstants.UNLOCKED)) {
            installedAppList.clear();
            installedAppList.addAll(unlockedAppList);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AppInfo appInfo = installedAppList.get(position);

        holder.applicationName.setText(appInfo.getName());
        holder.icon.setBackgroundDrawable(appInfo.getIcon());

        holder.switchView.setOnCheckedChangeListener(null);
        holder.switchView.setTag(appInfo);
        if (appInfo.getLocked()) {
            holder.switchView.setChecked(true);
        } else {
            holder.switchView.setChecked(false);
        }

        holder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppInfo appInfo1 = (AppInfo) buttonView.getTag();
                Log.e(TAG, "is " + appInfo1.getPackageName());
                if (isChecked) {
                    lockedAppList.add(appInfo1);
                    SharedPref.saveLocked(context, lockedAppList);
                } else {
                    SharedPref.removeLocked(context, appInfo1.getPackageName());
                }
            }
        });

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.switchView.performClick();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return installedAppList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.applicationName) TextView applicationName;
        @BindView(R.id.switchView) Switch switchView;
        @BindView(R.id.card_view) CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}