package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class InventorySlot extends Stack {
    private Item item = null;
    private final Skin skin;

    public InventorySlot(String typeOfSlot, Skin skin){
        this.skin = skin;
        add(new Image(skin.getDrawable(typeOfSlot)));
    }

    public void addItem(Item item){
        if(item != null){
            clearItem();
            this.add(new Image(skin.getDrawable("item_" + item.getID())));
        }
        this.item = item;
    }

    public void clearItem(){
        if(item != null){
            this.getChild(1).clear();
            item = null;
        }
    }

    public Item getItem() {
        return item;
    }

    public boolean containsItems(){
        return item != null;
    }
}
