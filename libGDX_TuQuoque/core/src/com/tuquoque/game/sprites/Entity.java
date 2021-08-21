package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Entity extends Sprite {

    //BOX2D stuff
    World world;
    public Body B2DBody;
    BodyDef bodyDef;
    FixtureDef fixtureDef;

    public final float NOMINAL_SPEED = 1.3f;
    private float speedX;
    private float speedY;

    public Entity(World world, Vector2 coords){
        this.world=world;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    /**
     * Getters and setters of SpeedX and SpeedY
     * */
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

}
