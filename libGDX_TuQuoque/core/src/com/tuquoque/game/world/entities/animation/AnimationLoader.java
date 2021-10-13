package com.tuquoque.game.world.entities.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationLoader {
    public static void loadAllAnimations(AssetManager assetManager){
        //player
        assetManager.load("player/idle_right.atlas", TextureAtlas.class);
        assetManager.load("player/idle_left.atlas", TextureAtlas.class);
        assetManager.load("player/walk_left.atlas", TextureAtlas.class);
        assetManager.load("player/walk_right.atlas", TextureAtlas.class);

        //dog
        assetManager.load("NPC/doggo/idle_right.atlas", TextureAtlas.class);
        assetManager.load("NPC/doggo/idle_left.atlas", TextureAtlas.class);
        assetManager.load("NPC/doggo/move_left.atlas", TextureAtlas.class);
        assetManager.load("NPC/doggo/move_right.atlas", TextureAtlas.class);
    }
}
