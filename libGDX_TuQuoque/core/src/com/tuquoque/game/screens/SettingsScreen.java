package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.input.GameKeys;
import com.tuquoque.game.input.InputListener;
import com.tuquoque.game.input.InputManager;

/*
* TODO: Preferences(LibGDX)
*  for audio (enable/disable music, music volume, effects sounds volume)
*/

public class SettingsScreen extends AbstractScreen implements InputListener {

    private Stage stage;
    private Image music_ON_active;
    private Image music_ON_inactive;
    private Scaling scaling;

    public SettingsScreen(GameStarter context) {
        super(context);

        // Setting up stage
        stage=new Stage(new FitViewport(1280,720));
        Gdx.input.setInputProcessor(stage);

        // Actors
        music_ON_active=new Image(new Texture(Gdx.files.internal("buttons/music_ON_red.png")));
        music_ON_inactive=new Image(new Texture(Gdx.files.internal("buttons/music_ON_white.png")));
        stage.addActor(music_ON_active);
        stage.addActor(music_ON_inactive);

        // Setting up actors
        music_ON_active.setHeight(music_ON_active.getHeight()/2);
        music_ON_active.setWidth(music_ON_active.getWidth()/2);
        music_ON_active.setPosition(200,500);

        music_ON_inactive.setHeight(music_ON_inactive.getHeight()/2);
        music_ON_inactive.setWidth(music_ON_inactive.getWidth()/2);
        music_ON_inactive.setPosition(200,500);

    }

    @Override
    protected Table getScreenUI(Skin skin) {
        return null;
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
        inputManager.addInputListener(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width,height);
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
        stage.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        switch (key){
            case BACK: //set screen 'MAINMENU'
                context.setScreen(ScreenType.MAINMENU);
            default:
                break;
        }
    }

    @Override
    public void KeyUp(InputManager manager, GameKeys key) {

    }
}
