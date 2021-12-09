package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PokemonsViewModel extends ViewModel {
    private PokemonsRepository pokemonsRepository;
    private LiveData<PokemonsData> pokemonsData;

    public PokemonsViewModel(){
        this.pokemonsRepository = new PokemonsRepository();  // link with repository
        this.pokemonsData = this.pokemonsRepository.getPokemonsData();  // get data after fetch
    }

    public LiveData<PokemonsData> getPokemonsData() {
        return pokemonsData;
    }

    public void loadForecast(int limit) {
        this.pokemonsRepository.loadPokemons(limit);
    }
}
