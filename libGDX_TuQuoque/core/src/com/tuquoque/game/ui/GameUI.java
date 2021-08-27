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
        setDebug(false);

        //widgets 1st row
        Button menu = new Button(skin.getDrawable("menuIconInactive"), skin.getDrawable("menuIconActive"));
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                context.setScreen(ScreenType.MAINMENU);
            }
        });
        Button inventory = new Button(skin.getDrawable("inventoryIconInactive"), skin.getDrawable("inventoryIconActive"));

        //widgets 2nd row
        playerStatus = new PlayerStatus(skin);
        hotbar = new Hotbar(skin);


        //first row
        add(menu).top().left();
        add(inventory).expandX().top().right();
        row();

        //second row
        add(playerStatus).expandY().bottom();
        playerStatus.pack();
        add(hotbar).bottom().padRight(playerStatus.getWidth());
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
}
