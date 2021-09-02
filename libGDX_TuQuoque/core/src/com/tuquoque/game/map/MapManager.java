package com.tuquoque.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tuquoque.game.sprites.Player;
import com.tuquoque.game.utils.WorldCreator;

public class MapManager {
    private MapType currentMapType;
    private TiledMap currentMap;
    private final AssetManager assetManager;
    private final World world;
    private WorldCreator currentWorldCreator;
    private final Array<MapListener> mapListeners;

    public MapManager(AssetManager assetManager, World world){
        this.assetManager = assetManager;
        this.world = world;
        mapListeners = new Array<>();
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
        if(currentMapType == null || !mapType.getFilePath().equals(currentMapType.getFilePath())){
            //set current MapType
            currentMapType = mapType;

            //set currentMap
            if(assetManager.isLoaded(mapType.getFilePath()))
                currentMap = assetManager.get(mapType.getFilePath(), TiledMap.class);
            else
                Gdx.app.error(MapManager.class.getSimpleName(), mapType.getFilePath() + "not loaded in AssetManager");

            for(MapListener listener : mapListeners){
                listener.mapChanged();
            }

            //debug
            Gdx.app.log(MapManager.class.getSimpleName(), "Moving to new map: " + currentMapType.name());

            //destroy bodies of old map if there are
            if(currentWorldCreator != null){
                currentWorldCreator.selfDestroy();
            }
            currentWorldCreator = new WorldCreator(world, currentMap);
        }
    }

    public void playerAtSpawnMap(Player player){
        player.teleportTo(currentMapType.getSpawnCoordinates());
    }

    public void addMapListener(final MapListener mapListener) {
        mapListeners.add(mapListener);
    }

    public interface MapListener {
        void mapChanged();
    }
}
