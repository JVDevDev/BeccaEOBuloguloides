package main;


import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
    private AudioClip clip;

    public static final Sound music = new Sound("/music.wav");
    public static final Sound hitEffect = new Sound("/dano.wav");
    public static final Sound hitEnemy = new Sound("/danoInimigo.wav");
    public static final Sound shoot = new Sound("/shoot.wav");
    public static final Sound municao = new Sound("/municao.wav");
    public static final Sound vida = new Sound("/vida.wav");

    private Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(name));
        }catch(Throwable e) {}
    }

    public void play() {
        try {
            new Thread() {
                public void run() {
                    clip.play();
                }
            }.start();
        }catch(Throwable e) {}
    }

    public void loop() {
        try {
            new Thread() {
                public void run() {
                    clip.loop();
                }
            }.start();
        }catch(Throwable e) {}
    }
}
