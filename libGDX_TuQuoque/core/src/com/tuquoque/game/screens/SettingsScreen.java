package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;

/*
* TODO: Preferences(LibGDX)
*  for audio (enable/disable music, music volume, effects sounds volume)
*/

public class SettingsScreen extends AbstractScreen {
    public SettingsScreen(GameStarter context) {
        super(context);
    }

    @Override
    public void show() {
        ScreenUtils.clear(1, 1, 1, 1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        //if ESC pressed -> set screen 'MAINMENU'
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            context.setScreen(ScreenType.MAINMENU);
        }
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
}
