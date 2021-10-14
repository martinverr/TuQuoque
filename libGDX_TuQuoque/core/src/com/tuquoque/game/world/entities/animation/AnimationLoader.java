package com.tuquoque.game.world.entities.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationLoader {

    private static String[] paths = {
            //player
            "player/idle_right.atlas",
            "player/idle_left.atlas",
            "player/walk_left.atlas",
            "player/walk_right.atlas",
            //dog
            "NPC/doggo/idle_right.atlas",
            "NPC/doggo/idle_left.atlas",
            "NPC/doggo/move_left.atlas",
            "NPC/doggo/move_right.atlas"
    };

    public static void loadAllAnimations(AssetManager assetManager) {
        for (String path : paths) {
            assetManager.load(path, TextureAtlas.class);
        }
    }
}
