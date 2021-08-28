package com.tuquoque.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SettingsUI extends Table {

    public SettingsUI(Skin skin) {
        super(skin);
        setFillParent(true);

        Slider musicVolume=new Slider(0,100,1,false,skin,"default");
        add(musicVolume);



    }
}
