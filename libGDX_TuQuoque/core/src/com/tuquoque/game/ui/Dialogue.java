package com.tuquoque.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class Dialogue extends Table {

    Skin skin;
    Stack dialogueStack;
    Label dialogueText;
    Label dialogueSpeaker;
    Table table;



    public Dialogue(Skin skin, String text, String speaker) {
            this.skin=skin;
            this.setVisible(false);
            setDebug(true);

            dialogueStack=new Stack();
            table=new Table(skin);
            table.setDebug(true);

            Image dialogueBox=new Image(skin.getDrawable("dialogbox"));
            dialogueText= new Label(text, skin, "debug");
            dialogueSpeaker=new Label(speaker, skin, "debug");
            dialogueBox.setSize(600,120);

            System.out.println(dialogueStack);

            dialogueSpeaker.setAlignment(Align.topLeft);
            dialogueText.setAlignment(Align.center);

            add(dialogueStack);
            dialogueStack.add(table);
            //dialogueStack.add(dialogueBox);
            table.add(dialogueSpeaker).padRight(450).padBottom(5);
            table.row();
            table.add(dialogueText).padBottom(45);

    }

    public void setMessage(String string){
        dialogueText.setText(string);
    }

    public void setSpeaker(String string){
        dialogueSpeaker.setText(string);
    }

}
