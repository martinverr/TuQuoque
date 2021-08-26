package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.screens.ScreenType;

public class GameUI extends Table {

    public GameUI(final GameStarter context, final Skin skin){
        super(skin);
        setFillParent(true);

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
        Image hotbarBegin = new Image(skin.getDrawable("hotbarBegin"));     //cambiare l'immagine dell'hud per avere solo la parte fissa a sx
        Image hotbar = new Image(skin.getDrawable("hotbarMid"));            //sarà un table da noi definito in futuro
        Image hotbarEnd = new Image(skin.getDrawable("hotbarEnd"));         //cambiare l'immagine dell'hud per avere solo la parte fissa a dx

        //table properties
        pad(20);
        setDebug(false);

        //first row
        add(menu).top().left();
        add(inventory).expandX().colspan(3).top().right();
        row();

        //second row
        add(playerStatus).expandY().bottom();
        add(hotbarBegin).expandX().bottom().right();
        add(hotbar).bottom();
        add(hotbarEnd).bottom().expandX().left();
    }
}