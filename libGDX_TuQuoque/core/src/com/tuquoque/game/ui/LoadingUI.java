package com.tuquoque.game.ui;


import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.Locale;

public class LoadingUI extends Table {
    private final ProgressBar progressBar;
    private final TextButton loadingText;
    private final TextButton currentLoadingAsset;

    public LoadingUI(final Skin skin){
        super(skin);
        setFillParent(true);

        progressBar = new ProgressBar(0, 1, 0.01f, false, skin, "default");
        loadingText = new TextButton("LOADING...", skin, "huge");
        currentLoadingAsset = new TextButton("NONE", skin, "debug");
        currentLoadingAsset.getLabel().setWrap(true);

        add(loadingText).expandX().fillX().bottom().colspan(2);
        row();

        add(currentLoadingAsset).expandX().fillX().bottom().colspan(2);
        row();

        add(new Image(skin.getDrawable("before"))).width(50).padLeft(25);
        add(progressBar).expandX().fillX().bottom().pad(20,0,20,25).prefWidth(1200);
        bottom();
        //setDebug(true);
    }

    public void setProgressBar(final float progress){
        progressBar.setValue(progress);
    }
    public void setLoadingStatus(final int remaining){
        currentLoadingAsset.setText("assets remaining: " + remaining);
    }
}
