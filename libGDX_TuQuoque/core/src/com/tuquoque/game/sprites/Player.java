package com.tuquoque.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {
    private World world;
    public Body B2DBody;

    public final float NOMINAL_SPEED = 1.3f;
    private float speedX;
    private float speedY;

    //animation
    private TextureAtlas idleRight;
    private TextureAtlas idleLeft;
    private TextureAtlas walkRight;
    private TextureAtlas walkLeft;

    private Animation walkRightAnimation;
    private Animation walkLeftAnimation;
    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;

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

    private void animationDef(){
        idleRight =new TextureAtlas(Gdx.files.internal("player/idle_right.atlas"));
        idleLeft=new TextureAtlas(Gdx.files.internal("player/idle_left.atlas"));
        walkLeft=new TextureAtlas(Gdx.files.internal("player/walk_left.atlas"));
        walkRight=new TextureAtlas(Gdx.files.internal("player/walk_right.atlas"));

        walkRightAnimation=new Animation(1/8f, walkRight.getRegions());
        walkLeftAnimation=new Animation(1/8f, walkLeft.getRegions());
        idleLeftAnimation=new Animation(1/5f, idleLeft.getRegions());
        idleRightAnimation=new Animation(1/5f, idleRight.getRegions());
    }

    private void playerDef(Vector2 coords){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(coords.x, coords.y);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.4f,0.65f);
        fixtureDef.shape = playerShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
    }


    public Player(World world, Vector2 coords){
        this.world = world;

        //Box2d B2DBody
        playerDef(coords);
        //Animation animations
        animationDef();
    }

    public Animation getWalkRightAnimation() {
        return walkRightAnimation;
    }

    public Animation getWalkLeftAnimation() {
        return walkLeftAnimation;
    }

    public Animation getIdleRightAnimation() {
        return idleRightAnimation;
    }

    public Animation getIdleLeftAnimation() {
        return idleLeftAnimation;
    }
}
