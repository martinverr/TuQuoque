package com.tuquoque.game.ui;

public class Item {
    private final String name;
    private final int ID;
    private int quantity;

    public Item(String name, int ID){
        this.name = name;
        this.ID = ID;
        this.quantity = 1;
    }

    public Item(String name, int ID, int quantity){
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
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
