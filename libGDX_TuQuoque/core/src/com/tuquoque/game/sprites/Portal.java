package com.tuquoque.game.sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.tuquoque.game.map.MapType;

import static com.tuquoque.game.GameStarter.UNIT_SCALE;

public class Portal {

    private BodyDef bdef;
    private Body body;
    private Rectangle rect;
    private String destinationStr;
    private MapType destinationMapType;

    public Portal(World world, TiledMap map, MapObject object) {
        bdef = new BodyDef();
        rect = ((RectangleMapObject) object).getRectangle();

        bdef.gravityScale=0;
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)* UNIT_SCALE,
                (rect.getY() + rect.getHeight() / 2)*UNIT_SCALE);
        body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth()/2*UNIT_SCALE, rect.getHeight()/2*UNIT_SCALE);
        body.createFixture(shape, 0f);
        body.setUserData("GROUND");

        shape.dispose();

        destinationStr = object.getName();
        destinationMapType = MapType.getMapTypeByName(destinationStr);
    }

}
