package com.tuquoque.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.screens.ScreenType;
import com.tuquoque.game.ui.inventory.Inventory;
import com.tuquoque.game.world.entities.Player;


public class GameUI extends Table {
    private static GameUI uniqueInstance;
    final private PlayerStatus playerStatus;
    final private Hotbar hotbar;
    final private Inventory inventory;
    final private Dialogue dialogue;
    final ActionPossibleUI actionPossible;

    /**
    * Singleton constructor of the uniqueInstance of GameUI
    */
    public static GameUI getInstance(final GameStarter context, final Skin skin, Player player){
        uniqueInstance = new GameUI(context, skin, player);
        return uniqueInstance;
    }

    /**
     * We use Singleton pattern, this is the getter of the uniqueInstance of GameUI.
     * Use this only if sure that it has been already initialized.
     */
    public static GameUI getInstance() {
        if (uniqueInstance == null) {
            Gdx.app.error(GameUI.class.getSimpleName(), "Tried to access to Singleton class GameUI, but not initialised yet (use getInstance(Gamestarter ..., ...)");
        }
        return uniqueInstance;
    }

    private GameUI(final GameStarter context, final Skin skin, Player player) {
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
        Button inventoryButt = new Button(skin.getDrawable("inventoryIconInactive"), skin.getDrawable("inventoryIconActive"));

        //widget 2nd row
        inventory = new Inventory(skin, player, context);
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

        dialogue=new Dialogue(skin, "PROVA DI DIALOGO PIU LUNGA PER VEDERE COME SI COMPORTANO LE RIGHE", "TIZIO");
        actionPossible =new ActionPossibleUI(skin);


        //widgets 3rd row
        playerStatus = new PlayerStatus(skin);
        hotbar = new Hotbar(skin);

        //first row
        add(menu).top().left();
        add().expandX().top().right();
        add(inventoryButt).top().right().size( 80,70);
        row();

        //second row
        add(inventory).expandY().center();
        add(dialogue).bottom().left().size(600, 120).padTop(200);
        add(actionPossible).bottom().right();
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

    public Inventory getInventory() {
        return inventory;
    }

    public ActionPossibleUI getActionPossible(){
        return actionPossible;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
