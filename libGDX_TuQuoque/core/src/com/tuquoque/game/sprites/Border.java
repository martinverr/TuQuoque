package com.tuquoque.game.sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Border {
    private final BodyDef mapBorders;
    private Body borders;
    private World world;

    public Border(Vector2[] vertices, World world){
        this.world=world;

        //mapBorders
        mapBorders=new BodyDef();
        mapBorders.position.set(8,4.5f);
        mapBorders.gravityScale=0;
        mapBorders.type= BodyDef.BodyType.StaticBody;

        FixtureDef fixtureDef=new FixtureDef();
        ChainShape chainShape=new ChainShape();
        chainShape.createLoop(vertices);
        fixtureDef.shape=chainShape;

        borders=world.createBody(mapBorders);
        borders.createFixture(fixtureDef);

    }
}
