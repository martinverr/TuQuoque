package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;

import java.awt.*;

public class GameScreen extends AbstractScreen {

    final  private SpriteBatch batch;

    final private Animation<TextureRegion> walkRightAnimation;
    final private Animation<TextureRegion> walkLeftAnimation;
    final private Animation<TextureRegion> idleRightAnimation;
    private final Animation<TextureRegion> idleLeftAnimation;

    float elapsedTime;
    private final Rectangle player;

    // True = right, False = left
    private boolean direction=true;

    public GameScreen(final GameStarter context){
        super(context);

        batch = new SpriteBatch();

        player=new Rectangle();

        player.height=32;
        player.width=32;

        player.x=1280/2-32/2;
        player.y=720/2-32/2;

        Texture imgRight = new Texture("player/Gladiator-Sprite Sheet.png");
        Texture imgLeft = new Texture("player/Gladiator-Sprite Sheet-Left.png");

        TextureRegion[][] tmpFramesRight =TextureRegion.split(imgRight, 32,32);
        TextureRegion[][] tmpFramesLeft =TextureRegion.split(imgLeft, 32,32);

        TextureRegion[] walkFramesRight = new TextureRegion[8];
        TextureRegion[] walkFramesLeft = new TextureRegion[8];
        TextureRegion[] idleFramesRight = new TextureRegion[5];
        TextureRegion[] idleFramesLeft = new TextureRegion[5];

        int index=0;

        for(int i=0;i<8;i++){
            walkFramesRight[index]= tmpFramesRight[1][i];
            walkFramesLeft[index]= tmpFramesLeft[1][i];
            index++;
        }

        index=0;

        for(int i=0;i<5;i++){
            idleFramesLeft[index]= tmpFramesLeft[0][i];
            idleFramesRight[index]= tmpFramesRight[0][i];
            index++;
        }

        walkRightAnimation =new Animation<>(1/8f, walkFramesRight);
        walkLeftAnimation=new Animation<>(1/8f, walkFramesLeft);
        idleLeftAnimation=new Animation<>(1/5f, idleFramesLeft);
        idleRightAnimation=new Animation<>(1/5f, idleFramesRight);
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void render(float delta) {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();


        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.x+=150*Gdx.graphics.getDeltaTime();
            batch.draw(walkRightAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
            direction=true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.x-=100*Gdx.graphics.getDeltaTime();
            batch.draw(walkLeftAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
            direction=false;
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.y-=100*Gdx.graphics.getDeltaTime();
            if(direction)
                batch.draw(walkRightAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
            else
                batch.draw(walkLeftAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
        }

        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.y+=150*Gdx.graphics.getDeltaTime();
            if(direction)
                batch.draw(walkRightAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
            else
                batch.draw(walkLeftAnimation.getKeyFrame(elapsedTime,true),player.x,player.y);
        }

        else{
            if(direction)
                batch.draw(idleRightAnimation.getKeyFrame(elapsedTime,true),player.x, player.y);
            else
                batch.draw(idleLeftAnimation.getKeyFrame(elapsedTime,true),player.x, player.y);
        }

        if(player.x<0)
            player.x=0;

        if(player.y<0)
            player.y=0;

        if(player.x+32>1280)
            player.x=1280-32;

        if(player.y+32>720)
            player.y=720-32;

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
