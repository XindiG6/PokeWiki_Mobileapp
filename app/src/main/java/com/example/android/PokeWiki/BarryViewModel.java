package com.example.android.PokeWiki;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class BarryViewModel extends ViewModel {
    private BarryRepository barryRepository;
    private LiveData<BarryData> barryData;

    public BarryViewModel(){
        this.barryRepository = new BarryRepository();  // link with repository
        this.barryData = this.barryRepository.getBarryData();  // get data after fetch
    }


    public LiveData<BarryData> getBarryData() {
        return barryData;
    }

    public void loadForecast(int limit) {
        this.barryRepository.loadBarry(limit);
    }
}
