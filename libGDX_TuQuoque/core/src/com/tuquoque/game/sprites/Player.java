package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {
    private World world;
    public Body B2DBody;
    private float speedX;
    private float speedY;

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

    public Player(World world){
        this.world = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(8,4.5f);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fixtureDef = new FixtureDef();
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape = cShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
    }
}
