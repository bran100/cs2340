package com.cs2340.noexceptions.homelesshelper;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * A custom array adapter for shelters
 */
class ShelterAdapter extends ArrayAdapter<Shelter> implements Filterable {
    private final Context mContext;
    private List<Shelter> shelterList = new ArrayList<>();
    private List<Shelter> shelterListCopy = new ArrayList<>();
    private final LayoutInflater layoutInflater;

    /**
     * The constructor for a custom array adapter
     * @param context The context of the app
     * @param list A list of shelters
     */
    public ShelterAdapter(Context context, List<Shelter> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        mContext = context;
        shelterList = list;
        shelterListCopy = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View temp = convertView;
        temp = layoutInflater.inflate(R.layout.shelter_item, parent, false);
        View listItem = temp;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.shelter_item, parent, false);
        }

        Shelter currentShelter = shelterListCopy.get(position);

        TextView shelter = listItem.findViewById(R.id.textView_shelter);
        shelter.setText(currentShelter.getName());

        TextView age = listItem.findViewById(R.id.textView_age);
        age.setText(currentShelter.getAge());

        TextView gender = listItem.findViewById(R.id.textView_gender);
        gender.setText(currentShelter.getGender());

        return listItem;
    }
    @Override
    public int getCount() {
        return shelterListCopy.size();
    }

    @Override
    public Shelter getItem(int arg0) {
        if(shelterListCopy != null){
            try {
                return shelterListCopy.get(arg0);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if ((charSequence == null) || (charSequence.length() == 0)) {
                    results.count = shelterList.size();
                    results.values = shelterList;
                } else {
                    Collection<Shelter> resultsData = new ArrayList<>();
                    String searchstr = charSequence.toString().toLowerCase();
                    for (Shelter s : shelterList) {
                        if (searchstr.startsWith("anyone") && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if (s.getName().toLowerCase().contains(searchstr)
                                && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if ((s.getAge().toLowerCase().contains(searchstr)
                                || s.getAge().toLowerCase().startsWith(searchstr)
                                || s.getGender().contains("Anyone"))
                                && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if ((s.getGender().toLowerCase().startsWith(searchstr)
                                || ((s.getGender().toLowerCase().contains("/"))
                                && (("male".equals(searchstr)
                                || "female".equals(searchstr)))))
                                && (!resultsData.contains(s))) {
                            resultsData.add(s);
                        }
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                shelterListCopy = (ArrayList<Shelter>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
