package com.tuquoque.game.audio;

public enum AudioType {
    THEME1("audio/background-theme-1.wav",true, 0.5f),
    THEME2("audio/background-theme-2.wav", true, 0.5f),
    AMBIENT_PALATINO("audio/background-ambient-palatino.wav", true, 0.5f),
    CLICK1_LIGHT("audio/click_light.wav", false, 0.5f),
    CLICK2_HEAVY("audio/click_heavy.wav", false, 0.5f),
    CLICK3_SUCCESS("audio/click_success.wav", false, 0.5f),
    FOOTSTEPS_STONE("audio/footsteps/footsteps-stone-merged.wav",false,1f),
    FOOTSTEPS_CLEAN("audio/footsteps/clean-footstep.wav",false,0.5f);

    private final String filePath;
    private final boolean isMusic;
    private final float volume;

    AudioType(final String filePath, final boolean isMusic, final  float volume){
        this.filePath = filePath;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public float getVolume() {
        return volume;
    }
}
