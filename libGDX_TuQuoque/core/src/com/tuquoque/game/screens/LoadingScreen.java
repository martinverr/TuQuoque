package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.audio.AudioType;

/*
* TODO: aggiungere gli assets del mainmenu all'assetmanager, sia per risolvere quindi il bug della doppia musica mainmenu e game
 */

public class LoadingScreen extends AbstractScreen {

    private final Texture loadingTexture;
    private final Camera camera;
    private final AssetManager assetManager;

    public LoadingScreen(final GameStarter context){
        super(context);
        camera = context.getCamera();
        loadingTexture = new Texture(Gdx.files.internal("background/loading.png"));

        //AssetManager
        assetManager = context.getAssetManager();

        //load map
        context.getAssetManager().load("map/prova.tmx", TiledMap.class);

        //load audio
        for(final AudioType audioType : AudioType.values()){
            if (audioType.isMusic()){
                System.out.println("Music found: " + audioType.name());
                assetManager.load(audioType.getFilePath(), Music.class);
            } else {
                assetManager.load(audioType.getFilePath(), Sound.class);
            }

        }
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        batch.begin();
        batch.draw(loadingTexture, 0, 0, 16, 9);
        batch.end();

        if(assetManager.update()){
            context.setScreen(ScreenType.MAINMENU);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        loadingTexture.dispose();
    }
}