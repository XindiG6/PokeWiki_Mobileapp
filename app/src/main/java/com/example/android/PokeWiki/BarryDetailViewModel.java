package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class BarryDetailViewModel extends ViewModel {
    private BarryRepository barryRepository;
    private LiveData<BerryDetail> berryDetailLiveData;
    public BarryDetailViewModel(){
        this.barryRepository = new BarryRepository();  // link with repository
        this.berryDetailLiveData = this.barryRepository.getBerryDetail();  // get data after fetch
    }


    public LiveData<BerryDetail> getBerryDetailLiveData() {
        return berryDetailLiveData;
    }

    public void loadDetailResults(String query){
        this.barryRepository.loadBerryDetail(query);
    }
}
