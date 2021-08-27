package com.tuquoque.game.map;

public enum MapType {
    CITY("map/prova.tmx"),
    OUTSIDE("map/prova_old.tmx");

    private final String filePath;

    MapType(final String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
