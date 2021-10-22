package com.tuquoque.game.world.npcs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.world.entities.Player;
import com.tuquoque.game.world.entities.npc.NPC;

public class Walker extends NPC {
    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world   Box2D World where the NPC will be defined
     * @param hWidth
     * @param hHeight
     * @param coords  coordinates of where the NPC will be spawned
     */
    public Walker(World world, float hWidth, float hHeight, Vector2 coords) {
        super(world, hWidth, hHeight, coords);
    }

    @Override
    public void actionTriggered(Player player) {
        
    }

    @Override
    public void draw(Batch batch, float elapsedTime) {

    }

}
