package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Entity {

    //BOX2D stuff
    World world;
    public Body B2DBody;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    private float bodyHalfWidth;
    private float bodyHalfHeight;

    //speed
    public final float NOMINAL_SPEED = 1.3f;
    private float speedX;
    private float speedY;

    public Entity(World world, Vector2 coords, float hWidth, float hHeight){
        this.world=world;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
        this.bodyHalfWidth = hWidth;
        this.bodyHalfHeight = hHeight;

        //Box2d B2DBody
        entityDef(coords, hWidth, hHeight);
    }


    /**
     * Init Box2D Body of entity (BodyDef and FixtureDef related)
     *
     * @param coords coordinates of bodyDef.position
     * */
    public void entityDef(Vector2 coords, float hWidth, float hHeight) {

        bodyDef.position.set(coords.x, coords.y);
        bodyDef.gravityScale=0;
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(hWidth,hHeight);
        fixtureDef.shape = playerShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
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

    public void teleportTo(Vector2 coordinates){
        world.destroyBody(B2DBody);
        entityDef(coordinates, bodyHalfWidth, bodyHalfHeight);
    }
}
