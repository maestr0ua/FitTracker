package com.task.webchallengetask.ui.modules.program;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.custom.DividerItemDecoration;
import com.task.webchallengetask.ui.modules.program.adapters.ProgramListAdapter;
import com.task.webchallengetask.ui.modules.program.presenters.ProgramsListPresenter;
import com.task.webchallengetask.ui.base.BaseFragment;
import com.task.webchallengetask.ui.dialogs.AddProgramDialog;
import com.task.webchallengetask.ui.dialogs.DialogListener;

import java.util.List;


public class ProgramsListFragment extends BaseFragment<ProgramsListPresenter>
        implements ProgramsListPresenter.ProgramListView {

    private FloatingActionButton fabAddProgram;
    private RecyclerView rvPrograms;
    private ProgramListAdapter mAdapter;
    private TextView tvTextHolder;

    public static ProgramsListFragment newInstance() {

        Bundle args = new Bundle();
        ProgramsListFragment fragment = new ProgramsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() {
        return R.string.programs;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_program_list;
    }

    @Override
    public ProgramsListPresenter initPresenter() {
        return new ProgramsListPresenter();
    }

    @Override
    public void findUI(View rootView) {
        fabAddProgram = (FloatingActionButton) rootView.findViewById(R.id.fab);
        rvPrograms = (RecyclerView) rootView.findViewById(R.id.rvPrograms_FPL);
        tvTextHolder = (TextView) rootView.findViewById(R.id.tvHolderText_FPL);
    }

    @Override
    public void setupUI() {
        RxUtils.click(fabAddProgram, view -> getPresenter().onAddProgramClicked());
        mAdapter = new ProgramListAdapter();

        rvPrograms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvPrograms.setLayoutManager(mLayoutManager);
        rvPrograms.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvPrograms.setAdapter(mAdapter);
        mAdapter.onItemClickListener(position -> getPresenter().onProgramClicked(position));
    }

    @Override
    public void showAddProgramDialog(DialogListener _listener) {
        AddProgramDialog dialog = new AddProgramDialog();
        dialog.setOnDismissListener(_listener);
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void showPrograms(List<ProgramTable> _data) {
        mAdapter.setData(_data);
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
