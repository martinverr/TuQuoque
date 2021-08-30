package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SettingsUI extends Table {

    public SettingsUI(Skin skin) {
        super(skin);
        setFillParent(true);
        Label settingsMenu=new Label("SETTINGS MENU", skin, "huge");
        Label music = new Label("MUSIC", skin, "huge");
        Label effects=new Label("EFFECTS", skin, "huge");
        Slider musicVolume=new Slider(0,100,1,false,skin,"default");
        Slider effectsSlider=new Slider(0,100,1,false,skin,"default");

        setDebug(true);

        add(settingsMenu).colspan(3).top().center();
        row().padTop(20);

        add(music).colspan(3).center();
        row();
        add(new Image(skin.getDrawable("SliderBegin"))).right();
        add(musicVolume).fillX();
        add(new Image(skin.getDrawable("SliderBegin"))).left();

        row().padTop(20);

        add(effects).colspan(3).center();
        row();
        add(new Image(skin.getDrawable("SliderBegin"))).right();
        add(effectsSlider).fillX();
        add(new Image(skin.getDrawable("SliderBegin"))).left();
    }
}
