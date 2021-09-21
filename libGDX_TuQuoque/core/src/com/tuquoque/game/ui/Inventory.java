package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.tuquoque.game.world.Player;

public class Inventory extends Table {
    private Stack title;
    private Table inventorySlotsTable;
    private Table inventoryBottomSlotsTable;
    private Array<InventorySlot> inventory;
    private Player player;

    private final int ROWS = 3;
    private final int COLUMNS = 5;


    public Inventory(Skin skin, Player player){
        super(skin);
        setVisible(false);

        // Title Setup
        title = new Stack();
        title.add(new Image(skin.getDrawable("inventoryTop")));
        Label titleLabel = new Label("INVENTARIO", skin, "small-white");
        titleLabel.setAlignment(1);
        titleLabel.setColor(1,1,1,1);
        titleLabel.setFontScale(1.5f);
        title.add(titleLabel);

        // Main inventory slots setup
        inventorySlotsTable = new Table(skin);
        inventory = new Array<>(COLUMNS*ROWS);
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLUMNS; c++){
                String name = "invSlot";
                if(r == 0)
                    name+="-top";
                else if(r == ROWS-1)
                    name+="-bottom";
                else
                    name+="-mid";

                if(c == 0)
                    name+="-left";
                else if(c == COLUMNS-1)
                    name+="-right";
                else
                    name+="-mid";

                InventorySlot inventorySlot = new InventorySlot(name, skin);
                inventorySlotsTable.add(inventorySlot);
                inventory.add(inventorySlot);
            }
            inventorySlotsTable.row();
        }

        // Accessories slots setup
        inventoryBottomSlotsTable = new Table(skin);
        inventoryBottomSlotsTable.add(new Image(skin.getDrawable("invSlot-helmet")));
        inventoryBottomSlotsTable.add(new Image(skin.getDrawable("invSlot-chest")));
        inventoryBottomSlotsTable.add(new Image(skin.getDrawable("invSlot-boots")));
        inventoryBottomSlotsTable.add(new Image(skin.getDrawable("invSlot-weapon")));

        // Complete inventory setup
        add(title);
        row();
        add(inventorySlotsTable);
        row();
        add(inventoryBottomSlotsTable);

        addItemToInventory(new Item("helmet", 200, 1));
    }

    public void open(){
        setVisible(true);
    }

    public void close(){
        setVisible(false);
    }

    public boolean isOpened(){
        return isVisible();
    }

    public void printInventory(){
        int index = 0;
        System.out.println("Inventory\n{");
        for(InventorySlot slot : inventory){
            System.out.print("\t" + index + ": ");
            if(slot.containsItems())
                System.out.println("["+slot.getItem()+"]; ");
            else
                System.out.println("[];");

            index++;
        }
        System.out.println("}\n");
    }

    private int indexOf(Item item){
        int index = 0;

        for(InventorySlot slot : inventory){
            if(slot.containsItems() && slot.getItem().equals(item))
                return index;
            index++;
        }
        return -1;
    }

    public int addItemToInventory(Item item){
        if(indexOf(item) != -1){
            InventorySlot slot = inventory.get(indexOf(item));
            slot.addItem(item);
            return slot.getItem().getQuantity();
        }

        for(InventorySlot slot : inventory){
            if(!slot.containsItems()){
                slot.addItem(item);
                return item.getQuantity();
            }
        }
        return 0;
    }
}
