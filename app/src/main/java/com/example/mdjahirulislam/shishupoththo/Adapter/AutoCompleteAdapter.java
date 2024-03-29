package com.example.mdjahirulislam.shishupoththo.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> mListData;

    public AutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mListData = new ArrayList<>();
    }

    public void setData(List<String> list) {
        mListData.clear();
        mListData.addAll(list);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return mListData.get(position);
    }

    /**
     * Used to Return the full object directly from adapter.
     *
     * @param position
     * @return
     */
    public String getObject(int position) {
        return mListData.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mListData;
                    filterResults.count = mListData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }
}
