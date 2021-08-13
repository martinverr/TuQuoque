package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;

public class LoadingScreen extends AbstractScreen {

    private Texture loadingTexture;

    private AssetManager assetManager;

    public LoadingScreen(final GameStarter context){
        super(context);

        loadingTexture = new Texture(Gdx.files.internal("background/loading.png"));

        //AssetManager
        assetManager = context.getAssetManager();
        context.getAssetManager().load("map/prova.tmx", TiledMap.class);
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        /* TODO: press space to GameScreen only if assetManager loaded (decide if do it manually or automatically
        * if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        *   context.setScreen(ScreenType.GAME);
        * }
        */

        batch.begin();
        batch.draw(loadingTexture, 0, 0, 16, 9);
        batch.end();

        if(assetManager.update()){
            context.setScreen(ScreenType.GAME);
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