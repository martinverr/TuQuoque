package com.tuquoque.game.world.npcs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tuquoque.game.world.npcs.NPC;
import com.tuquoque.game.world.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class NPC_handler {

    private final ArrayList<NPC> npcs;
    private final Player player;

    /**
     * Constructor, set the player and an empty npcs list
     *
     * @param player The player the NPCs will interact with
     */
    public NPC_handler(Player player) {
        this.npcs= new ArrayList<NPC>();
        this.player=player;
    }

    /**
     * Constructor v2, set the player and a beginning list of npcs
     *
     * @param npcs array of NPCs that will be added immediately
     * @param player The player the NPCs will interact with
     */
    public NPC_handler(NPC[] npcs, Player player) {
        this.player=player;

        this.npcs= new ArrayList<NPC>();
        this.npcs.addAll(Arrays.asList(npcs));
    }

    /**
     * add a NPC to be managed by this NPC_handler
     *
     * @param npc NPC to be added
     */
    public void addNPC(NPC npc){
        npcs.add(npc);
    }

    /**
     * remove a NPC from this NPC_handler
     *
     * @param npc NPC to be removed
     */
    public void remove(NPC npc){
        npcs.remove(npc);
    }

    /**
     * For each npc check if they are in their action radius to the player, if so trigger their action
     */
    public void update(){
        for(NPC npc : npcs){
            if (npc.B2DBody.getPosition().dst(player.B2DBody.getPosition()) < npc.getActionRadius())
                npc.actionTriggered(player);
        }
    }

    public void draw(Batch batch, float elapsedTime) {
        if (batch.isDrawing()) {
            for(NPC npc : npcs){
                if (npc.B2DBody.getPosition().dst(player.B2DBody.getPosition()) < 10)
                    npc.draw(batch, elapsedTime);
            }
        }
        else
            Gdx.app.error(this.getClass().getSimpleName(), "batch not drawing");
    }

    public int getNPCsCount(){
        return npcs.size();
    }
}
