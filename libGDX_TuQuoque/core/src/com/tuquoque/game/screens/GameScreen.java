package com.tuquoque.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.sprites.Border;
import com.tuquoque.game.sprites.Box;
import com.tuquoque.game.sprites.Player;


public class GameScreen extends AbstractScreen {


    private final Player playerB2D;
    private final Vector2 savedPlayerCoords = new Vector2(8, 4.5f);

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final OrthographicCamera camera;

    private final Vector2[] vertices;
    private final Border border;
    private Box box;
    private final int objectLayerId=6;
    private TiledMap map;
    private TiledMapTileLayer collisionObjectLayer;
    private MapObjects mapObject;

    float elapsedTime=0;

    // True = right, False = left
    private boolean direction = true;

    public GameScreen(final GameStarter context){
        super(context);
        this.camera = context.getCamera();
        world=context.getWorld();

        // map Vertices
        vertices=new Vector2[6];
        vertices[0]=new Vector2(-8,-4.5f);
        vertices[1]=new Vector2(-8,13.5f);
        vertices[2]=new Vector2(16,13.5f);
        vertices[3]=new Vector2(16, 11.5f);
        vertices[4]=new Vector2(37,11.5f);
        vertices[5]=new Vector2(37,-4.5f);

        border =new Border(vertices, world);

        //Create player
        playerB2D = new Player(world, savedPlayerCoords);

        //mapRenderer init
        mapRenderer = new OrthogonalTiledMapRenderer(null, 1/32f, batch);

    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
        mapRenderer.setMap(context.getAssetManager().get("map/prova.tmx", TiledMap.class));
        map = mapRenderer.getMap();
        collisionObjectLayer = (TiledMapTileLayer) map.getLayers().get(objectLayerId);
        mapObject = collisionObjectLayer.getObjects();

        for(RectangleMapObject rectangleMapObject: mapObject.getByType(RectangleMapObject.class)){
            Rectangle rectangle=rectangleMapObject.getRectangle();
            box =new Box(rectangle, world);
        }
    }

    private void handleInput(){
        //if WASD or ARROWS -> setSpeed to player
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            playerB2D.setSpeedX(playerB2D.NOMINAL_SPEED);
            direction=true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            playerB2D.setSpeedX(-playerB2D.NOMINAL_SPEED);
            direction=false;
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
    }

    @Override
    public void render(float delta) {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        //map render
        mapRenderer.setView(camera);
        mapRenderer.render();

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
        if(playerB2D.B2DBody.getLinearVelocity().x != 0 || playerB2D.B2DBody.getLinearVelocity().y != 0){
            if(direction)
                batch.draw((TextureRegion) playerB2D.getWalkRightAnimation().getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.65f,
                        playerB2D.B2DBody.getPosition().y -0.7f,
                        1.3f,1.6f);
            else
                batch.draw((TextureRegion) playerB2D.getWalkLeftAnimation().getKeyFrame(elapsedTime,true),
                        playerB2D.B2DBody.getPosition().x - 0.65f,
                        playerB2D.B2DBody.getPosition().y -0.7f,
                        1.3f,1.6f);
        }
        else{
            batch.draw((TextureRegion) playerB2D.getIdleRightAnimation().getKeyFrame(elapsedTime,true),
                    playerB2D.B2DBody.getPosition().x - 0.65f,
                    playerB2D.B2DBody.getPosition().y -0.7f,
                    1.3f,1.6f);
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
        mapRenderer.dispose();
    }
}
