package com.example.android.PokeWiki;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BarryService {
    @GET("berry/")
    Call<BarryData> fetchBarry(
          @Query("limit") int limit_num
//            @Query("units") String units,
//            @Query("appid") String apiKey
    );
    @GET("berry/{name}")
    Call<BerryDetail> fetchBarryDetail(
            @Path("name") String name
    );

    @GET("pokemon/")
    Call<PokemonsData> fetchPokemon(
            @Query("limit") int limit_num
    );
    @GET("pokemon/{name}")
    Call<PokemonsDetail> fetchPokemonDetail(
            @Path("name") String name
    );

    @GET("move/")
    Call<MovesData> fetchMoves(
            @Query("limit") int limit_num
    );
    @GET("move/{name}")
    Call<MovesDetail> fetchMovesDetail(
            @Path("name") String name
    );
}
