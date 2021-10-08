package com.tuquoque.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tuquoque.game.ui.Item;
import com.tuquoque.game.world.npcs.animation.PlayerAnimation;


public class Player extends Entity {

    private final World world;
    private final float B2D_BODY_DEF_WIDTH = 0.4f;
    private final float B2D_BODY_DEF_HEIGHT = 0.6f;

    private Array<Item> playerInv;


    /**
    * Constructor of Player
     *
     * Create the Box2D Body of the player and init the animations of its texture
     *
     * @param world Box2D World where the player will be defined
     * @param coords coordinates of where the player will be spawned
    * */
    public Player(World world, Vector2 coords){
        //offset to legs = - heightEntireBox + heightLegs / 2
        super(world, coords, 0.4f, 0.15f, -0.6f +0.15f/2);
        this.world = world;
        playerBodyDef();

        playerInv = new Array<>(15);
        setNpcAnimation(new PlayerAnimation(this));
    }

    void playerBodyDef(){
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(B2D_BODY_DEF_WIDTH, B2D_BODY_DEF_HEIGHT);
        fixtureDef.shape = sensorShape;
        fixtureDef.isSensor = true;
        B2DBody.createFixture(fixtureDef).setUserData("player_sensor");

        fixtureDef.isSensor = false;
        sensorShape.dispose();
    }


    public Array<Item> getPlayerInv() {
        return playerInv;
    }


    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getNpcAnimation().getCurrentAnimation().getKeyFrame(elapsedTime, true),
                    B2DBody.getPosition().x - 0.65f,
                    B2DBody.getPosition().y - 0.7f,
                    1.3f, 1.6f);
        }
        else
            Gdx.app.error(this.getClass().getSimpleName(), "batch not drawing");
    }

    @Override
    public void teleportTo(Vector2 coordinates){
        world.destroyBody(B2DBody);
        //offset to legs = - heightEntireBox + heightLegs / 2
        entityDef(coordinates, B2D_BODY_DEF_WIDTH, 0.15f, -B2D_BODY_DEF_HEIGHT +0.15f/2);
        playerBodyDef();
    }
}
