package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.GameStarter;

public class NPC extends Entity {

    public Vector2 coords;
    private Circle circle;

    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the player and init the animations of its texture
     *
     * @param world  Box2D World where the player will be defined
     * @param coords coordinates of where the player will be spawned
     */
    public NPC(World world, Vector2 coords) {
        super(world, coords);

        this.coords=coords;
        entityDef(coords);
    }

}
