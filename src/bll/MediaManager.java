package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaManager {
    private MediaPlayer mediaPlayer;
    private double duration = 0;
    private StringProperty durationProperty = new SimpleStringProperty("");
    private StringProperty currentTimeProperty = new SimpleStringProperty("");

    /**
     * håndtere at pause sangen.
     * @param paused hvor vidt den er pauset eller ej.
     */
    public void play(boolean paused){
        if (paused) {
            mediaPlayer.play();
        }else{
            mediaPlayer.pause();
        }
    }

    /**
     * Sætter sætter det medie der skal afspilles på, og laver en ny mediaPlayer ud fra det.
     * @param me mediet der skal afspilles.
     */
    public void setMedia(Media me) {
        mediaPlayer = new MediaPlayer(me);
        mediaPlayer.setOnReady(() -> {
            double millis = me.getDuration().toMillis();
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);
            long minutes = seconds/60;
            durationProperty.set(minutes + ":" + seconds);
        });
    }

    /**
     * TODO VIRKER IKKE PT.
     * håndtere når vi muter sangen.
     * @param mute hvorvidt den er mutet eller ej.
     */
    public void setMute(boolean mute){
        if (mediaPlayer != null) {
            if (mute) {
                mediaPlayer.setMute(true);
            } else {
                mediaPlayer.setMute(false);
            }
        }
    }

    /**
     * Sætter volumen af sangen når vi bruger slideren.
     * @param volume Værdien fra slideren.
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            if (volume <= 0) {
                mediaPlayer.setMute(true);
            } else {
                mediaPlayer.setMute(false);
                mediaPlayer.setVolume(volume);
            }
        }
    }

    /**
     * getter for duration af sangen.
     * @return længden af sangen.
     */
    public StringProperty getDuration(){
        return durationProperty;
    }
}
