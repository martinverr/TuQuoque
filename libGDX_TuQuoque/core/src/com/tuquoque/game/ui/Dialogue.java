package com.tuquoque.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.tuquoque.game.ui.dialogues.Conversation;
import com.tuquoque.game.ui.dialogues.ConversationGraph;
import com.tuquoque.game.world.entities.npc.NPC;

public class Dialogue extends Table {

    private Skin skin;
    private Label dialogueText;
    private Label dialogueSpeaker;

    private ConversationGraph graph;
    private Json json;


    public Dialogue(Skin skin, String text, String speaker) {
        this.skin=skin;
        this.json = new Json();
        this.setVisible(false);
        setDebug(false);

        //Init WidgetsGroup
        Stack dialogueStack = new Stack();;
        Table table = new Table(skin);
        table.setDebug(false);

        //Init Widgets
        Image dialogueBox=new Image(skin.getDrawable("dialogbox"));
        dialogueBox.setSize(600,120);
        dialogueText= new Label(text, skin, "debug");
        dialogueText.setAlignment(Align.left);
        dialogueText.setWrap(true);
        dialogueSpeaker=new Label(speaker, skin, "debug");
        dialogueSpeaker.setAlignment(Align.topLeft);

        //Layout setup
        add(dialogueStack);
        dialogueStack.add(dialogueBox);
        dialogueStack.add(table);
        table.add(dialogueSpeaker).padRight(450).padBottom(5);
        table.row();
        table.add(dialogueText).width(600).pad(0,10,45,10);

    }

    public void loadConversation(NPC npc){
        //Set Speaker name
        if(npc == null){
            this.setSpeaker("Sistema");
        }
        else
            this.setSpeaker(npc.getNPCname());


        //Set ConversationGraph
        String conversationFilenamePath = npc.getConversationConfigPath();
        if( conversationFilenamePath.isEmpty() || !Gdx.files.internal(conversationFilenamePath).exists() ){
            Gdx.app.debug(this.getClass().getSimpleName(), "Conversation file does not exist!");
            return;
        }
        graph = json.fromJson(ConversationGraph.class, Gdx.files.internal(conversationFilenamePath));

        populateConversationDialog(graph.getCurrentConversationID());
    }

    public void setConversationGraph(ConversationGraph graph){
        if( graph != null ) graph.removeAllObservers();
        this.graph = graph;
        populateConversationDialog(graph.getCurrentConversationID());
    }

    private void populateConversationDialog(String conversationID){
        Conversation conversation = graph.getConversationByID(conversationID);
        if( conversation == null ) return;
        graph.setCurrentConversation(conversationID);
        setMessage(conversation.getDialog());
    }

    public void setMessage(String string){
        dialogueText.setText(string);
    }

    public void setSpeaker(String string){
        dialogueSpeaker.setText(string);
    }

}
