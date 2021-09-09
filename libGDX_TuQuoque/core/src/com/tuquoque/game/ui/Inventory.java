package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Inventory extends Table {
    private Stack title;
    private Table inventorySlots;
    private Table inventoryBottomSlots;

    public Inventory(Skin skin){
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
        inventorySlots= new Table(skin);
        for(int r=0; r<3; r++){
            for(int c=0; c<5; c++){
                String name = "invSlot";
                if(r == 0)
                    name+="-top";
                else if(r == 2)
                    name+="-bottom";
                else
                    name+="-mid";

                if(c == 0)
                    name+="-left";
                else if(c == 4)
                    name+="-right";
                else
                    name+="-mid";

                inventorySlots.add(new Image(skin.getDrawable(name)));
            }
            inventorySlots.row();
        }

        // Accessories slots setup
        inventoryBottomSlots=new Table(skin);
        inventoryBottomSlots.add(new Image(skin.getDrawable("invSlot-helmet")));
        inventoryBottomSlots.add(new Image(skin.getDrawable("invSlot-chest")));
        inventoryBottomSlots.add(new Image(skin.getDrawable("invSlot-boots")));
        inventoryBottomSlots.add(new Image(skin.getDrawable("invSlot-weapon")));

        // Complete inventory setup
        add(title);
        row();
        add(inventorySlots);
        row();
        add(inventoryBottomSlots);
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
}
