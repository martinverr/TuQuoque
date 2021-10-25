package com.tuquoque.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.audio.AudioManager;
import com.tuquoque.game.audio.AudioType;
import com.tuquoque.game.input.GameKeys;
import com.tuquoque.game.input.InputListener;
import com.tuquoque.game.input.InputManager;
import com.tuquoque.game.ui.SettingsUI;


public class SettingsScreen extends AbstractScreen implements InputListener {

    AudioManager audioManager;

    public SettingsScreen(GameStarter context) {
        super(context);

        audioManager=context.getAudioManager();
    }

    @Override
    protected Table getScreenUI(Skin skin) {
        return new SettingsUI(skin, context);
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
