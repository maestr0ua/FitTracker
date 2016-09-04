package com.task.webchallengetask.ui.modules.activity.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.data.database.tables.ActionParametersTable;
import com.task.webchallengetask.ui.modules.activity.adapters.ActivityListAdapter;
import com.task.webchallengetask.ui.modules.activity.presenters.ActivityListPresenter;
import com.task.webchallengetask.ui.base.BaseFragment;
import com.task.webchallengetask.ui.custom.DividerItemDecoration;

import java.util.List;


public class ActivityListFragment extends BaseFragment<ActivityListPresenter>
        implements ActivityListPresenter.ActivityListView {

    private RecyclerView rvActivities;
    private FloatingActionButton fabCreateActivity;
    private ActivityListAdapter mAdapter;
    private TextView tvTextHolder;

    public static ActivityListFragment newInstance() {

        Bundle args = new Bundle();

        ActivityListFragment fragment = new ActivityListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() {
        return R.string.activity_list;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_activity_list;
    }

    @Override
    public ActivityListPresenter initPresenter() {
        return new ActivityListPresenter();
    }

    @Override
    public void findUI(View rootView) {
        fabCreateActivity = (FloatingActionButton) rootView.findViewById(R.id.fab);
        rvActivities = (RecyclerView) rootView.findViewById(R.id.rvActivityList_FAL);
        tvTextHolder = (TextView) rootView.findViewById(R.id.tvHolderText_FAL);
    }

    @Override
    public void setupUI() {
        fabCreateActivity.setOnClickListener(view -> getPresenter().onFABClicked());

        mAdapter = new ActivityListAdapter();

        rvActivities.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvActivities.setAdapter(mAdapter);
        mAdapter.onItemClickListener(model -> getPresenter().onActivityClicked(model));
    }

    @Override
    public void addActivities(List<ActionParametersTable> _data) {
        mAdapter.addDataByDay(_data);
    }

    @Override
    public void showHolder() {
        tvTextHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHolder() {
        tvTextHolder.setVisibility(View.GONE);
    }
}
