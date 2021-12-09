package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovesDetailViewModel extends ViewModel {
    private MovesRepository movesRepository;
    private LiveData<MovesDetail> movesDetailLiveData;
    public MovesDetailViewModel(){
        this.movesRepository = new MovesRepository();  // link with repository
        this.movesDetailLiveData = this.movesRepository.getMovesDetail();  // get data after fetch
    }

    public LiveData<MovesDetail> getMovesDetailLiveData() {
        return movesDetailLiveData;
    }

    public void loadDetailResults(String query){
        this.movesRepository.loadMovesDetail(query);
    }


}
