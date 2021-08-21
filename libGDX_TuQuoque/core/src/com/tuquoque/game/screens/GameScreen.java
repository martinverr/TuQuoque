package com.tuquoque.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.audio.AudioType;
import com.tuquoque.game.input.GameKeys;
import com.tuquoque.game.input.InputListener;
import com.tuquoque.game.input.InputManager;
import com.tuquoque.game.sprites.NPC;
import com.tuquoque.game.sprites.Player;
import com.tuquoque.game.utils.NPC_handler;
import com.tuquoque.game.utils.WorldCreator;
import static com.tuquoque.game.GameStarter.UNIT_SCALE;


public class GameScreen extends AbstractScreen implements InputListener {
    //Player
    private final Player playerB2D;
    private final NPC npc1;
    private final Vector2 savedPlayerCoords = new Vector2(11, 12.5f);
    float elapsedTime=0;
    private boolean newMovementInput = false;
    private NPC_handler npc_handler;

    //map
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final int[] layers_1 ={0,1,2,3};
    private final int[] layers_2 ={4,5,6,7};

    //camera (not the gamestarter camera)
    private final OrthographicCamera gamecamera;

    public GameScreen(final GameStarter context){
        super(context);

        //init camera
        gamecamera = new OrthographicCamera(16, 9);
        gamecamera.position.set(savedPlayerCoords, 0);
        //gamecamera.update();
        batch.setProjectionMatrix(gamecamera.combined);

        //Create player
        playerB2D = new Player(world, savedPlayerCoords);
        npc1=new NPC(world, new Vector2(15,13));

        //mapRenderer init
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, batch);
        mapRenderer.setMap(context.getAssetManager().get("map/prova.tmx", TiledMap.class));

        //mapObjects in B2D World creation
        new WorldCreator(world, mapRenderer.getMap());

        // creating NPC_handler
        npc_handler = new NPC_handler(new NPC[]{npc1}, playerB2D);
        /*
        * oppure
        * npc_handler = new NPC_handler(playerB2D)
        * npc_handler.addNPC(npc1);
        */
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
        inputManager.addInputListener(this);
        audioManager.playAudio(AudioType.AMBIENT_PALATINO);
    }


    @Override
    public void render(float delta) {

        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        //map render
        mapRenderer.setView(gamecamera);
        mapRenderer.render(layers_1);

        //update player to new speed after moving inputs
        if(newMovementInput){
            playerB2D.B2DBody.applyLinearImpulse(
                    playerB2D.getSpeedX()-playerB2D.B2DBody.getLinearVelocity().x,
                    playerB2D.getSpeedY()-playerB2D.B2DBody.getLinearVelocity().y,
                    playerB2D.B2DBody.getWorldCenter().x, playerB2D.B2DBody.getWorldCenter().y,
                    true);
        }

        //footsteps sound if player is moving
        if(playerB2D.B2DBody.getLinearVelocity().isZero())
            audioManager.stopLoopingSound(AudioType.FOOTSTEPS_STONE);
        else
            audioManager.playAudio(AudioType.FOOTSTEPS_STONE);

        batch.begin();
        //drawing player
        batch.draw((TextureRegion) playerB2D.getCurrentAnimation().getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.65f,
                        playerB2D.B2DBody.getPosition().y -0.7f,
                        1.3f,1.6f);
        batch.end();

        //drawing last layers of the map
        mapRenderer.render(layers_2);


        //camera follows player
        gamecamera.position.x = playerB2D.B2DBody.getPosition().x;
        gamecamera.position.y = playerB2D.B2DBody.getPosition().y;
        gamecamera.update();


        //World of B2D
        world.step(delta, 6, 2);
        box2DDebugRenderer.render(world, gamecamera.combined);

        npc_handler.update();

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
        inputManager.removeInputListener(this);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        switch (key){
            /*
            * Player Movement
            */
            case UP:
                playerB2D.setSpeedY(playerB2D.NOMINAL_SPEED);
                newMovementInput = true;
                break;
            case DOWN:
                newMovementInput = true;
                playerB2D.setSpeedY(-playerB2D.NOMINAL_SPEED);
                break;
            case LEFT:
                newMovementInput = true;
                playerB2D.setSpeedX(-playerB2D.NOMINAL_SPEED);
                break;
            case RIGHT:
                newMovementInput = true;
                playerB2D.setSpeedX(playerB2D.NOMINAL_SPEED);
                break;

            /*
            * Screens
            */
            case BACK:
                savedPlayerCoords.set(playerB2D.B2DBody.getPosition().x, playerB2D.B2DBody.getPosition().y);
                context.setScreen(ScreenType.MAINMENU);

            /*
             * BOX2D DEBUG COMMANDS
             */
            case DEBUG7:    //set velocity visible or not
                box2DDebugRenderer.setDrawVelocities(!box2DDebugRenderer.isDrawVelocities());
                break;
            case DEBUG8:    //set contacts visible or not
                box2DDebugRenderer.setDrawContacts(!box2DDebugRenderer.isDrawContacts());
                break;
            case DEBUG9:    //set hitbox visible or not
                box2DDebugRenderer.setDrawBodies(!box2DDebugRenderer.isDrawBodies());
                break;
            default:
                break;
        }
    }

    @Override
    public void KeyUp(InputManager manager, GameKeys key) {
        switch (key){
            case UP:
                newMovementInput = true;
                playerB2D.setSpeedY(manager.isKeyPressed(GameKeys.DOWN) ? -playerB2D.NOMINAL_SPEED : 0);
                break;
            case DOWN:
                newMovementInput = true;
                playerB2D.setSpeedY(manager.isKeyPressed(GameKeys.UP) ? playerB2D.NOMINAL_SPEED : 0);
                break;
            case LEFT:
                newMovementInput = true;
                playerB2D.setSpeedX(manager.isKeyPressed(GameKeys.RIGHT) ? playerB2D.NOMINAL_SPEED : 0);
                break;
            case RIGHT:
                newMovementInput = true;
                playerB2D.setSpeedX(manager.isKeyPressed(GameKeys.LEFT) ? -playerB2D.NOMINAL_SPEED : 0);
                break;
            default:
                break;
        }
    }
}
