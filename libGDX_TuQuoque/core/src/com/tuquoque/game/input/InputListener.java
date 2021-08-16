package com.tuquoque.game.input;

public interface InputListener {
    void keyPressed(final InputManager manager, final GameKeys key);

    void KeyUp(final InputManager manager, final GameKeys key);
}
