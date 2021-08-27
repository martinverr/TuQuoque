package com.tuquoque.game.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.tuquoque.game.utils.WorldCreator;

public class MapManager {
    private MapType currentMapType;
    private TiledMap currentMap;
    private final AssetManager assetManager;
    private final World world;
    private WorldCreator currentWorldCreator;


    public MapManager(AssetManager assetManager, World world){
        this.assetManager = assetManager;
        this.world = world;
        currentMapType = null;
        currentMap = null;
    }

    public MapType getCurrentMapType() {
        return currentMapType;
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public void loadMap(MapType mapType) {
        currentMapType = mapType;
        currentMap = assetManager.get(mapType.getFilePath(), TiledMap.class);

        if(currentWorldCreator != null){
            currentWorldCreator.selfDestroy();
        }
        currentWorldCreator = new WorldCreator(world, currentMap);
    }
}
