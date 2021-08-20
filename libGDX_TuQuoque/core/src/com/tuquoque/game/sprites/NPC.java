package com.tuquoque.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.GameStarter;

/*
* TODO: circle in NPC non viene usato, ma è giusto che sia qui e non in NPChandler, ogni NPC ne può avere uno
*  diverso. Per questo serve anche un getter del Circle (o sostituirlo con un float raggio e lasciare all'handler il compito
*  di creare il Circle).
*
* TODO: aggiungere un metodo chiamato dal NPChandler quando il player è nel suo raggio d'azione, il nome
*  può essere qualcosa tipo eventTrigger-playerInRange etc...
*/
public class NPC extends Entity {

    public Vector2 coords;
    private Circle circle;

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

}
