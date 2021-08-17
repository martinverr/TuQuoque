package com.tuquoque.game.input;

import com.badlogic.gdx.Input;

public enum GameKeys {
    UP(Input.Keys.W, Input.Keys.UP),
    DOWN(Input.Keys.S, Input.Keys.DOWN),
    LEFT(Input.Keys.A, Input.Keys.LEFT),
    RIGHT(Input.Keys.D, Input.Keys.RIGHT),
    NEXT(Input.Keys.SPACE, Input.Keys.ENTER),
    BACK(Input.Keys.ESCAPE),
    DEBUG7(Input.Keys.NUMPAD_7),
    DEBUG8(Input.Keys.NUMPAD_8),
    DEBUG9(Input.Keys.NUMPAD_9);

    final int[] keyCode;

    GameKeys(final int... keyCode){
        this.keyCode = keyCode;
    }

    public int[] getKeyCode(){
        return keyCode;
    }
}
