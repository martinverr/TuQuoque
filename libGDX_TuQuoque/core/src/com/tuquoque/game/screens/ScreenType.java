package com.tuquoque.game.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    GAME(GameScreen.class),
    MAINMENU(MainmenuScreen.class),
    SETTINGS(SettingsScreen.class),
    LOADING(LoadingScreen.class);

    //class of a specific Screen
    private final Class <? extends AbstractScreen>  screenClass;

    /*
    * Constructor of ScreenType
    *
    * @param screenClass: type of Screen
    */
    ScreenType(final Class<? extends AbstractScreen> screenClass){
        this.screenClass = screenClass;
    }

    /*
     * Getter of ScreenClass
     */
    public Class<? extends  Screen> getScreenClass(){
        return screenClass;
    }
}
