package com.tuquoque.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;


public class WorldCreator {
    private final World world;
    private Array<Body> bodies;

    public WorldCreator(World world, TiledMap map){
        this.world=world;
        bodies = new Array<>();

        for (MapObject object : map.getLayers().get("Solids").getObjects().getByType(RectangleMapObject.class)){
            new BoxAndWall(world, map, object);
        }

        for (PolygonMapObject object : map.getLayers().get("Solids").getObjects().getByType(PolygonMapObject.class)){
            new Border(world, map, object);
        }

        for (MapObject object : map.getLayers().get("portals").getObjects().getByType(RectangleMapObject.class)){
            new Portal(world, map, object);
        }
}

    public void selfDestroy(){
        world.getBodies(bodies);
        int deleted = 0;
        int max = world.getBodyCount();
        for(Body body : bodies){
            if(body.getUserData() != null && body.getUserData().equals("mapObject")){
                world.destroyBody(body);
                deleted++;
            }
        }
        Gdx.app.debug(this.getClass().getSimpleName(), deleted + " out of " + max + "; remained " + world.getBodyCount());
    }
}
