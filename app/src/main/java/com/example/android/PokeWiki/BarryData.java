package com.example.android.PokeWiki;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BarryData implements Serializable {
    @SerializedName("results")
    private ArrayList<BarryNames> berryNames;
//    public String BarryNames;

    //private BerryDetail berryDetail;

    public BarryData(){
        this.berryNames = null;
        //this.berryDetail = null;
    }

    public ArrayList<BarryNames> getBarryNames() {
        return berryNames;
    }
}
