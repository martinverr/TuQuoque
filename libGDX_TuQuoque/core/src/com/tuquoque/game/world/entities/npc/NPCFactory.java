package com.tuquoque.game.world.entities.npc;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class NPCFactory {
    public NPC createNPC(String name, float x, float y, World world, AssetManager assetManager){
        NPC npc = null;
        if(name.equals("Cotoletta")){
            npc = new Dog(world, new Vector2(x, y), assetManager);
        } else if (name.equals("Minatore")){
            npc = new Miner(world, new Vector2(x, y), assetManager);
        } else if (name.equals("Volpe")){
            npc = new Fox(world, new Vector2(x, y), assetManager);
        } else if (name.equals("Merlo")){
            npc = new Merlo(world, new Vector2(x, y), assetManager);
        }  else if (name.equals("Bardo")){
            npc = new Bardo(world, new Vector2(x, y), assetManager);
        } else if (name.equals("Furio")){
            npc = new Furio(world, new Vector2(x, y), assetManager);
        }
        else return null;
        return npc;
    }
}
