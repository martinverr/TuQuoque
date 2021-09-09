package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.screens.ScreenType;


public class GameUI extends Table {
    final private PlayerStatus playerStatus;
    final private Hotbar hotbar;

    public GameUI(final GameStarter context, final Skin skin) {
        super(skin);

        //table properties
        setFillParent(true);
        pad(20);
        setDebug(true);

        //widgets 1st row
        Button menu = new Button(skin.getDrawable("menuIconInactive"), skin.getDrawable("menuIconActive"));
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                context.setScreen(ScreenType.MAINMENU);
            }
        });
        Button inventoryButt = new Button(skin.getDrawable("inventoryIconInactive"), skin.getDrawable("inventoryIconActive"));

        //widget 2nd row
        final Inventory inventory = new Inventory(skin);
        inventoryButt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(inventory.isOpened())
                    inventory.close();
                else
                    inventory.open();
            }
        });

        //widgets 3rd row
        playerStatus = new PlayerStatus(skin);
        hotbar = new Hotbar(skin);


        //first row
        add(menu).top().left();
        add(inventoryButt).colspan(2).expandX().top().right();
        row();

        //second row
        add();
        add(inventory).expandY().center(); //TODO: Inventory
        add(); //TODO: ActionsPossible
        row();

        //third row
        add(playerStatus).bottom();
        playerStatus.pack();
        add(hotbar).colspan(2).bottom().padRight(playerStatus.getWidth());
    }

    public void setHealth(final float value){
        playerStatus.setHealth(value);
    }

    public void setExp(final float value){
        playerStatus.setExp(value);
    }

    public void setMana(final float value){
        playerStatus.setMana(value);
    }

    public void setBars(final float healthValue, final float expValue, final float manaValue){
        playerStatus.setBars(healthValue, expValue, manaValue);
    }

    public void changeSlotHotbar(int slotIndex){
        hotbar.changeSlot(slotIndex);
    }

    public void nextSlotHotbar() {
        if(hotbar.getCurrentPointedSlot() != hotbar.getNumberOfSlots() - 1){
            hotbar.changeSlot(hotbar.getCurrentPointedSlot() + 1);
        }
        else{
            hotbar.changeSlot(0);
        }
    }
    public void previousSlotHotbar() {
        if(hotbar.getCurrentPointedSlot() != 0){
            hotbar.changeSlot(hotbar.getCurrentPointedSlot() - 1);
        }
        else{
            hotbar.changeSlot(hotbar.getNumberOfSlots() - 1);
        }
    }

}
