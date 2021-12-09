package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;

public class BerryDetail implements Serializable {
    private String firmness_name;
    private int growth_time;
    private int natural_gift_power;
    private String natural_gift_type_name;
    private int max_harvest;
    private int size;
    private int smoothness;
    private int soil_dryness;

    public BerryDetail(String firmness_name, int growth_time, int natural_gift_power, String natural_gift_type_name, int max_harvest, int size,  int smoothness, int soil_dryness) {
        this.firmness_name = firmness_name;
        this.growth_time = growth_time;
        this.natural_gift_power = natural_gift_power;
        this.natural_gift_type_name = natural_gift_type_name;
        this.max_harvest = max_harvest;
        this.size = size;
        this.smoothness = smoothness;
        this.soil_dryness = soil_dryness;
    }

    public String getFirmness_name() {
        return firmness_name;
    }

    public int getGrowth_time() {
        return growth_time;
    }

    public int getNatural_gift_power() {
        return natural_gift_power;
    }

    public String getNatural_gift_type_name() {
        return natural_gift_type_name;
    }

    public int getMax_harvest() {
        return max_harvest;
    }

    public int getSize() {
        return size;
    }

    public int getSmoothness() {
        return smoothness;
    }

    public int getSoil_dryness() {
        return soil_dryness;
    }
    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<BerryDetail> {
        @Override
        public BerryDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject BerryDetailObj = json.getAsJsonObject();
            JsonObject firmnessObj = BerryDetailObj.getAsJsonObject("firmness");
            JsonObject giftType = BerryDetailObj.getAsJsonObject("natural_gift_type");

            return new BerryDetail(
                    firmnessObj.getAsJsonPrimitive("name").getAsString(),
                    BerryDetailObj.getAsJsonPrimitive("growth_time").getAsInt(),
                    BerryDetailObj.getAsJsonPrimitive("natural_gift_power").getAsInt(),
                    giftType.getAsJsonPrimitive("name").getAsString(),
                    BerryDetailObj.getAsJsonPrimitive("max_harvest").getAsInt(),
                    BerryDetailObj.getAsJsonPrimitive("size").getAsInt(),
                    BerryDetailObj.getAsJsonPrimitive("smoothness").getAsInt(),
                    BerryDetailObj.getAsJsonPrimitive("soil_dryness").getAsInt()

            );
        }
    }
}
