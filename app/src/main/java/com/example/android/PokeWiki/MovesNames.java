package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class MovesNames implements Serializable {
    private String Name;
    private String url;

    public MovesNames(){
        this.Name = null;
    }
    public MovesNames(String Name, String url){
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
    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<MovesNames> {
        @Override
        public MovesNames deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject resultObj = json.getAsJsonObject();
            return new MovesNames(
                    resultObj.getAsJsonPrimitive("name").getAsString(),
                    resultObj.getAsJsonPrimitive("url").getAsString()

            );
        }
    }
}
