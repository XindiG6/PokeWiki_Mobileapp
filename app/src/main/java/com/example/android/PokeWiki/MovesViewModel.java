package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovesViewModel extends ViewModel {
    private MovesRepository movesRepository;
    private LiveData<MovesData> movesData;

    public MovesViewModel(){
        this.movesRepository = new MovesRepository();  // link with repository
        this.movesData = this.movesRepository.getMovesData();  // get data after fetch
    }

    public LiveData<MovesData> getMovesData() {
        return movesData;
    }

    public void loadForecast(int limit) {
        this.movesRepository.loadMoves(limit);
    }
}
