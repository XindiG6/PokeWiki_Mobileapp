package com.example.android.PokeWiki;

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

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonsItemViewHolder> implements Filterable {

    private PokemonsData pokemonsData;
    private PokemonsAdapter.OnPokemonsItemClickListener onPokemonsItemClickListener;
    private PokemonsData PokemonsDataCopy;

    public interface OnPokemonsItemClickListener {
        void onPokemonsItemClickListener(PokemonsName pokemonsName);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PokemonsName> barryData_temp = new ArrayList<>();
            if (constraint.toString().isEmpty()) {
                barryData_temp.addAll(PokemonsDataCopy.getPokemonsName());
            } else {
                for (PokemonsName names : PokemonsDataCopy.getPokemonsName()) {
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
            pokemonsData.getPokemonsName().clear();
            pokemonsData.getPokemonsName().addAll((List) results.values);
            notifyDataSetChanged();
        }

    };
    public PokemonsAdapter(PokemonsAdapter.OnPokemonsItemClickListener onPokemonsItemClickListener){
        this.onPokemonsItemClickListener = onPokemonsItemClickListener;
    }

    @NonNull
    @Override
    public PokemonsAdapter.PokemonsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_pokemons_list, parent, false);
        return new PokemonsAdapter.PokemonsItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonsAdapter.PokemonsItemViewHolder holder, int position) {
        holder.bind(pokemonsData.getPokemonsName().get(position));
    }

    public void updateForecastData(PokemonsData pokemonsData) {
        this.pokemonsData = pokemonsData;
        this.PokemonsDataCopy = pokemonsData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.pokemonsData == null ) {
            return 0;
        } else {
            return this.pokemonsData.getPokemonsName().size();
        }
    }
    class PokemonsItemViewHolder extends RecyclerView.ViewHolder{
        final private TextView NameTV;
        public PokemonsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTV = itemView.findViewById(R.id.pokemon_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPokemonsItemClickListener.onPokemonsItemClickListener(
                            pokemonsData.getPokemonsName().get(getAdapterPosition())
                    );
                }
            });
        }
        public void bind(PokemonsName pokemonsName){
            String upperName = pokemonsName.getName().substring(0, 1).toUpperCase() + pokemonsName.getName().substring(1).toLowerCase();
            NameTV.setText(upperName);

        }
    }
}
