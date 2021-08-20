package com.tuquoque.game.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.tuquoque.game.sprites.NPC;
import com.tuquoque.game.sprites.Player;

public class NPC_handler {

    private final NPC[] npcs;
    private final Circle[] circles;
    private final Player player;

    public NPC_handler(NPC[] npcs, Player player) {
        this.npcs=npcs;
        this.player=player;

        circles=new Circle[npcs.length];
        for(int i=0;i< circles.length;i++)
            circles[i]=new Circle();
    }

    public void update(){
        for(int i=0;i<npcs.length;i++){
            circles[i].setRadius(10);
            circles[i].setPosition(npcs[i].B2DBody.getPosition());

            // npc follows player

            follow(npcs[i], player.B2DBody.getPosition(), circles[i]);
        }
    }
    
    public void follow(NPC npc, Vector2 coords, Circle circle){

        // Checks if circle contains the given coords and applies a linear impulse to reach these coords

        if(circle.contains(coords)) {
            if(coords.x<npc.B2DBody.getPosition().x){
                npc.setSpeedX(-npc.NOMINAL_SPEED/2);
            }
            else
                npc.setSpeedX(npc.NOMINAL_SPEED/2);

            if(coords.y<npc.B2DBody.getPosition().y){
                npc.setSpeedY(-npc.NOMINAL_SPEED/2);
            }
            else
                npc.setSpeedY(npc.NOMINAL_SPEED/2);
        }
        else{
            npc.setSpeedX(0);
            npc.setSpeedY(0);
        }

        npc.B2DBody.applyLinearImpulse(npc.getSpeedX()-npc.B2DBody.getLinearVelocity().x,
                npc.getSpeedY()-npc.B2DBody.getLinearVelocity().y,
                npc.B2DBody.getWorldCenter().x,npc.B2DBody.getWorldCenter().y,true);
    }
}
