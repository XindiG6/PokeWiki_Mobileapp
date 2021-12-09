package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class PokemonsName implements Serializable {

    private String Name;
    private String url;

    public PokemonsName(){
        this.Name = null;
    }
    public PokemonsName(String Name, String url){
        this.Name = Name;
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return url;
    }


    // Deserializer
    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<PokemonsName> {
        @Override
        public PokemonsName deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject resultObj = json.getAsJsonObject();
            return new PokemonsName(
                    resultObj.getAsJsonPrimitive("name").getAsString(),
                    resultObj.getAsJsonPrimitive("url").getAsString()

            );
        }
    }
}
