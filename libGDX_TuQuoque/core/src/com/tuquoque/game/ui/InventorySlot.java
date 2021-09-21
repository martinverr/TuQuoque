package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;

public class InventorySlot extends Stack {
    private Item item = null;
    private final Skin skin;

    public InventorySlot(String typeOfSlot, Skin skin){
        this.skin = skin;
        add(new Image(skin.getDrawable(typeOfSlot)));
    }

    public void addItem(Item item){
        if(item != null){
            if(item.equals(this.item))
                item.setQuantity(item.getQuantity() + this.item.getQuantity());

            Label quantityLabel = new Label(Integer.valueOf(item.getQuantity()).toString(), skin, "small-white");
            quantityLabel.setAlignment(Align.bottomRight);

            removeItem();
            this.add(new Image(skin.getDrawable("item_" + item.getID())));
            this.add(quantityLabel);
        }
        this.item = item;
    }

    public void removeItem(){
        if(item != null){
            this.getChild(2).remove();
            this.getChild(1).remove();
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
