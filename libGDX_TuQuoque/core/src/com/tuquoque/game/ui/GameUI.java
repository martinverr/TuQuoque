package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.screens.ScreenType;

import java.util.ArrayList;

public class GameUI extends Table {

    public GameUI(final GameStarter context, final Skin skin){
        super(skin);

        //table properties
        setFillParent(true);
        pad(20);
        setDebug(false);

        //widgets 1st row
        Button menu = new Button(skin.getDrawable("menuIconInactive"), skin.getDrawable("menuIconActive"));
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                context.setScreen(ScreenType.MAINMENU);
            }});
        Button inventory = new Button(skin.getDrawable("inventoryIconInactive"), skin.getDrawable("inventoryIconActive"));

        //widgets 2nd row
        Image playerStatus = new Image(skin.getDrawable("playerStatus"));   //sarà un table da noi definito in futuro
        Hotbar hotbar = new Hotbar(skin);


        //first row
        add(menu).top().left();
        add(inventory).expandX().top().right();
        row();

        //second row
        add(playerStatus).expandY().bottom();
        add(hotbar).bottom().padRight(playerStatus.getWidth());
    }
}



class Hotbar extends Table{
    ArrayList<Stack> slots;

    public Hotbar(Skin skin){
        super(skin);
        slots = new ArrayList<>(8);

        setScale(1.5f);
        setDebug(false);


        add(new Image(skin.getDrawable("hotbarBegin"))).expandX().bottom().right();
        for(int i=0; i<8; i++){
            Stack currentStack = new Stack();
            currentStack.add(new Image(skin.getDrawable("hotbarMid")));
            add(currentStack);
            slots.add(currentStack);
        }
        add(new Image(skin.getDrawable("hotbarEnd"))).bottom().expandX().left();
    }
}

class playerStatus extends Table{

    public playerStatus(Skin skin){
        super(skin);
        setDebug(true);

        add().size(74,64).colspan(3);
        add().size(102,15).pad(4,0,2,0);
        add().size(21,64).colspan(3);

        row();
        add().size(102,15).pad(2,0,2,0);
        row();
        add().size(102,15).pad(2,0,4,0);
    }
}