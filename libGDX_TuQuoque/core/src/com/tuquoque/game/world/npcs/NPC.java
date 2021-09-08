package com.tuquoque.game.world.npcs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.world.Entity;
import com.tuquoque.game.world.Player;

public abstract class NPC extends Entity {

    private Vector2 coords;
    private float actionRadius = 10;
    float NPCspeed = NOMINAL_SPEED;

    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world  Box2D World where the NPC will be defined
     * @param coords coordinates of where the NPC will be spawned
     */
    public NPC(World world, float hWidth, float hHeight, Vector2 coords) {
        super(world, coords, hWidth, hHeight);

        this.coords=coords;
    }


    /**
     * getter of actionRadius
     *
     * @return the radius in which the npc is aware of the player
     */
    public float getActionRadius() {
        return actionRadius;
    }

    /**
     * setter of actionRadius
     */
    public void setActionRadius(float actionRadius) {
        this.actionRadius = actionRadius;
    }

    /**
     * The NPC_handler will notify the NPC when the player enters in his action radius by this method
     *
     * This NPC just follows the player
     *
     * @param player player whom the npc will interact
     */
    public abstract void actionTriggered(Player player);


    void follow(Player player, float stopAtDistance, boolean speedUpIfFar){
        Vector2 pcoords = player.B2DBody.getPosition();
        coords = B2DBody.getPosition();
        float distance = player.B2DBody.getPosition().dst(this.B2DBody.getPosition());

        // check if not too close to player, then set speed to follow him
        if(distance > stopAtDistance){
            if (speedUpIfFar){
                //adjust speed
                if (distance > stopAtDistance*4){ //speed increments if npc is getting far
                    NPCspeed *= 1.5f;
                }
                if (distance < stopAtDistance*2){ //speed returns normal
                    NPCspeed = NOMINAL_SPEED * 0.5f;
                }
            }

            //set speed to the body
            if(pcoords.x< coords.x)
                setSpeedX(-NPCspeed);
            else
                setSpeedX(NPCspeed);

            if(pcoords.y < coords.y)
                setSpeedY(-NPCspeed);
            else
                setSpeedY(NPCspeed);
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

    public abstract void draw();
}
