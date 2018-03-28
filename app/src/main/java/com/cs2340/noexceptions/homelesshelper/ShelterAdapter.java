package com.cs2340.noexceptions.homelesshelper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nenadstanic on 3/5/18.
 */

public class ShelterAdapter extends ArrayAdapter<Shelter> implements Filterable {
    private Context mContext;
    private List<Shelter> shelterList = new ArrayList<>();
    private List<Shelter> shelterListCopy = new ArrayList<>();
    private LayoutInflater layoutInflater;
    public ShelterAdapter(Context context, ArrayList<Shelter> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        mContext = context;
        shelterList = list;
        shelterListCopy = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.shelter_item,null);
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.shelter_item,parent,false);

        Shelter currentShelter = shelterListCopy.get(position);

        TextView shelter = (TextView) listItem.findViewById(R.id.textView_shelter);
        shelter.setText(currentShelter.getName());

        TextView age = (TextView) listItem.findViewById(R.id.textView_age);
        age.setText(currentShelter.getAge());

        TextView gender = (TextView) listItem.findViewById(R.id.textView_gender);
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
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                if (charSequence == null || charSequence.length() == 0) {
                    results.count = shelterList.size();
                    results.values = shelterList;
                } else {
                    List<Shelter> resultsData = new ArrayList<>();
                    String searchstr = charSequence.toString().toLowerCase();
                    for (Shelter s : shelterList) {
                        if (searchstr.startsWith("anyone") && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if (s.getName().toLowerCase().contains(searchstr) && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if ((s.getAge().toLowerCase().startsWith(searchstr) || s.getGender().contains("Anyone")) && !resultsData.contains(s)) {
                            resultsData.add(s);
                        }
                        if ((s.getGender().toLowerCase().startsWith(searchstr) || (s.getGender().toLowerCase().contains("/")
                                && (searchstr.equals("male") || searchstr.equals("female"))) && !resultsData.contains(s))) {
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
