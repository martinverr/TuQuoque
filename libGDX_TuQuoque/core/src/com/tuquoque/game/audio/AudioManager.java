package com.tuquoque.game.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;

public class AudioManager {
    private AudioType currentMusicType;
    private Music currentMusic;
    private final AssetManager assetManager;

    private ArrayList<AudioType> loopSoundsPlaying_at;
    private ArrayList<LoopingSound> loopSoundsPlaying_ls;

    public AudioManager(final AssetManager assetManager){
        this.assetManager = assetManager;
        currentMusicType = null;
        currentMusic = null;

        loopSoundsPlaying_at = new ArrayList<>();
        loopSoundsPlaying_ls = new ArrayList<>();
    }

    public void playAudio(AudioType audioType){
        switch(audioType.getTypeOfSound()){
            case MUSIC:
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
                break;

            case SOUND:
                assetManager.get(audioType.getFilePath(), Sound.class).play(audioType.getVolume());
                break;

            case LOOPINGSOUND:
                if(!loopSoundsPlaying_at.contains(audioType)){
                    LoopingSound loopingSound = new LoopingSound(audioType, assetManager);

                    loopSoundsPlaying_at.add(audioType);
                    loopSoundsPlaying_ls.add(loopingSound);
                    loopingSound.play();

                    //debug
                    System.out.println("loopSoundsPlaying_at: " + loopSoundsPlaying_at.size());
                    System.out.println("loopSoundsPlaying_ls: " + loopSoundsPlaying_ls.size());
                    System.out.println("now play: " + loopingSound.getAudioType().name() + "(" + loopingSound + ")\n");
                }
        }
    }

    public void stopCurrentMusic(){
        if(currentMusic != null){
            currentMusic.stop();
            currentMusicType = null;
            currentMusic = null;
        }
    }

    public void stopLoopingSound(AudioType audioType){
        int index = loopSoundsPlaying_at.indexOf(audioType);
        if(index != -1){
            //debug
            System.out.println("now stop: " + loopSoundsPlaying_ls.get(index).getAudioType().name() + "(" + loopSoundsPlaying_ls.get(index) + ")");

            loopSoundsPlaying_ls.get(index).stop();
            loopSoundsPlaying_at.remove(index);
            loopSoundsPlaying_ls.remove(index);

            //debug
            System.out.println("loopSoundsPlaying_at: " + loopSoundsPlaying_at.size());
            System.out.println("loopSoundsPlaying_ls: " + loopSoundsPlaying_ls.size() + "\n");

        }
    }
}


class LoopingSound{
    private Sound sound;
    private AudioType audioType;
    private long soundId;

    public LoopingSound(AudioType audioType, AssetManager assetManager) {
        this.audioType = audioType;
        this.sound =  assetManager.get(audioType.getFilePath(), Sound.class);
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public AudioType getAudioType() {
        return audioType;
    }

    void play(){
        soundId = sound.play();
        sound.setLooping(soundId, true);
        sound.setVolume(soundId, audioType.getVolume());
    }

    void stop(){
        sound.stop();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final LoopingSound other = (LoopingSound) obj;

        return this.sound.equals(other.sound);
    }
}