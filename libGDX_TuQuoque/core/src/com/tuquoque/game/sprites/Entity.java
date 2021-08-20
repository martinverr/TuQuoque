package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/*
* TODO: entityDef è specifico di player o NPC o NPC1 etc (possono avere caratteristiche fisiche diverse),
*  farei di Entity una classe abstract e le classi figlie devono implementarne i metodi entityDef() e animationDef().
*/
public class Entity extends Sprite {

    //BOX2D stuff
    private World world;
    public Body B2DBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    public final float NOMINAL_SPEED = 1.3f;
    private float speedX;
    private float speedY;

    public Entity(World world, Vector2 coords){
        this.world=world;
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

    /**
     * Init Box2D Body of entity (BodyDef and FixtureDef related)
     *
     * @param coords coordinates of bodyDef.position
     * */

    public void entityDef(Vector2 coords){
        bodyDef=new BodyDef();
        bodyDef.position.set(coords.x, coords.y);
        bodyDef.gravityScale=0;
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        fixtureDef = new FixtureDef();
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.4f,0.65f);
        fixtureDef.shape = playerShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
    }
}
