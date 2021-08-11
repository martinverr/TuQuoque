package com.tuquoque.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tuquoque.game.GameStarter;

import static com.tuquoque.game.GameStarter.*;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public GameScreen(final GameStarter context){
        super(context);

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //CIRCLE
        bodyDef.position.set(8, 9);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();

        //BOX
        bodyDef.position.set(8.9f, 4);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.3f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_CIRCLE | BIT_GROUND;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();

        //PLATFORM
        bodyDef.position.set(8f, 2);
        bodyDef.gravityScale = 0;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(7.9f, 0.5f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            context.setScreen(ScreenType.LOADING);
        }

        viewport.apply();
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
