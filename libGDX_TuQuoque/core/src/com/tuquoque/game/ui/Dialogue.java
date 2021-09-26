package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class Dialogue extends Table {

    Skin skin;

    TextField dialogueText;

    public Dialogue(Skin skin, String text) {
            this.skin=skin;

            Image dialogueBox=new Image(skin.getDrawable("DialogueBar"));
            add(dialogueBox).size(500,100);
            dialogueText=new TextField(text, skin, "normal");

    }

}
