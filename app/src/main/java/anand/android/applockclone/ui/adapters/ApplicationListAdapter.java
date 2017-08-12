package anand.android.applockclone.ui.adapters;

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

import anand.android.applockclone.R;
import anand.android.applockclone.ui.adapters.modle.AppInfo;
import anand.android.applockclone.utils.Constants;
import anand.android.applockclone.utils.SharedPref;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplicationListAdapter extends RecyclerView.Adapter<ApplicationListAdapter.ViewHolder> {

    private static final String TAG = ApplicationListAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<AppInfo> installedAppList;
    private String lockStatus;
    private List<AppInfo> lockedAppList = new ArrayList<AppInfo>();
    private SharedPref sharedPref = new SharedPref();

    public ApplicationListAdapter(ArrayList<AppInfo> installedAppList, Activity activity, String lockStatus) {
        this.installedAppList = installedAppList;
        this.context = activity;
        this.lockStatus = lockStatus;
        filterApps();
    }

    private void filterApps() {
        List<AppInfo> lockedFilteredAppList = new ArrayList<AppInfo>();
        List<AppInfo> unlockedFilteredAppList = new ArrayList<AppInfo>();
        boolean flag = true;
        if (lockStatus.matches(Constants.LOCKED) || lockStatus.matches(Constants.UNLOCKED)) {
            for (int i = 0; i < installedAppList.size(); i++) {
                flag = true;
                if (sharedPref.getLocked(context) != null) {
                    for (int j = 0; j < sharedPref.getLocked(context).size(); j++) {
                        if (installedAppList.get(i).getPackageName().matches(sharedPref.getLocked(context).get(j))) {
                            lockedFilteredAppList.add(installedAppList.get(i));
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    unlockedFilteredAppList.add(installedAppList.get(i));
                }
            }
            if (lockStatus.matches(Constants.LOCKED)) {
                installedAppList.clear();
                installedAppList.addAll(lockedFilteredAppList);
            } else if (lockStatus.matches(Constants.UNLOCKED)) {
                installedAppList.clear();
                installedAppList.addAll(unlockedFilteredAppList);
            }
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
                    sharedPref.addLocked(context, appInfo1.getPackageName());
                } else {
                    sharedPref.removeLocked(context, appInfo1.getPackageName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return installedAppList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.applicationName) TextView applicationName;
        @BindView(R.id.switchView) Switch switchView;
        @BindView(R.id.card_view) CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}