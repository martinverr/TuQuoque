package com.tuquoque.game.ui;

public enum ActionType {
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