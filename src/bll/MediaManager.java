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
    private double defaultvolume = 1;
    private double currentvolume = 1;
    private boolean isPause = true;
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
     *           Sætter også volumen til 1, dvs den normale volume
     */
    public void setMedia(Media me) {
        mediaPlayer = new MediaPlayer(me);
        mediaPlayer.setOnReady(() -> {
            double millis = me.getDuration().toMillis();
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);
            long minutes = seconds/60;
            durationProperty.set(minutes + ":" + seconds);
            mediaPlayer.setVolume(defaultvolume);
            isPause = false;
        });
    }

    /**
     * gets the current time of the song
     */
    public void currentTime(){
        if(mediaPlayer != null) {
            double millis = mediaPlayer.getCurrentTime().toMillis();
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);
            long minutes = seconds / 60;
            X.set(minutes + ":" + seconds);
        }
    }

    /**
     * sætter musiken på pause
     */
    public void pause() {
        if (mediaPlayer != null && isPause == false){
            mediaPlayer.pause();
        }
    }

    /**
     * starter sangen efter den er sat på pause
     */
    public void resume() {
        if (mediaPlayer != null && isPause == true) {
            mediaPlayer.play();
        }
    }

    /**
     * stopper sangen, således at den starter fra begyndelsen igen, hvis genstartet
     */
    public void stop(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Ændret, men ikke testet, virker formentligt. i stedet for at bruge mute metoden, bruges setvolume
     * som sætter volumen til 0.
     * håndtere når vi muter sangen.
     * @param mute hvorvidt den er mutet eller ej.
     */
    public void setMute(boolean mute){
        if (mediaPlayer != null)
            if(mute == true) {
                mediaPlayer.setVolume(0);
                currentvolume = 0;
            }
        if (mute == false) {
            mediaPlayer.setVolume(1);
            currentvolume = 1;
        }
    }

    /**
     * Sætter volumen af sangen når vi bruger slideren.
     * @param volume Værdien fra slideren.
     */
    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
        currentvolume = volume;
    }

    /**
     * hæver volumen med 10%
     */
    public void increaseVolume() {
        double raise = currentvolume + 0.1;
        mediaPlayer.setVolume(raise);
        currentvolume = raise;
    }

    /**
     * sænker volumen med 10%
     */
    public void lowerVolume() {
        double lower = currentvolume - 0.1;
        mediaPlayer.setVolume(lower);
        currentvolume = lower;
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
