package com.example.android.PokeWiki;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonsRepository {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";  // berry api

    private MutableLiveData<PokemonsData> pokemonsData;
    private MutableLiveData<PokemonsDetail> pokemonsDetail;

    private BarryService barryService;

    private int currentUnit;


    public PokemonsRepository(){
        this.pokemonsData = new MutableLiveData<>();
        this.pokemonsDetail = new MutableLiveData<>();
        this.pokemonsData.setValue(null);
        this.pokemonsDetail.setValue(null);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PokemonsName.class, new PokemonsName.JsonDeserializer())
                .registerTypeAdapter(PokemonsDetail.class, new PokemonsDetail.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.barryService = retrofit.create(BarryService.class);
    }


    public MutableLiveData<PokemonsData> getPokemonsData() {
        return pokemonsData;
    }

    public MutableLiveData<PokemonsDetail> getPokemonsDetail() {
        return pokemonsDetail;
    }
    public void loadPokemons(int limit) {

        this.pokemonsData.setValue(null);
        Call<PokemonsData> req = this.barryService.fetchPokemon(limit);
        Log.d(TAG, " i am before response ");
        req.enqueue(new Callback<PokemonsData>() {
                        @Override
                        public void onResponse(Call<PokemonsData> call, Response<PokemonsData> response) {
                            Log.d(TAG, "onResponse: i am in response");
                            if(response.code() == 200){
                                Log.d(TAG, "successful API request: " + call.request().url());
                                pokemonsData.setValue(response.body());
                            }else {
                                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                                Log.d(TAG, "  -- response status code: " + response.code());
                                Log.d(TAG, "  -- response: " + response.toString());
                            }

                        }
                        @Override
                        public void onFailure(Call<PokemonsData> call, Throwable t) {
                            Log.d(TAG, "failed API request: " + call.request().url());
                            t.printStackTrace();
                        }
                    }
        );
    }

    public void loadPokemonsDetail(String query){
        this.pokemonsDetail.setValue(null);
        Call<PokemonsDetail> req = this.barryService.fetchPokemonDetail(query);
        Log.d(TAG, " i am before response ");
        req.enqueue(new Callback<PokemonsDetail>() {
                        @Override
                        public void onResponse(Call<PokemonsDetail> call, Response<PokemonsDetail> response) {
                            Log.d(TAG, "onResponse: i am in response");
                            if(response.code() == 200){
                                Log.d(TAG, "successful API request: " + call.request().url());
                                pokemonsDetail.setValue(response.body());
                            }else {
                                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                                Log.d(TAG, "  -- response status code: " + response.code());
                                Log.d(TAG, "  -- response: " + response.toString());
                            }

                        }
                        @Override
                        public void onFailure(Call<PokemonsDetail> call, Throwable t) {
                            Log.d(TAG, "failed API request: " + call.request().url());
                            t.printStackTrace();
                        }
                    }
        );
    }
}
