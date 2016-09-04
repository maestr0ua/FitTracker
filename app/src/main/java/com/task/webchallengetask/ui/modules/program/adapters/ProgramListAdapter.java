package com.task.webchallengetask.ui.modules.program.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.data.database.tables.ProgramTable;

import java.util.ArrayList;
import java.util.List;


public class ProgramListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<ProgramTable> mData;
    private OnItemClickListener mListener;

    public ProgramListAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        ProgramItemHolder holder = new ProgramItemHolder(v);
        holder.setExternalListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ProgramItemHolder)holder).tvTitle.setText(mData.get(position).getName());
        ((ProgramItemHolder)holder).tvSubTitle.setText(mData.get(position).getDifficult());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<ProgramTable> _data) {
        mData.clear();
        mData.addAll(_data);
        notifyDataSetChanged();
    }

    class ProgramItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnItemClickListener mExternalListener;
        TextView tvTitle;
        TextView tvSubTitle;

        public ProgramItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_PI);
            tvSubTitle = (TextView) itemView.findViewById(R.id.tvSubTitle_PI);
        }

        @Override
        public void onClick(View v) {
            if (mExternalListener != null) mExternalListener.onClick(getAdapterPosition());
        }

        public void setExternalListener(OnItemClickListener _listener) {
            mExternalListener = _listener;
        }

    }

    public void onItemClickListener(OnItemClickListener _listener) {
        mListener = _listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }



}
