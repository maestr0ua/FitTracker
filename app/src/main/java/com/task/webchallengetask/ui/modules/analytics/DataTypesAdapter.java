package com.task.webchallengetask.ui.modules.analytics;

import android.content.Context;
import android.support.v4.util.Pair;
import android.widget.ArrayAdapter;

import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.difficults.Difficult;

import java.util.List;


public class DataTypesAdapter extends ArrayAdapter<String> {

    private List<Pair<Constants.DATA_TYPES, String>> data;

    public DataTypesAdapter(Context context, int resource, List<Pair<Constants.DATA_TYPES, String>> _data) {
        super(context, resource);
        data = _data;
    }

    @Override
    public String getItem(int position) {
        return data.get(position).second;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public Pair<Constants.DATA_TYPES, String> getSelected(int position) {
        return data.get(position);
    }

    public Pair<Constants.DATA_TYPES, String> getDataType(int position) {
        return data.get(position);
    }
}
