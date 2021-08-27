package com.tuquoque.game.sprites;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static com.tuquoque.game.GameStarter.UNIT_SCALE;

public class BoxAndWall {

    private BodyDef bdef;
    private FixtureDef fdef;
    private Body body;
    private  Rectangle rect;

    public BoxAndWall(World world, TiledMap map, MapObject object){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        rect = ((RectangleMapObject) object).getRectangle();

        bdef.gravityScale=0;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)* UNIT_SCALE,
                    (rect.getY() + rect.getHeight() / 2)*UNIT_SCALE);
        body = world.createBody(bdef);
        //debug
        // System.out.println("body created: " + bdef.position.x + ";" + bdef.position.y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth()/2*UNIT_SCALE, rect.getHeight()/2*UNIT_SCALE);
        fdef.shape = shape;
        body.createFixture(fdef);
        body.setUserData("GROUND");
        }
}
