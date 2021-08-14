package com.tuquoque.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.tuquoque.game.sprites.Border;
import com.tuquoque.game.sprites.BoxAndWall;


public class WorldCreator {
    public WorldCreator(World world, TiledMap map){

        for (MapObject object : map.getLayers().get("Solids").getObjects().getByType(RectangleMapObject.class)){
            new BoxAndWall(world, map, object);
        }

        for (PolygonMapObject object : map.getLayers().get("Solids").getObjects().getByType(PolygonMapObject.class)){
            new Border(world, map, object);
        }
    }
}
