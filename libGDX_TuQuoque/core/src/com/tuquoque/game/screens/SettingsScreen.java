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
import com.tuquoque.game.ui.SettingsUI;

/*
* TODO: Preferences(LibGDX)
*  for audio (enable/disable music, music volume, effects sounds volume)
*/

public class SettingsScreen extends AbstractScreen implements InputListener {

    public SettingsScreen(GameStarter context) {
        super(context);

    }

    @Override
    protected Table getScreenUI(Skin skin) {
        return new SettingsUI(skin);
    }

    @Override
    public void show() {
        super.show();
        ScreenUtils.clear(0, 0, 0, 1);
        inputManager.addInputListener(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
    }

    public void resize(int width, int height){
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
        inputManager.removeInputListener(this);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        if (key == GameKeys.BACK) { //set screen 'MAINMENU'
            context.setScreen(ScreenType.MAINMENU);
        }
    }

    @Override
    public void KeyUp(InputManager manager, GameKeys key) {

    }

    @Override
    public void scrollVertical(InputManager manager, float amount) {

    }
}
