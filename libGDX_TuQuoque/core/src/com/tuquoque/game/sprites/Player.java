package com.tuquoque.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Entity {

    private final World world;

    //animation stuff
    private boolean direction = true;
    private Animation<TextureAtlas.AtlasRegion> walkRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> walkLeftAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleLeftAnimation;

    /**
     * The 4(till now) possible status of the player
     * */
    private enum Status {
        IDLEL, //stand left
        IDLER, //stand right
        WALKL, //walk left
        WALKR //walk right
    }

    /**
     * Init TextureAtlas frames and related Animations
     */
    private void animationDef(){
        TextureAtlas idleRight =new TextureAtlas(Gdx.files.internal("player/idle_right.atlas"));
        TextureAtlas idleLeft=new TextureAtlas(Gdx.files.internal("player/idle_left.atlas"));
        TextureAtlas walkLeft=new TextureAtlas(Gdx.files.internal("player/walk_left.atlas"));
        TextureAtlas walkRight=new TextureAtlas(Gdx.files.internal("player/walk_right.atlas"));

        walkLeftAnimation=new Animation <>(1/8f, walkLeft.getRegions());
        walkRightAnimation=new Animation<>(1/8f, walkRight.getRegions());
        idleLeftAnimation=new Animation <>(1/5f, idleLeft.getRegions());
        idleRightAnimation=new Animation <>(1/5f, idleRight.getRegions());
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
        super(world, coords, 0.4f, 0.65f);
        this.world = world;

        //Animation animations
        animationDef();
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
    public Animation<TextureAtlas.AtlasRegion> getCurrentAnimation(){
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
