package com.tuquoque.game.world.npcs.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tuquoque.game.world.npcs.Dog;

public class DogAnimation implements NpcAnimation{
    private Dog dog;
    private boolean direction = true;
    private Animation<TextureAtlas.AtlasRegion> walkRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> walkLeftAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleLeftAnimation;

    private enum Status {
        IDLEL, //stand left
        IDLER, //stand right
        WALKL, //walk left
        WALKR //walk right
    }

    public DogAnimation(Dog dog){
        this.dog = dog;
        animationDef();
    }

    /**
     * Init TextureAtlas frames and related Animations
     */
    private void animationDef(){
        TextureAtlas idleRight =new TextureAtlas(Gdx.files.internal("NPC/doggo/idle_right.atlas"));
        TextureAtlas idleLeft=new TextureAtlas(Gdx.files.internal("NPC/doggo/idle_left.atlas"));
        TextureAtlas walkLeft=new TextureAtlas(Gdx.files.internal("NPC/doggo/move_left.atlas"));
        TextureAtlas walkRight=new TextureAtlas(Gdx.files.internal("NPC/doggo/move_right.atlas"));

        walkLeftAnimation=new Animation<>(1/8f, walkLeft.getRegions());
        walkRightAnimation=new Animation<>(1/8f, walkRight.getRegions());
        idleLeftAnimation=new Animation <>(1/4f, idleLeft.getRegions());
        idleRightAnimation=new Animation <>(1/4f, idleRight.getRegions());
    }

    /**
     * return current status of the dog considering last direction and if it's moving
     */
    private DogAnimation.Status getStatus(){
        if(!dog.B2DBody.getLinearVelocity().isZero()){ //Walk
            if(dog.B2DBody.getLinearVelocity().x>0){ //right
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
     * return the animation for the current state of the dog
     */
    @Override
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