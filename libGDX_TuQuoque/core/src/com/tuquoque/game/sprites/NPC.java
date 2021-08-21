package com.tuquoque.game.sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class NPC extends Entity {

    private Vector2 coords;
    private float actionRadius = 10;

    /**
     * Constructor of NPC
     * <p>
     * Create the Box2D Body of the npc and init the animations of its texture
     *
     * @param world  Box2D World where the NPC will be defined
     * @param coords coordinates of where the NPC will be spawned
     */
    public NPC(World world, Vector2 coords) {
        super(world, coords);

        this.coords=coords;
        entityDef(coords);
    }

    /**
     * Init Box2D Body of entity (BodyDef and FixtureDef related)
     *
     * @param coords coordinates of bodyDef.position
     * */
    public void entityDef(Vector2 coords) {
        bodyDef.position.set(coords.x, coords.y);
        bodyDef.gravityScale=0;
        bodyDef.type= BodyDef.BodyType.DynamicBody;

        PolygonShape NPCShape = new PolygonShape();
        NPCShape.setAsBox(0.4f,0.65f);
        fixtureDef.shape = NPCShape;

        B2DBody = world.createBody(bodyDef);
        B2DBody.createFixture(fixtureDef);
    }

    /**
     * getter of actionRadius
     *
     * @return the radius in which the npc is aware of the player
     */
    public float getActionRadius() {
        return actionRadius;
    }

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
    public void actionTriggered(Player player){
        follow(player);
    }


    private void follow(Player player){
        Vector2 pcoords = player.B2DBody.getPosition();
        coords = B2DBody.getPosition();

        // check if not too close to player, then set speed to follow him
        if(player.B2DBody.getPosition().dst(this.B2DBody.getPosition()) > 1.5f){
            if(pcoords.x< coords.x)
                setSpeedX(-NOMINAL_SPEED/2);
            else
                setSpeedX(NOMINAL_SPEED/2);

            if(pcoords.y < coords.y)
                setSpeedY(-NOMINAL_SPEED/2);
            else
                setSpeedY(NOMINAL_SPEED/2);
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
