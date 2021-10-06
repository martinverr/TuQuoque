package com.tuquoque.game.world.npcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.world.Player;

public class Dog extends NPC{
    private Vector2 coords;

    private enum Status {
        IDLEL, //stand left
        IDLER, //stand right
        WALKL, //walk left
        WALKR //walk right
    }

    //animation stuff
    private boolean direction = true;
    private Animation<TextureAtlas.AtlasRegion> walkRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> walkLeftAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleRightAnimation;
    private Animation<TextureAtlas.AtlasRegion> idleLeftAnimation;

    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world  Box2D World where the NPC will be defined
     * @param coords coordinates of where the NPC will be spawned
     */
    public Dog(World world, Vector2 coords) {
        super(world, 0.8f, 0.15f, coords);
        NPCspeed *= 0.75f;

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
     * return current status of the player considering last direction and if it's moving
     */
    public Dog.Status getStatus(){
        if(!B2DBody.getLinearVelocity().isZero()){ //Walk
            if(B2DBody.getLinearVelocity().x>0){ //right
                direction = true;
                return Dog.Status.WALKR;
            }
            else{ //left
                direction = false;
                return Dog.Status.WALKL;
            }

        }
        else{ //Idle
            if(direction)   return Dog.Status.IDLER;
            else            return Dog.Status.IDLEL;
        }
    }

    /**
     * return the animation for the current state of the dog
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

    @Override
    public void actionTriggered(Player player) {
        follow(player, 1.5f, true);
    }

    @Override
    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getCurrentAnimation().getKeyFrame(elapsedTime, true),
                    B2DBody.getPosition().x - 0.8f,
                    B2DBody.getPosition().y - 0.15f,
                    1.6f, 0.8f);
        }
        else
            Gdx.app.error(this.getClass().getSimpleName(), "batch not drawing");
    }

    void follow(Player player, float stopAtDistance, boolean speedUpIfFar){
        Vector2 pcoords = player.B2DBody.getPosition();
        coords = B2DBody.getPosition();
        float distance = player.B2DBody.getPosition().dst(this.B2DBody.getPosition());

        // check if not too close to player, then set speed to follow him
        if(distance > stopAtDistance){
            if (speedUpIfFar){
                //adjust speed
                if (distance > stopAtDistance*4){ //speed increments if npc is getting far
                    NPCspeed = NOMINAL_SPEED * 2;
                }
                if (distance < stopAtDistance*2){ //speed returns normal
                    NPCspeed = NOMINAL_SPEED * 0.75f;
                }
            }

            //set speed to the body
            if(Math.abs(pcoords.x - coords.x) > 0.05f)
                if(pcoords.x< coords.x)
                    setSpeedX(-NPCspeed);
                else
                    setSpeedX(NPCspeed);
            else setSpeedX(0);

            if(Math.abs(pcoords.y - coords.y) > 0.05f)
                if(pcoords.y < coords.y)
                    setSpeedY(-NPCspeed);
                else
                    setSpeedY(NPCspeed);
            else setSpeedY(0);
        }
        else{ //if too close stop
            setSpeedX(0);
            setSpeedY(0);
        }

        B2DBody.applyLinearImpulse(
                getSpeedX()-B2DBody.getLinearVelocity().x,
                getSpeedY()-B2DBody.getLinearVelocity().y,
                B2DBody.getWorldCenter().x,B2DBody.getWorldCenter().y,true);

    }
}
