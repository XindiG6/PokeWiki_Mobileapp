package com.example.android.PokeWiki;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovesAdapter extends RecyclerView.Adapter<MovesAdapter.MovesItemViewHolder> implements Filterable {
    private MovesData movesData;
    private OnMovesItemClickListener onMovesItemClickListener;
    private MovesData movesDataCopy;

    public interface OnMovesItemClickListener {
        void onMovesItemClickListener(MovesNames movesNames);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MovesNames> barryData_temp = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                barryData_temp.addAll(movesDataCopy.getMovesNames());
            } else {
                for (MovesNames names : movesDataCopy.getMovesNames()) {
                    if (names.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        barryData_temp.add(names);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = barryData_temp;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movesData.getMovesNames().clear();
            movesData.getMovesNames().addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public MovesAdapter(MovesAdapter.OnMovesItemClickListener onMovesItemClickListener){
       this.onMovesItemClickListener = onMovesItemClickListener;
        //this.barryDataCopy = barryData;
    }

    @NonNull
    @Override
    public MovesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_moves_list, parent, false);
        return new MovesAdapter.MovesItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovesItemViewHolder holder, int position) {
        holder.bind(movesData.getMovesNames().get(position));
    }

    public void updateForecastData(MovesData movesData) {
        this.movesData = movesData;
        this.movesDataCopy = movesData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.movesData == null ) {
            return 0;
        } else {
            return this.movesData.getMovesNames().size();
        }
    }
    class MovesItemViewHolder extends RecyclerView.ViewHolder{
        final private TextView NameTV;
        public MovesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTV = itemView.findViewById(R.id.moves_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMovesItemClickListener.onMovesItemClickListener(
                    movesData.getMovesNames().get(getAdapterPosition())
                    );
                }
            });
        }
        public void bind(MovesNames movesData){
            String upperName = movesData.getName().substring(0, 1).toUpperCase() + movesData.getName().substring(1).toLowerCase();
            NameTV.setText(upperName);

        }
    }



}
