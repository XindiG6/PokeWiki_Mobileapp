package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class BarryNames implements Serializable {
    private String Name;
    private String url;

    public BarryNames(){
        this.Name = null;
    }
    public BarryNames(String Name, String url){
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
    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<BarryNames> {
        @Override
        public BarryNames deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject resultObj = json.getAsJsonObject();
            return new BarryNames(
                resultObj.getAsJsonPrimitive("name").getAsString(),
                    resultObj.getAsJsonPrimitive("url").getAsString()

           );
        }
    }
}
