package com.example.android.PokeWiki;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BarryAdapter extends RecyclerView.Adapter<BarryAdapter.BarryItemViewHolder> implements Filterable {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BarryData barryData;
    private BarryData barryDataCopy;
    private OnBarryItemClickListener onBarryItemClickListener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BarryNames> barryData_temp = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                barryData_temp.addAll(barryDataCopy.getBarryNames());
                Log.d(TAG, "performFiltering: i am empty");
            }else {
                for(BarryNames names: barryDataCopy.getBarryNames()){
                    if(names.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        barryData_temp.add(names);
                    };
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = barryData_temp;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            barryData.getBarryNames().clear();
            barryData.getBarryNames().addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnBarryItemClickListener {
        void onBarryItemClickListener(BarryNames barryNames);
    }
    public BarryAdapter(OnBarryItemClickListener onBarryItemClickListener){
        this.onBarryItemClickListener = onBarryItemClickListener;
        //this.barryDataCopy = barryData;
    }

//    public void filter(String target){
//        items.clear();
//        if(text.isEmpty()){
//            items.addAll(itemsCopy);
//        } else{
//            text = text.toLowerCase();
//            for(PhoneBookItem item: itemsCopy){
//                if(item.name.toLowerCase().contains(text) || item.phone.toLowerCase().contains(text)){
//                    items.add(item);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public  BarryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_barry_list, parent, false);
        return new BarryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BarryItemViewHolder holder, int position) {
        holder.bind(barryData.getBarryNames().get(position));
    }

    public void updateForecastData(BarryData barryData) {
        this.barryData = barryData;
        this.barryDataCopy = barryData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.barryData == null ) {
            return 0;
        } else {
            return this.barryData.getBarryNames().size();
        }
    }

    class BarryItemViewHolder extends RecyclerView.ViewHolder{
        final private TextView NameTV;
//        final private TextView FirmnessTV;
//        final private TextView TypeTV;
//        final private TextView PowerTV;
//        final private TextView PowerNumTV;

        public BarryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTV = itemView.findViewById(R.id.barry_name);
//            FirmnessTV = itemView.findViewById(R.id.barry_firmness);
//            TypeTV = itemView.findViewById(R.id.barry_type);
//            PowerTV = itemView.findViewById(R.id.barry_power);
//            PowerNumTV = itemView.findViewById(R.id.barry_power_num);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onBarryItemClickListener.onBarryItemClickListener(
                           barryData.getBarryNames().get(getAdapterPosition())
                   );

                }
            });
        }

        public void bind(BarryNames barryData){
            String upperName = barryData.getName().substring(0, 1).toUpperCase() + barryData.getName().substring(1).toLowerCase();
            NameTV.setText(upperName);
        }
    }
}
