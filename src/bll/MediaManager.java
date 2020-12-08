package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MediaManager {
    private MediaPlayer mediaPlayer;
    private final ObservableList<Song> playOrder = FXCollections.observableArrayList();
    private final ObservableList<Song> unShuffledPlayOrder = FXCollections.observableArrayList();
    private double duration = 0;
    private StringProperty durationProperty = new SimpleStringProperty("");
    private StringProperty currentTimeProperty = new SimpleStringProperty("");
    private Playlist currentPlaylist;

    public MediaManager(Playlist currentPlaylist){
        this.currentPlaylist = currentPlaylist;
    }

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
        mediaPlayer.setOnEndOfMedia(()->{
            setMedia(me);
        });
    }

    /**
     * TODO VIRKER IKKE PT.
     * håndtere når vi muter sangen.
     * @param mute hvorvidt den er mutet eller ej.
     */
    public void setMute(boolean mute){
        if (mediaPlayer != null)
            mediaPlayer.setMute(mute);
    }

    /**
     * Sætter volumen af sangen når vi bruger slideren.
     * @param volume Værdien fra slideren.
     */
    public void setVolume(double volume) {
        if (volume <= 0){
            setMute(true);
        } else{
            setMute(false);
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Getter for vores nuværende playorder.
     * @return Arrayliste for vores playorder.
     */
    public ObservableList<Song> getPlayOrder() {
        return playOrder;
    }

    /**
     * getter vores unshuffleable playorder. så vi kan returnere til vores tidligere
     * playorder hvis shuffle er deaktiveret.
     * @return observablelist for vores unshuffleable playorder.
     */
    public ObservableList<Song> getUnShuffledPlayOrder() {
        return unShuffledPlayOrder;
    }

    /**
     * Shuffler vores playorder liste, så sangene er på en tilfældig plads.
     * @param shuffleActive håndtere om playorderen skal shuffles eller ej.
     */
    public void shuffle(boolean shuffleActive){
        if (currentPlaylist != null) {
            playOrder.clear();
            playOrder.addAll(unShuffledPlayOrder);
            if (shuffleActive) {
                Collections.shuffle(playOrder);
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

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }
}
