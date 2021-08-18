package com.tuquoque.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tuquoque.game.GameStarter;
import com.tuquoque.game.audio.AudioManager;
import com.tuquoque.game.input.InputManager;

public abstract class AbstractScreen implements Screen {
   protected final GameStarter context;
   protected final FitViewport viewport;
   protected World world;
   protected final Box2DDebugRenderer box2DDebugRenderer;
   protected final SpriteBatch batch;
   protected final InputManager inputManager;
    protected final AudioManager audioManager;

   public AbstractScreen(final GameStarter context){
       this.context = context;
       viewport = context.getViewport();
       batch = context.getBatch();
       this.world = context.getWorld();
       this.box2DDebugRenderer = context.getBox2DDebugRenderer();
       this.inputManager = context.getInputManager();
       this.audioManager = context.getAudioManager();
   }

    @Override
    public void resize(int width, int height) {
       viewport.update(width, height);
    }

}
