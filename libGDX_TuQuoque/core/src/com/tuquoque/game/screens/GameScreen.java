package com.tuquoque.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.sprites.Player;
import com.tuquoque.game.utils.WorldCreator;
import static com.tuquoque.game.GameStarter.UNIT_SCALE;


public class GameScreen extends AbstractScreen {
    //Player
    private final Player playerB2D;
    private final Vector2 savedPlayerCoords = new Vector2(8, 4.5f);

    //map
    private final OrthogonalTiledMapRenderer mapRenderer;

    //camera (not the gamestarter camera)
    private final OrthographicCamera gamecamera;

    float elapsedTime=0;

    public GameScreen(final GameStarter context){
        super(context);

        //init camera
        gamecamera = new OrthographicCamera(16, 9);
        gamecamera.position.set(savedPlayerCoords, 0);
        //gamecamera.update();
        batch.setProjectionMatrix(gamecamera.combined);

        //Create player
        playerB2D = new Player(world, savedPlayerCoords);

        //mapRenderer init
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, batch);
        mapRenderer.setMap(context.getAssetManager().get("map/prova.tmx", TiledMap.class));

        //mapObjects in B2D World creation
        new WorldCreator(world, mapRenderer.getMap());
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    private void handleInput(){
        //if WASD or ARROWS -> setSpeed to player
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            playerB2D.setSpeedX(playerB2D.NOMINAL_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            playerB2D.setSpeedX(-playerB2D.NOMINAL_SPEED);
        } else {
            playerB2D.setSpeedX(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            playerB2D.setSpeedY(playerB2D.NOMINAL_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            playerB2D.setSpeedY(-playerB2D.NOMINAL_SPEED);
        } else {
            playerB2D.setSpeedY(0);
        }

        //if ESC pressed -> set screen 'MAINMENU'
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            savedPlayerCoords.set(playerB2D.B2DBody.getPosition().x, playerB2D.B2DBody.getPosition().y);
            context.setScreen(ScreenType.MAINMENU);
        }

        /**
        * BOX2D DEBUG COMMANDS
        */
        //if NUMPAD_9 pressed -> set hitbox visible or not
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)){
            box2DDebugRenderer.setDrawBodies(!box2DDebugRenderer.isDrawBodies());
        }

        //if NUMPAD_8 pressed -> set contacts visible or not
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)){
            box2DDebugRenderer.setDrawContacts(!box2DDebugRenderer.isDrawContacts());
        }
        //if NUMPAD_7 pressed -> set velocity visible or not
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7)){
            box2DDebugRenderer.setDrawVelocities(!box2DDebugRenderer.isDrawVelocities());
        }

    }

    @Override
    public void render(float delta) {

        int[] layers_1 ={0,1,2,3};
        int[] layers_2 ={4,5,6,7};

        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        //map render
        mapRenderer.setView(gamecamera);
        mapRenderer.render(layers_1);

        //handling input
        handleInput();

        //update player to new speed after possible moving inputs
        playerB2D.B2DBody.applyLinearImpulse(
                playerB2D.getSpeedX()-playerB2D.B2DBody.getLinearVelocity().x,
                playerB2D.getSpeedY()-playerB2D.B2DBody.getLinearVelocity().y,
                playerB2D.B2DBody.getWorldCenter().x, playerB2D.B2DBody.getWorldCenter().y,
                true);


        batch.begin();
        //drawing player
        batch.draw((TextureRegion) playerB2D.getCurrentAnimation().getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.65f,
                        playerB2D.B2DBody.getPosition().y -0.7f,
                        1.3f,1.6f);
        batch.end();

        gamecamera.position.x = playerB2D.B2DBody.getPosition().x;
        gamecamera.position.y = playerB2D.B2DBody.getPosition().y;
        gamecamera.update();

        mapRenderer.render(layers_2);

        //World of B2D
        world.step(delta, 6, 2);
        box2DDebugRenderer.render(world, gamecamera.combined);
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
        mapRenderer.dispose();
    }
}
