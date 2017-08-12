package anand.android.applockclone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anand.android.applockclone.R;
import anand.android.applockclone.ui.activities.MainActivity;
import anand.android.applockclone.ui.adapters.ApplicationListAdapter;

public class AppListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ApplicationListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    static String requiredAppsType;
    private static final String TYPE = "type";

    public static AppListFragment newInstance(String type) {
        AppListFragment fragment = new AppListFragment();
        Bundle arg = new Bundle();
        arg.putString(TYPE, type);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_app_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewApp);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ApplicationListAdapter(((MainActivity) getActivity()).getListOfInstalledApp(getActivity()),
                getActivity(), getArguments().getString(TYPE));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        return v;

    }

}
