package com.example.mdjahirulislam.shishupoththo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mdjahirulislam.shishupoththo.R;
import com.example.mdjahirulislam.shishupoththo.model.MedicineModelClass;

import java.util.ArrayList;
import java.util.List;
public class MyAdapterForAutoComplete extends ArrayAdapter<MedicineModelClass> {
    private Context context;
    private int resourceId;
    private List<MedicineModelClass> items, tempItems, suggestions;
    public MyAdapterForAutoComplete(@NonNull Context context, int resourceId, ArrayList<MedicineModelClass> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            MedicineModelClass medicineModelClass = getItem(position);
            TextView name = (TextView) view.findViewById(R.id.textView);
            ImageView imageView = view.findViewById(R.id.imageView);

            if (medicineModelClass.getDrugType().equalsIgnoreCase("Injection")){
                imageView.setImageResource(R.drawable.injection);
            }else  if (medicineModelClass.getDrugType().equalsIgnoreCase("capsule")){
                imageView.setImageResource(R.drawable.two_pills);
            }else  if (medicineModelClass.getDrugType().equalsIgnoreCase("suspension")){
                imageView.setImageResource(R.drawable.suspension);
            }else  if (medicineModelClass.getDrugType().equalsIgnoreCase("tablet")){
                imageView.setImageResource(R.drawable.pills);
            }else  if (medicineModelClass.getDrugType().equalsIgnoreCase("syrup")){
                imageView.setImageResource(R.drawable.syrup);
            }
            name.setText(medicineModelClass.getDrugName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public MedicineModelClass getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }
    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            MedicineModelClass fruit = (MedicineModelClass) resultValue;
            return fruit.getDrugName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (MedicineModelClass fruit: tempItems) {
                    if (fruit.getDrugName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(fruit);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<MedicineModelClass> tempValues = (ArrayList<MedicineModelClass>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (MedicineModelClass fruitObj : tempValues) {
                    add(fruitObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}