package com.tuquoque.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.tuquoque.game.ui.Item;

public class Player extends Entity {

    private final World world;
    private final float B2D_BODY_DEF_WIDTH = 0.4f;
    private final float B2D_BODY_DEF_HEIGHT = 0.6f;


    //animation stuff
    private boolean direction = true;
    private Animation<TextureAtlas.AtlasRegion> walkRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> walkLeftAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleLeftAnimation;

    private Array<Item> playerInv;

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
        //offset to legs = - heightEntireBox + heightLegs / 2
        super(world, coords, 0.4f, 0.15f, -0.6f +0.15f/2);
        this.world = world;
        playerInv = new Array<>(15);

        //Animation animations
        animationDef();

        playerBodyDef();
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

    public Array<Item> getPlayerInv() {
        return playerInv;
    }

    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getCurrentAnimation().getKeyFrame(elapsedTime, true),
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
