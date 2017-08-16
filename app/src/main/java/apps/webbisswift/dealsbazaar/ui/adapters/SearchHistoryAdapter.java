package apps.webbisswift.dealsbazaar.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import apps.webbisswift.dealsbazaar.domain.database.SearchHistory;

/**
 * Created by biswas on 28/05/2017.
 */

public class SearchHistoryAdapter extends ArrayAdapter<SearchHistory> implements Filterable {

    private ArrayList<SearchHistory> mOriginalValues;
    private List<SearchHistory> fullList;
    private ApproxFilter mFilter;
    //private final Object mLock = new Object();

    public SearchHistoryAdapter(Context context,int layoutId, int textViewResourceId, List<SearchHistory> objects) {
        super(context,layoutId ,textViewResourceId, objects);

        fullList =  objects;
        mOriginalValues = new ArrayList<SearchHistory>(fullList);
    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public SearchHistory getItem(int position) {
        return fullList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull  ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }



    @NonNull
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ApproxFilter();
        }
        return mFilter;
    }


    private class ApproxFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = mOriginalValues;
                results.count = mOriginalValues.size();
            }
            else {
                // We perform filtering operation
                List<SearchHistory> nApproxList = new ArrayList<SearchHistory>();
                for (SearchHistory f : mOriginalValues) {
                    if (f.getQuery().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        nApproxList.add(f);
                    }
                }

                results.values = nApproxList;
                results.count = nApproxList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Now we have to inform the adapter about the new list filtered
            if (filterResults.count == 0)
                notifyDataSetInvalidated();
            else {
                fullList =  (List<SearchHistory>)filterResults.values;
                notifyDataSetChanged();
            }
        }


    }


}