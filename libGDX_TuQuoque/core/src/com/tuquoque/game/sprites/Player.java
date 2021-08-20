package com.tuquoque.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Entity {

    private World world;

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
     * Init TextureAtlas frames and related Animations
     */
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
    * Constructor of Player
     *
     * Create the Box2D Body of the player and init the animations of its texture
     *
     * @param world Box2D World where the player will be defined
     * @param coords coordinates of where the player will be spawned
    * */

    public Player(World world, Vector2 coords){
        super(world,coords);
        this.world = world;

        //Box2d B2DBody
        entityDef(coords);
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
