package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SettingsUI extends Table {

    public SettingsUI(Skin skin) {
        super(skin);
        setFillParent(true);
        TextField settingsMenu=new TextField("SETTINGS MENU", skin, "huge");
        TextField music=new TextField("MUSIC", skin, "huge");
        TextField effects=new TextField("EFFECTS", skin, "huge");
        Image sliderBegin=new Image(skin.getDrawable("SliderBegin"));
        Image sliderEnding=new Image(skin.getDrawable("SliderEnding"));
        Slider musicVolume=new Slider(0,100,1,false,skin,"default");

        setDebug(true);

        add(settingsMenu).growX().colspan(3).align(2);
        row();

        add(music).colspan(3).growX();
        row().padTop(20);
        add(sliderBegin).right();
        add(musicVolume).fillX();
        add(sliderEnding).left();

        row();

        add(effects).colspan(3).growX();
        row().padTop(20);
        add(sliderBegin).right();
        add(musicVolume).fillX();
        add(sliderEnding).left();


    }
}
