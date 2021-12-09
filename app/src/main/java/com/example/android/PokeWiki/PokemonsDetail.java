package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class PokemonsDetail implements Serializable {
    private int base_experience;
    private int height;
    private boolean is_default;
    private int order;
    private int weight;
    private String species_name;

    public PokemonsDetail(int base_experience, int height, boolean is_default, int order, int weight, String species_name) {
        this.base_experience = base_experience;
        this.height = height;
        this.is_default = is_default;
        this.order = order;
        this.weight = weight;
        this.species_name = species_name;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public int getHeight() {
        return height;
    }

    public boolean getIs_default() {
        return is_default;
    }

    public int getOrder() {
        return order;
    }

    public int getWeight() {
        return weight;
    }

    public String getSpecies_name() {
        return species_name;
    }
    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<PokemonsDetail> {
        @Override
        public PokemonsDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject PokemonDetailObj = json.getAsJsonObject();
            JsonObject PokemonsSpecies = PokemonDetailObj.getAsJsonObject("species");

            return new PokemonsDetail(
                    PokemonDetailObj.getAsJsonPrimitive("base_experience").getAsInt(),
                    PokemonDetailObj.getAsJsonPrimitive("height").getAsInt(),
                    PokemonDetailObj.getAsJsonPrimitive("is_default").getAsBoolean(),
                    PokemonDetailObj.getAsJsonPrimitive("order").getAsInt(),
                    PokemonDetailObj.getAsJsonPrimitive("weight").getAsInt(),
                    PokemonsSpecies.getAsJsonPrimitive("name").getAsString()
            );
        }
    }
}
