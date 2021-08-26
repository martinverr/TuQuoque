package com.tuquoque.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.input.GameKeys;
import com.tuquoque.game.input.InputListener;
import com.tuquoque.game.input.InputManager;
import com.tuquoque.game.ui.LoadingUI;
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
        ScreenUtils.clear(0, 0, 0, 1);
        inputManager.addInputListener(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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
