package com.example.android.PokeWiki;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MovesData implements Serializable {
    @SerializedName("results")
    private ArrayList<MovesNames> movesNames;


    public MovesData(){
        this.movesNames = null;
    }

    public ArrayList<MovesNames> getMovesNames() {
        return movesNames;
    }
}
