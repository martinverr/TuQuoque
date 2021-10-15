package com.tuquoque.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.tuquoque.game.map.MapType;
import com.tuquoque.game.ui.inventory.Inventory;
import com.tuquoque.game.world.entities.Player;

import java.io.StringWriter;

public class JsonProfile {
    public static void saveProfile(String nameProfile, Player player, Inventory inventory, MapType map){
        Json json = new Json(JsonWriter.OutputType.json);
        StringWriter jsonText = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(jsonText);
        json.setWriter(jsonWriter);


        json.writeObjectStart();
        json.writeValue("coordX", player.B2DBody.getPosition().x);
        json.writeValue("coordY", player.B2DBody.getPosition().y);
        json.writeValue("map", map.toString());
        {
            json.writeObjectStart("stats");
            json.writeValue("health", player.getHealth());
            json.writeValue("maxHealth", player.getMaxHealth());
            json.writeValue("mana", player.getMana());
            json.writeValue("maxMana", player.getMaxMana());
            json.writeValue("exp", player.getExp());
            json.writeValue("maxExp", player.getMaxExp());
            json.writeValue("level", player.getLevel());
            json.writeObjectEnd();
        }
        {
            json.writeObjectStart("inv");
            json.writeValue("gold", 0);
            json.writeValue("items", inventory.getItemsArray(), Array.class);
            json.writeObjectEnd();
        }
        json.writeObjectEnd();

        FileHandle file = Gdx.files.local("data/" + nameProfile + ".json");
        file.writeString(json.prettyPrint(jsonText.toString()), false);
    }
}
