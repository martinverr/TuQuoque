package com.tuquoque.game.world.entities.npc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.world.entities.Player;
import com.tuquoque.game.world.entities.animation.DogAnimation;

public class Dog extends NPC{
    private Vector2 coords;

    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world  Box2D World where the NPC will be defined
     * @param coords coordinates of where the NPC will be spawned
     */
    public Dog(World world, Vector2 coords) {
        super(world, 0.8f, 0.4f, coords);
        NPCspeed *= 0.5f;

        setNpcAnimation(new DogAnimation(this));
    }


    @Override
    public void actionTriggered(Player player) {
        follow(player, 2f, true);
    }

    @Override
    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            batch.draw(getNpcAnimation().getCurrentAnimation().getKeyFrame(elapsedTime, true),
                    B2DBody.getPosition().x - 0.8f,
                    B2DBody.getPosition().y - 0.4f,
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
                if (distance < stopAtDistance * 1.5f){ //speed returns normal
                    NPCspeed = NOMINAL_SPEED * 0.5f;
                }
            }

            //set speed to the body
            if(Math.abs(pcoords.x - coords.x) > 0.05f)
                if(pcoords.x< coords.x)
                    setSpeedX(-NPCspeed);
                else
                    setSpeedX(NPCspeed);
            else setSpeedX(0);

            if(Math.abs(pcoords.y -0.3f - coords.y) > 0.05f)
                if(pcoords.y - 0.3f < coords.y)
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
