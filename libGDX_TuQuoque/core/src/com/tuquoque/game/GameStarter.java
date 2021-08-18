package com.tuquoque.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tuquoque.game.audio.AudioManager;
import com.tuquoque.game.input.InputManager;
import com.tuquoque.game.screens.ScreenType;

import java.util.EnumMap;

public class GameStarter extends Game {
	//Game vars
	public static final float UNIT_SCALE = 1/32f;
	private EnumMap<ScreenType, Screen> screenAvailable = new EnumMap<ScreenType, Screen>(ScreenType.class);
	private OrthographicCamera camera;
	private FitViewport viewport;
	private SpriteBatch batch;


	//box2D vars and constants
	private float accumulator;
	private static final float FIXED_TIME_STAMP = 1/60f;
	public static final short BIT_CIRCLE = 1;
	public static final short BIT_BOX= 1 << 1;
	public static final short BIT_GROUND = 1 << 2;
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;

	//assetManager
	private AssetManager assetManager;

	//other manager
	private AudioManager audioManager;
	private InputManager inputManager;

	@Override
	public void create () {
		//box2d stuff
		Box2D.init();
		world = new World(new Vector2(0,-9.81f), true);
		box2DDebugRenderer = new Box2DDebugRenderer();

		// Invisible hitboxes
		//box2DDebugRenderer.setDrawBodies(false);
		accumulator = 0;

		//Camera, viewport, batch
		camera = new OrthographicCamera(16, 9);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
		viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
		batch = new SpriteBatch();

		//AssetManager
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(assetManager.getFileHandleResolver()));

		//AudioManager
		audioManager = new AudioManager(assetManager);

		//input
		inputManager= new InputManager();
		Gdx.input.setInputProcessor(new InputMultiplexer(inputManager));

		setScreen(ScreenType.MAINMENU);
	}

	public void setScreen(final ScreenType screenType){
		//screen conterrà lo Screen di tipo screenType dagli screenAvailable, oppure null se non ancora creato
		final Screen screen = screenAvailable.get(screenType);

		if(screen == null){
			//si crea lo Screen di tipo screenType
			try{
				final Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), GameStarter.class).newInstance(this);
				screenAvailable.put(screenType, newScreen);
				setScreen(newScreen);
			} catch (ReflectionException error){
				throw new GdxRuntimeException("Lo Screen " + screenType + "non è stato creato per: ", error);
			}
		}
		else
			//si usa lo Screen già presente
			setScreen(screen);
	}


	/**
	 * Getter of World
	 */
	public World getWorld() {
		return world;
	}

	/**
	* Getter of camera
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Getter of viewport
	 */
	public FitViewport getViewport() {
		return viewport;
	}

	/**
	 * Getter of batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Getter of B2DDebugRenderer
	 */
	public Box2DDebugRenderer getBox2DDebugRenderer(){
		return box2DDebugRenderer;
	}

	/**
	 * Getter of assetManager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Getter of inputManager
	 */
	public InputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Getter of inputManager
	 */
	public AudioManager getAudioManager() {
		return audioManager;
	}

	/**
	* Each frame will update World of Box2D of a fixed timestamp
	*/
	@Override
	public void render() {
		super.render();

		//Timestamp fixed for B2D simulation
		accumulator += Math.min(0.25f, Gdx.graphics.getDeltaTime());
		while(accumulator>=FIXED_TIME_STAMP){
			world.step(FIXED_TIME_STAMP, 6, 2);
			accumulator -= FIXED_TIME_STAMP;
		}

		//final float alpha = accumulator/FIXED_TIME_STAMP;
	}

	@Override
	public void dispose(){
		super.dispose();
		world.dispose();
		box2DDebugRenderer.dispose();
	}
}
