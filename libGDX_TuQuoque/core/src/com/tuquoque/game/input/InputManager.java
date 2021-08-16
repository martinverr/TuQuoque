package com.tuquoque.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    private final GameKeys[] keyMapping;
    private final boolean[] keyStatus;
    private final Array<InputListener> listeners;

    public InputManager(){
        this.keyMapping = new GameKeys[256];
        for(final GameKeys gameKey : GameKeys.values()){
            for (final int code : gameKey.keyCode){
                keyMapping[code] = gameKey;
            }
        }
        keyStatus = new boolean[GameKeys.values().length];
        listeners = new Array<InputListener>();
    }

    public void addInputListener(final InputListener listener){
        listeners.add(listener);
    }

    public void removeInputListener(final InputListener listener){
        listeners.removeValue(listener, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        final GameKeys gameKey = keyMapping[keycode];

        if(gameKey == null){
            return false;
        }

        notifyKeyDown(gameKey);

        return false;
    }

    public void notifyKeyDown(final GameKeys gameKey){
        keyStatus[gameKey.ordinal()] = true;
        for(final InputListener listener : listeners){
            listener.KeyUp(this, gameKey);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
