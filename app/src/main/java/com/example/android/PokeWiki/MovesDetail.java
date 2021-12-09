package com.example.android.PokeWiki;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class MovesDetail implements Serializable {
    private int accuracy;
    private int power;
    private int pp;
    private int priority;
    private String contest_type;
    private String damage_class;
    private String generation;
    private String target;
    private String type;

    public MovesDetail(int accuracy, int power, int pp, int priority, String contest_type, String damage_class, String generation, String target, String type) {
        this.accuracy = accuracy;
        this.power = power;
        this.pp = pp;
        this.priority = priority;
        this.contest_type = contest_type;
        this.damage_class = damage_class;
        this.generation = generation;
        this.target = target;
        this.type = type;

    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getDamage_class() {
        return damage_class;
    }

    public String getContest_type() {
        return contest_type;
    }

    public int getPriority() {
        return priority;
    }

    public String getGeneration() {
        return generation;
    }

    public int getPp() {
        return pp;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public int getPower() {
        return power;
    }

    public static class JsonDeserializer implements com.google.gson.JsonDeserializer<MovesDetail> {
        @Override
        public MovesDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject MovesDetailObj = json.getAsJsonObject();
            JsonObject contest_typeObj = MovesDetailObj.getAsJsonObject("contest_type");
            JsonObject damage_classObj = MovesDetailObj.getAsJsonObject("damage_class");
            JsonObject generationObj = MovesDetailObj.getAsJsonObject("generation");
            JsonObject targetObj = MovesDetailObj.getAsJsonObject("target");
            JsonObject typeObj = MovesDetailObj.getAsJsonObject("type");

            return new MovesDetail(
                    MovesDetailObj.getAsJsonPrimitive("accuracy").getAsInt(),
                    MovesDetailObj.getAsJsonPrimitive("power").getAsInt(),
                    MovesDetailObj.getAsJsonPrimitive("pp").getAsInt(),
                    MovesDetailObj.getAsJsonPrimitive("priority").getAsInt(),
                    contest_typeObj.getAsJsonPrimitive("name").getAsString(),
                    damage_classObj.getAsJsonPrimitive("name").getAsString(),
                    generationObj.getAsJsonPrimitive("name").getAsString(),
                    targetObj.getAsJsonPrimitive("name").getAsString(),
                    typeObj.getAsJsonPrimitive("name").getAsString()



            );
        }
    }
}
