package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

enum ActionType {
    CHAT("Parla", "key_E", "action_chat"),
    PORTAL("Attraversa", "key_SPACE", "action_portal");

    private final String text;
    private final String key;
    private final String action;

    ActionType(final String text, final String key, final String action) {
        this.text = text;
        this.key = key;
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public String getKey() {
        return key;
    }

    public String getAction() {
        return action;
    }
}



public class ActionPossibleUI extends Table {
    private Skin skin;


    public ActionPossibleUI(Skin skin, ActionType actionType){
        this.skin = skin;
        this.setDebug(false);

        add(new Image(skin.getDrawable(actionType.getKey())));
        add(new Label(actionType.getText(), skin, "debug")).expandX().padLeft(5);
        row();
        add(new Image((skin.getDrawable(actionType.getAction())))).colspan(2);
    }
}
