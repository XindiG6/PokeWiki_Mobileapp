package com.example.android.PokeWiki;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarryRepository {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";  // berry api

    private MutableLiveData<BarryData> barryData;
    private MutableLiveData<BerryDetail> berryDetail;

    private BarryService barryService;

    private int currentUnit;


    public BarryRepository(){
        this.barryData = new MutableLiveData<>();
        this.berryDetail = new MutableLiveData<>();
        this.barryData.setValue(null);
        this.berryDetail.setValue(null);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BarryNames.class, new BarryNames.JsonDeserializer())
                .registerTypeAdapter(BerryDetail.class, new BerryDetail.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.barryService = retrofit.create(BarryService.class);
    }


    public MutableLiveData<BarryData> getBarryData() {
        return barryData;
    }
    public MutableLiveData<BerryDetail> getBerryDetail() {
        return berryDetail;
    }


    public void loadBarry(int unit){
        if(shouldExecuteSearch(unit)){
            Log.d(TAG, "running new search for this query: " + unit);
            this.currentUnit = unit;
            this.executeSearch(unit);
        }else {
            Log.d(TAG, "using cached search results for this query: " + unit);
        }
    }

    public void executeSearch(int limit) {

        this.barryData.setValue(null);
        Call<BarryData> req = this.barryService.fetchBarry(limit);
        Log.d(TAG, " i am before response ");
        req.enqueue(new Callback<BarryData>() {
                        @Override
                        public void onResponse(Call<BarryData> call, Response<BarryData> response) {
                            Log.d(TAG, "onResponse: i am in response");
                            if(response.code() == 200){
                                Log.d(TAG, "successful API request: " + call.request().url());
                                //Log.d(TAG, "successful API request: " + response.body().getBarryNames());
                                barryData.setValue(response.body());
                            }else {
                                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                                Log.d(TAG, "  -- response status code: " + response.code());
                                Log.d(TAG, "  -- response: " + response.toString());
                            }

                        }
                        @Override
                        public void onFailure(Call<BarryData> call, Throwable t) {
                            Log.d(TAG, "failed API request: " + call.request().url());
                            t.printStackTrace();
                        }
                    }
        );
    }

    public void loadBerryDetail(String queryTerm){
        this.berryDetail.setValue(null);
        Call<BerryDetail> req = this.barryService.fetchBarryDetail(queryTerm);
        Log.d(TAG, " i am before response ");
        req.enqueue(new Callback<BerryDetail>() {
            @Override
            public void onResponse(Call<BerryDetail> call, Response<BerryDetail> response) {
                Log.d(TAG, "onResponse: i am in response");
                if(response.code() == 200){
                    Log.d(TAG, "successful API request: " + call.request().url());
                    //Log.d(TAG, "successful API request: " + response.body().getBarryNames());
                    berryDetail.setValue(response.body());
                }else {
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    Log.d(TAG, "  -- response status code: " + response.code());
                    Log.d(TAG, "  -- response: " + response.toString());
                }

            }
            @Override
            public void onFailure(Call<BerryDetail> call, Throwable t) {
                Log.d(TAG, "failed API request: " + call.request().url());
                t.printStackTrace();
            }
        }
        );
    }

    private boolean shouldExecuteSearch(int unit){
        if (unit != this.currentUnit){
            return true;
        }else{
            return false;// did search if the the query and current query are different
        }
    }

}
