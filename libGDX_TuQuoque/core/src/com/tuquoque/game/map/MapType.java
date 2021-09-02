package com.tuquoque.game.map;

import com.badlogic.gdx.math.Vector2;

public enum MapType {
    CITY("map/prova.tmx", 11, 14),
    OUTSIDE("map/outside.tmx", 183, 182);

    private final String filePath;
    private final float x;
    private final float y;

    MapType(final String filePath, final float x, final float y) {
        this.filePath = filePath;
        this.x = x;
        this.y = y;
    }

    public String getFilePath() {
        return filePath;
    }

    public Vector2 getSpawnCoordinates(){
        return new Vector2(x, y);
    }
}

