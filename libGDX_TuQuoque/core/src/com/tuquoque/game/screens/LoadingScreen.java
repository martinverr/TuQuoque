package com.tuquoque.game.screens;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.audio.AudioType;
import com.tuquoque.game.ui.LoadingUI;



public class LoadingScreen extends AbstractScreen {

    private final AssetManager assetManager;
    private final Color bgColor;

    public LoadingScreen(final GameStarter context){
        super(context);
        bgColor = context.getSkin().getColor("neroOpaco");

        //AssetManager
        assetManager = context.getAssetManager();

        //load map
        context.getAssetManager().load("map/prova.tmx", TiledMap.class);

        //load audio
        for(final AudioType audioType : AudioType.values()){
            if (audioType.isMusic()){
                assetManager.load(audioType.getFilePath(), Music.class);
            } else {
                assetManager.load(audioType.getFilePath(), Sound.class);
            }

        }
    }

    @Override
    protected Table getScreenUI(Skin skin) {
        return new LoadingUI(skin);
    }

    @Override
    public void show() {
        super.show();
        ScreenUtils.clear(bgColor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(bgColor);

        if(assetManager.update()){
            context.setScreen(ScreenType.MAINMENU);
        }
        ((LoadingUI) screenUI).setLoadingStatus(assetManager.getQueuedAssets());
        ((LoadingUI) screenUI).setProgressBar(assetManager.getProgress());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
    }
}