package com.task.webchallengetask.ui.modules.program.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.task.webchallengetask.global.programs.difficults.Difficult;

import java.util.List;


public class DifficultAdapter extends ArrayAdapter<String> {

    private List<Difficult> data;

    public DifficultAdapter(Context context, int resource, List<Difficult> _data) {
        super(context, resource);
        data = _data;
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public Difficult getDifficult(int position) {
        return data.get(position);
    }



}
