package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Sound {

    Clip clip;
    public static final Sound music = new Sound("res/music.wav");
    public static final Sound hitEffect = new Sound("res/dano.wav");
    public static final Sound hitEnemy = new Sound("res/danoInimigo.wav");
    public static final Sound shoot = new Sound("res/shoot.wav");
    public static final Sound municao = new Sound("res/municao.wav");
    public static final Sound vida = new Sound("res/vida.wav");

    private Sound(String name) {
        try {
            File file = new File(name);
            if (file.exists()){
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
            }else{
                throw new RuntimeException("Sound: file not found: " + name);
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e){
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e){
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}