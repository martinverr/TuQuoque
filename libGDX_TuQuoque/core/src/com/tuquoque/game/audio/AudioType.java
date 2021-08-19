package com.tuquoque.game.audio;

enum TypeOfSound {
    //Only one can be played at time; looping; instance of Gdx.audio.Music; should be used for background > 2 sec
    MUSIC,
    //Different and same sound can be played at the same time; not looping; instance of Gdx.audio.Sound; should be used for effects <2 sec
    SOUND,
    //Different sound can be played at the same time, not the same; looping; instance of Gdx.audio.Sound; should be used for looping effects <2 sec
    LOOPINGSOUND;
}

public enum AudioType {
    THEME1("audio/background-theme-1.wav",TypeOfSound.MUSIC, 0.5f),
    THEME2("audio/background-theme-2.wav", TypeOfSound.MUSIC, 0.5f),
    AMBIENT_PALATINO("audio/background-ambient-palatino.wav", TypeOfSound.MUSIC, 0.5f),
    CLICK1_LIGHT("audio/click_light.wav", TypeOfSound.SOUND, 0.5f),
    CLICK2_HEAVY("audio/click_heavy.wav", TypeOfSound.SOUND, 0.5f),
    CLICK3_SUCCESS("audio/click_success.wav", TypeOfSound.SOUND, 0.5f),
    FOOTSTEPS_STONE("audio/footsteps/footsteps-stone-merged.wav",TypeOfSound.LOOPINGSOUND,1f),
    FOOTSTEPS_CLEAN("audio/footsteps/clean-footstep.wav",TypeOfSound.LOOPINGSOUND,0.5f);

    private final String filePath;
    private final TypeOfSound typeOfSound;
    private final float volume;

    AudioType(final String filePath, final TypeOfSound typeOfSound, final  float volume){
        this.filePath = filePath;
        this.typeOfSound = typeOfSound;
        this.volume = volume;
    }

    public String getFilePath() {
        return filePath;
    }

    public TypeOfSound getTypeOfSound() {
        return typeOfSound;
    }

    public boolean isMusic(){
        return typeOfSound == TypeOfSound.MUSIC;
    }

    public float getVolume() {
        return volume;
    }
}
