package com.tuquoque.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.sprites.Player;

import java.awt.*;


/*
 * TODO: collision
 *  TODO: map
 *   TODO: loadingScreen -> MainMenuScreen, add loadingScreen
 * */

public class GameScreen extends AbstractScreen {
    final private Animation<TextureRegion> walkRightAnimation;
    final private Animation<TextureRegion> walkLeftAnimation;
    final private Animation<TextureRegion> idleRightAnimation;
    private final Animation<TextureRegion> idleLeftAnimation;

    private final Player playerB2D;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private OrthographicCamera camera;

    float elapsedTime;

    // True = right, False = left
    private boolean direction = true;

    public GameScreen(final GameStarter context){
        super(context);
        this.camera = context.getCamera();

        //Create circle player
        playerB2D = new Player(world);

        //mapRenderer init
        mapRenderer = new OrthogonalTiledMapRenderer(null, 1/32f, batch);

        //texture stuff
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
        mapRenderer.setMap(context.getAssetManager().get("map/prova.tmx", TiledMap.class));

    }

    private void handleInputPlayerMov(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            playerB2D.setSpeedX(playerB2D.NOMINAL_SPEED);
            direction=true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            playerB2D.setSpeedX(-playerB2D.NOMINAL_SPEED);
            direction=false;
        } else {
            playerB2D.setSpeedX(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            playerB2D.setSpeedY(playerB2D.NOMINAL_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            playerB2D.setSpeedY(-playerB2D.NOMINAL_SPEED);
        } else {
            playerB2D.setSpeedY(0);
        }
    }

    @Override
    public void render(float delta) {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        //map render
        mapRenderer.setView(camera);
        mapRenderer.render();


        //handling input for player movement
        handleInputPlayerMov();
        playerB2D.B2DBody.applyLinearImpulse(
                playerB2D.getSpeedX()-playerB2D.B2DBody.getLinearVelocity().x,
                playerB2D.getSpeedY()-playerB2D.B2DBody.getLinearVelocity().y,
                playerB2D.B2DBody.getWorldCenter().x, playerB2D.B2DBody.getWorldCenter().y,
                true);


        batch.begin();

        //drawing player
        if(playerB2D.B2DBody.getLinearVelocity().x != 0 || playerB2D.B2DBody.getLinearVelocity().y != 0){
            if(direction)
                batch.draw(walkRightAnimation.getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.5f,
                        playerB2D.B2DBody.getPosition().y - 0.5f,
                        1,1.7f);
            else
                batch.draw(walkLeftAnimation.getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.5f,
                        playerB2D.B2DBody.getPosition().y - 0.5f,
                        1,1.7f);
        }
        else{
            if(direction)
                batch.draw(idleRightAnimation.getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.5f,
                        playerB2D.B2DBody.getPosition().y - 0.5f,
                        1,1.7f);
            else
                batch.draw(idleLeftAnimation.getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.5f,
                        playerB2D.B2DBody.getPosition().y - 0.5f,
                        1,1.7f);
        }
        batch.end();

        // collision player-screenBorder -> player reposition
        //TODO: box2D collision

        camera.position.x = playerB2D.B2DBody.getPosition().x;
        camera.position.y = playerB2D.B2DBody.getPosition().y;
        camera.update();

        //World of B2D
        world.step(delta, 6, 2);
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
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
