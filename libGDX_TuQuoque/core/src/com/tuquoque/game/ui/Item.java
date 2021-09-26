package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Item {
    private final String name;
    private final int ID;
    private int quantity;
    private String type;

    public Item(String name, int ID){
        this.name = name;
        this.ID = ID;
        this.quantity = 1;
        this.type = null;
    }

    public Item(String name, int ID, int quantity){
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
    }

    public Item(String name, int ID, int quantity, String type){
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
        this.type = type;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(){
        quantity++;
    }

    public Image getItemImage(Skin skin) {
        return new Image(skin.getDrawable("item_" + ID));
    }

    public String getItemType(){
        return type;
    }

    @Override
    public String toString(){
        return "item " + ID + " (" + name + "): " + quantity;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Item){
            return ((Item) obj).ID == this.ID;
        }
        return false;
    }
}
