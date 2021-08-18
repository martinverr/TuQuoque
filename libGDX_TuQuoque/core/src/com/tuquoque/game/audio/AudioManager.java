package com.tuquoque.game.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private AudioType currentMusicType;
    private Music currentMusic;
    private final AssetManager assetManager;

    public AudioManager(final AssetManager assetManager){
        this.assetManager = assetManager;
        currentMusicType = null;
        currentMusic = null;
    }

    public void playAudio(AudioType audioType){
        if(audioType.isMusic()){
            if(currentMusicType == audioType){
                return;
            }

            if(currentMusic != null){
                currentMusic.stop();
            }

            currentMusicType = audioType;
            currentMusic = assetManager.get(audioType.getFilePath(), Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(audioType.getVolume());
            currentMusic.play();

        }
        else{
            assetManager.get(audioType.getFilePath(), Sound.class).play(audioType.getVolume());
        }
    }

    public void stopCurrentMusic(){
        if(currentMusic != null){
            currentMusic.stop();
            currentMusicType = null;
            currentMusic = null;
        }
    }
}
