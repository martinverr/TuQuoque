package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SettingsUI extends Table {

    public Label settingsMenu;
    public Label music;
    public Slider musicVolume;
    public Slider effectsVolume;
    public Label musicValue;
    public Label effectsValue;
    public Label effects;

    public SettingsUI(Skin skin) {
        super(skin);
        setFillParent(true);

        settingsMenu=new Label("SETTINGS MENU", skin, "huge");

        music = new Label("MUSIC", skin, "huge");
        effects=new Label("EFFECTS", skin, "huge");

        musicValue=new Label("0",skin,"normal");
        effectsValue=new Label("0",skin,"normal");

        musicVolume=new Slider(0,1,0.01f,false,skin,"default");
        effectsVolume=new Slider(0,1,0.01f,false,skin,"default");

        setDebug(false);

        add(settingsMenu).colspan(3).top().center();
        row().padTop(100);

        add(music).colspan(3).center();
        row();
        add(musicValue).colspan(3).center();
        row();
        add(new Image(skin.getDrawable("SliderBegin"))).right();
        add(musicVolume).fillX();
        add(new Image(skin.getDrawable("SliderBegin"))).left();

        row().padTop(20);

        add(effects).colspan(3).center();
        row();
        add(effectsValue).colspan(3).center();
        row();
        add(new Image(skin.getDrawable("SliderBegin"))).right();
        add(effectsVolume).fillX();
        add(new Image(skin.getDrawable("SliderBegin"))).left();

        musicVolume.setValue(1);
        effectsVolume.setValue(1);
    }

    public Slider getMusicVolume() {
        return musicVolume;
    }

    public Slider getEffectsVolume(){
        return effectsVolume;
    }

    public void setMusicValue(int volume){
        musicValue.setText(String.valueOf(volume));
    }

    public void setEffectsValue(int volume){
        effectsValue.setText(String.valueOf(volume));
    }

}
