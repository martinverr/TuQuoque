package com.tuquoque.game.world.npcs.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public interface NpcAnimation {
    public abstract Animation<TextureAtlas.AtlasRegion> getCurrentAnimation();
}
