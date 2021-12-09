package com.example.android.PokeWiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PokemonsData implements Serializable {

    @SerializedName("results")
    private ArrayList<PokemonsName> pokemonsName;

    public PokemonsData(){
        this.pokemonsName = null;
    }

    public ArrayList<PokemonsName> getPokemonsName() {
        return pokemonsName;
    }
}
