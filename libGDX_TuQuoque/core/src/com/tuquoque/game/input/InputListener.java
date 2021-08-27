package com.tuquoque.game.input;

/**
 * Interface that is stored in InputManager and allows class which implements it to be notified of inputs
 * with the following methods
 *
 * @see InputManager
 */
public interface InputListener {
    /**
     * When a key is pressed, the listener is notified of this key by InputManager,
     * manage behaviour for that
     *
     * @param manager InputManager managing current listener
     * @param key key pressed
     * */
    void keyPressed(final InputManager manager, final GameKeys key);

    /**
     * When a key is released, the listener is notified of this key by InputManager,
     * manage behaviour for that
     *
     * @param manager InputManager managing current listener
     * @param key key released
     * */
    void KeyUp(final InputManager manager, final GameKeys key);

    void scrollVertical(final InputManager manager, final float amount);
}
