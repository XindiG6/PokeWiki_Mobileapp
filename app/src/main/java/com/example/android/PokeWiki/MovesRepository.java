package com.example.android.PokeWiki;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovesRepository {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";  // berry api

    private MutableLiveData<MovesData> movesData;
    private MutableLiveData<MovesDetail> movesDetail;

    private BarryService barryService;

    public MovesRepository(){
        this.movesData = new MutableLiveData<>();
        this.movesDetail = new MutableLiveData<>();
        this.movesData.setValue(null);
        this.movesDetail.setValue(null);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MovesNames.class, new MovesNames.JsonDeserializer())
                .registerTypeAdapter(MovesDetail.class, new MovesDetail.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.barryService = retrofit.create(BarryService.class);

    }

    public MutableLiveData<MovesData> getMovesData() {
        return movesData;
    }
    public MutableLiveData<MovesDetail> getMovesDetail() {
        return movesDetail;
    }

    public void loadMoves(int limit) {

        this.movesData.setValue(null);
        Call<MovesData> req = this.barryService.fetchMoves(limit);
        Log.d(TAG, " i am before response ");
        req.enqueue(new Callback<MovesData>() {
                        @Override
                        public void onResponse(Call<MovesData> call, Response<MovesData> response) {
                            Log.d(TAG, "onResponse: i am in response");
                            if(response.code() == 200){
                                Log.d(TAG, "successful API request: " + call.request().url());
                                movesData.setValue(response.body());
                            }else {
                                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                                Log.d(TAG, "  -- response status code: " + response.code());
                                Log.d(TAG, "  -- response: " + response.toString());
                            }

                        }
                        @Override
                        public void onFailure(Call<MovesData> call, Throwable t) {
                            Log.d(TAG, "failed API request: " + call.request().url());
                            t.printStackTrace();
                        }
                    }
        );
    }

    public void loadMovesDetail(String query) {

        this.movesDetail.setValue(null);
        Call<MovesDetail> req = this.barryService.fetchMovesDetail(query);
        Log.d(TAG, " i am before moves detail response ");
        req.enqueue(new Callback<MovesDetail>() {
                        @Override
                        public void onResponse(Call<MovesDetail> call, Response<MovesDetail> response) {
                            Log.d(TAG, "onResponse: i am in response");
                            if(response.code() == 200){
                                Log.d(TAG, "successful API request: " + call.request().url());
                                movesDetail.setValue(response.body());
                            }else {
                                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                                Log.d(TAG, "  -- response status code: " + response.code());
                                Log.d(TAG, "  -- response: " + response.toString());
                            }

                        }
                        @Override
                        public void onFailure(Call<MovesDetail> call, Throwable t) {
                            Log.d(TAG, "failed API request: " + call.request().url());
                            t.printStackTrace();
                        }
                    }
        );
    }

}
