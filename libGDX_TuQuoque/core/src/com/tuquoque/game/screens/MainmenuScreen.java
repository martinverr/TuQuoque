package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.utils.MovingTexture;


public class MainmenuScreen extends AbstractScreen {
    private final float BG_WIDTH = 22;
    private final float BG_HEIGHT = 12;
    private final float PLAY_BUTT_WIDTH = 5;
    private final float PLAY_BUTT_HEIGHT = 1.3F;
    private final float SETT_BUTT_WIDTH = 5.5F;
    private final float SETT_BUTT_HEIGHT = 0.75F;


    private MovingTexture BG;
    private Texture texture_playbutton_active;
    private Texture texture_playbutton_inactive;
    private Texture texture_settingsbutton_active;
    private Texture texture_settingsbutton_inactive;

    private Sound clickButtonSound;
    private Sound playButtonSound;
    private Music themeMusic;

    private Vector3 coordsPointed;

    private final OrthographicCamera camera;

    /*
    * Constructor of Screen->Abstract Screen->LoadingScreen
    * <p>
    * Create instances of Objects used in create() and render()
    *
    * @param context    Instance of GameStarter
    */
    public MainmenuScreen(final GameStarter context){
        super(context);
        //Others
        camera = context.getCamera();
        coordsPointed = new Vector3();

        //texture
        BG = new MovingTexture(Gdx.files.internal("background/cesaricidio.jpg"), -1f, -1f, -0.5f, -0.5f);
        BG.setWidth(32);
        BG.setHeight(18);
        texture_playbutton_active = new Texture(Gdx.files.internal("buttons/playbutton_red.png"));
        texture_playbutton_inactive = new Texture(Gdx.files.internal("buttons/playbutton_lightgrey.png"));
        texture_settingsbutton_active = new Texture(Gdx.files.internal("buttons/settingsbutton_red.png"));
        texture_settingsbutton_inactive = new Texture(Gdx.files.internal("buttons/settingsbutton_lightgrey.png"));

        //Sound and Music
        clickButtonSound = Gdx.audio.newSound(Gdx.files.internal("audio/click_heavy.wav"));
        playButtonSound = Gdx.audio.newSound(Gdx.files.internal("audio/click_success.wav"));
        themeMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/background-theme-1.wav"));
        themeMusic.setLooping(true);
    }

    /*
     * Every time this Screen is shown (after pause() or other Screen gamestate
     *  clear OpenGL screen
     *  resume themeMusic
     */
    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        themeMusic.play();
        BG.resume();
    }

    /*
     * Every new frame render() is called
     *
     * Render what will be seen in the screen
     *
     * @param delta time occurred from last frame rendered
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        //get coords of mouse in coordsPointed
        camera.update();
        coordsPointed.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(coordsPointed);

        batch.begin();

        //draw moving background
        BG.updatePosition(16, 9, 1);
        batch.draw(BG, BG.getX(), BG.getY(), 16*2, 9*2);

        //draw texture play button
        batch.draw(texture_playbutton_inactive, 8-PLAY_BUTT_WIDTH/2, 1, PLAY_BUTT_WIDTH, PLAY_BUTT_HEIGHT);
        if(coordsPointed.x > 8-PLAY_BUTT_WIDTH/2 && coordsPointed.x < 8+PLAY_BUTT_WIDTH/2 &&
                coordsPointed.y < 1 + PLAY_BUTT_HEIGHT && coordsPointed.y > 1){
            batch.draw(texture_playbutton_active, 8-PLAY_BUTT_WIDTH/2, 1, PLAY_BUTT_WIDTH, PLAY_BUTT_HEIGHT);
            if(Gdx.input.justTouched()){
                BG.stop();
                context.setScreen(ScreenType.LOADING);
                themeMusic.pause();
                clickButtonSound.play();
                playButtonSound.play();
            }
        }

        //draw settings button
        batch.draw(texture_settingsbutton_inactive, 8-SETT_BUTT_WIDTH/2, 2.5f, SETT_BUTT_WIDTH, SETT_BUTT_HEIGHT);
        if(coordsPointed.x > 8-SETT_BUTT_WIDTH/2 && coordsPointed.x < 8 + SETT_BUTT_WIDTH/2 &&
                coordsPointed.y < 2.5f + SETT_BUTT_HEIGHT && coordsPointed.y > 2.5f) {
            batch.draw(texture_settingsbutton_active, 8 - SETT_BUTT_WIDTH / 2, 2.5f, SETT_BUTT_WIDTH, SETT_BUTT_HEIGHT);
            if (Gdx.input.justTouched()) {
                clickButtonSound.play();
                context.setScreen(ScreenType.SETTINGS);
            }
        }
        batch.end();


        //if SPACE pressed, set screen 'LOADING'
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            BG.stop();
            context.setScreen(ScreenType.LOADING);
            themeMusic.pause();
            playButtonSound.play();
        }
    }

    /*
    * Every time the window is resized, resize() is called
    * <p>
    * update viewport with new size
    *
    * @param width  New width of the window
    * @param height New height of the window
    */
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

    /*
    * Before a pause() or exit(), dispose no-more used Objects that Java Garbage Collector doesn't secure
    */
    @Override
    public void dispose() {
        //Sound and Music
        clickButtonSound.dispose();
        playButtonSound.dispose();
        themeMusic.dispose();

        //Texture
        BG.dispose();
        texture_playbutton_inactive.dispose();
        texture_playbutton_active.dispose();
        texture_settingsbutton_active.dispose();
        texture_settingsbutton_inactive.dispose();
    }
}
