package com.tuquoque.game.sprites;

import com.badlogic.gdx.physics.box2d.*;

public class Box {

    private final Body body;
    private World world;

    public Box(com.badlogic.gdx.math.Rectangle rectangle, World world) {
        this.world=world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(rectangle.getX(),rectangle.getY());
        bodyDef.gravityScale=0;
        bodyDef.type= BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef=new FixtureDef();
        PolygonShape boxShape=new PolygonShape();
        boxShape.setAsBox(rectangle.getWidth(), rectangle.getHeight() );
        fixtureDef.shape=boxShape;

        body= world.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }
}
