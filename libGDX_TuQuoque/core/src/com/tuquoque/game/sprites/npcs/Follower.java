package com.tuquoque.game.sprites.npcs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.sprites.Player;

public class Follower extends NPC{
    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world  Box2D World where the NPC will be defined
     * @param coords coordinates of where the NPC will be spawned
     */
    public Follower(World world, Vector2 coords) {
        super(world, coords);
        NPCspeed *= 0.5f;
    }

    @Override
    public void actionTriggered(Player player) {
        follow(player, 1.5f, true);
    }

    @Override
    public void draw() {

    }
}
