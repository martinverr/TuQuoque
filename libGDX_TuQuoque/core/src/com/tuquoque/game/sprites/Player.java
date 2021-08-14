package com.tuquoque.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {

    //BOX2D stuff
    private World world;
    public Body B2DBody;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    public final float NOMINAL_SPEED = 1.3f;
    private float speedX;
    private float speedY;

    //animation stuff
    private TextureAtlas idleRight;
    private TextureAtlas idleLeft;
    private TextureAtlas walkRight;
    private TextureAtlas walkLeft;
    // True = right, False = left
    private boolean direction = true;
    private Animation walkRightAnimation;
    private Animation walkLeftAnimation;
    private Animation idleRightAnimation;
    private Animation idleLeftAnimation;

    /**
     * The 4(till now) possible status of the player
     * */
    private enum Status {
        IDLEL, //stand left
        IDLER, //stand right
        WALKL, //walk left
        WALKR; //walk right
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
     * Init TextureAtlas frames and related Animations
     * */
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

    /**
     * Init Box2D Body of player (BodyDef and FixtureDef related)
     *
     * @param coords coordinates of bodyDef.position
     * */
    private void playerDef(Vector2 coords){
        bodyDef = new BodyDef();
        bodyDef.position.set(coords.x, coords.y);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        fixtureDef = new FixtureDef();
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.4f,0.65f);
        fixtureDef.shape = playerShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
    }

    /**
    * Constructor of Player
     *
     * Create the Box2D Body of the player and init the animations of the its texture
     *
     * @param world Box2D World where the player will be defined
     * @param coords coordinates of where the player will be spawned
    * */
    public Player(World world, Vector2 coords){
        this.world = world;

        //Box2d B2DBody
        playerDef(coords);
        //Animation animations
        animationDef();
    }

    /**
    * getters of animations
    */
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

    /**
     * return current status of the player considering last direction and if it's moving
     */
    public Status getStatus(){
        if(!B2DBody.getLinearVelocity().isZero()){ //Walk
            if(B2DBody.getLinearVelocity().x>0){ //right
                direction = true;
                return Status.WALKR;
            }
            else{ //left
                direction = false;
                return Status.WALKL;
            }

        }
        else{ //Idle
            if(direction)   return Status.IDLER;
            else            return Status.IDLEL;
        }
    }

    /**
     * return the animation for the current state of the player
     */
    public Animation getCurrentAnimation(){
        switch (getStatus()){
            case IDLEL:
                return idleLeftAnimation;
            default:
            case IDLER:
                return idleRightAnimation;
            case WALKL:
                return walkLeftAnimation;
            case WALKR:
                return walkRightAnimation;
        }
    }
}
