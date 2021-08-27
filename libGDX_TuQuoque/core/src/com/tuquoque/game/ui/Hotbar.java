package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;

class Hotbar extends Table {
    private final int NUMBER_OF_SLOTS = 8;
    private int currentPointedSlot = 0;
    ArrayList<Stack> slots;
    ArrayList<Cell<Image>> cursors;


    public Hotbar(Skin skin){
        super(skin);
        slots = new ArrayList<>(NUMBER_OF_SLOTS);
        cursors = new ArrayList<>(NUMBER_OF_SLOTS);
        setScale(1.5f);
        setDebug(false);


        add(new Image(skin.getDrawable("hotbarBegin"))).expandX().bottom().right();
        for(int i=0; i<NUMBER_OF_SLOTS; i++){
            Stack currentStack = new Stack();
            currentStack.add(new Image(skin.getDrawable("hotbarMid")));
            add(currentStack);
            slots.add(currentStack);
        }
        add(new Image(skin.getDrawable("hotbarEnd"))).bottom().expandX().left();

        row();
        add();
        for(int i=0; i<NUMBER_OF_SLOTS; i++){
            cursors.add(this.add(new Image(skin, "hotbarCursor")));
            cursors.get(i).getActor().setVisible(false);
        }

        cursors.get(currentPointedSlot).getActor().setVisible(true);
    }


    void changeSlot(int slotIndex){
        cursors.get(currentPointedSlot).getActor().setVisible(false);
        currentPointedSlot = slotIndex;
        cursors.get(currentPointedSlot).getActor().setVisible(true);
    }

    public int getCurrentPointedSlot() {
        return currentPointedSlot;
    }

    public int getNumberOfSlots() {
        return NUMBER_OF_SLOTS;
    }
}
