package com.tuquoque.game.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.tuquoque.game.sprites.NPC;
import com.tuquoque.game.sprites.Player;

/*
* TODO: non mi piace che si passi al costruttore l'array di npc, creerei un array vuoto inizialmente e aggiungerei il
*  metodo "void add(NPC npc)". In futuro ci sarà un loop dal caller per creare tutti gli npc magari memorizzati in un enum
*
* TODO: L'uso di Circle secondo me è sostituibile con un raggio, in ogni caso il raggio, o il cerchio che sia, vanno
*  presi dal NPC stesso con dei getter, così come hai fatto con getPosition().
*
* TODO: in update() invece di follow() chiamiamo un npc.eventTrigger(Player) e sarà il npc specifico a fare qualcosa quando
*  il playere entra nel raggio d'azione, per esempio followare il player. follow() va spostato di conseguenza in una
*  delle classi NPC.
*
* TODO: il follow mi piace un sacco, metterei una condizione in più, se all'interno di un raggio piccolo non deve muoversi
*  così si ferma vicino al player. il raggio sarà poco più del raggio hitbox del Player. Questo per evitare che un alleato
*  faccia ostruzione e sposti il player, ma viceversa. Se è un nemico invece attaccherà o va bene che lo ostruisca apposta.
*  intanto sarà tutto all'interno del codice dell'npc stesso
*  */
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
