package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PokemonsDetailViewModel extends ViewModel {
    private PokemonsRepository pokemonsRepository;
    private LiveData<PokemonsDetail> pokemonsDetailLiveData;
    public PokemonsDetailViewModel(){
        this.pokemonsRepository = new PokemonsRepository();  // link with repository
        this.pokemonsDetailLiveData = this.pokemonsRepository.getPokemonsDetail();  // get data after fetch
    }

    public LiveData<PokemonsDetail> getPokemonsDetailLiveData() {
        return pokemonsDetailLiveData;
    }
    public void loadDetailResults(String query){
        this.pokemonsRepository.loadPokemonsDetail(query);
    }

}
