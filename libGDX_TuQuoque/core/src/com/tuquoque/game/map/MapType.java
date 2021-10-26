package com.tuquoque.game.map;

import com.badlogic.gdx.math.Vector2;

public enum MapType {
    CITY("map/prova.tmx", 9.5f, 14, "Città, entrata Sud-Ovest"),
    OUTSIDE("map/outside.tmx", 183.5f, 182, "Fuori città Sud-Ovest");

    private final String filePath;
    private final float x;
    private final float y;
    private final String description;

    MapType(final String filePath, final float x, final float y, final String description) {
        this.filePath = filePath;
        this.x = x;
        this.y = y;
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public Vector2 getSpawnCoordinates(){
        return new Vector2(x, y);
    }

    public String getDescription() {
        return description;
    }

    public static MapType getMapTypeByName(String MapTypeName) {
        for (MapType mapType : MapType.values()){
            if (mapType.name().equals(MapTypeName)){
                return mapType;
            }
        }
        return null;
    }
}

