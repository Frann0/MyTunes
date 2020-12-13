package bll;

import gui.MyTunesController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaManager {
    public IntegerProperty index = new SimpleIntegerProperty();
    private MediaPlayer mediaPlayer;
    private final ObservableList<Song> playOrder = FXCollections.observableArrayList();
    private final ObservableList<Song> unShuffledPlayOrder = FXCollections.observableArrayList();

    private double currentvolume = 1;
    private boolean repeatActive = false;

    private StringProperty durationProperty = new SimpleStringProperty("");
    private List<dbSong> currentPlaylist;

    private MyTunesController controller;

    public MediaManager(){
    }

    public void setController(MyTunesController controller){
        this.controller = controller;
    }

    /**
     * Sætter sætter det medie der skal afspilles på, og laver en ny mediaPlayer ud fra det.
     * @param path pathen for filen der skal afspilles
     *           Sætter også volumen til 1, dvs den normale volume
     */
    public void setMedia(String path) {
        Media me = new Media(path);
        mediaPlayer = new MediaPlayer(me);
        mediaPlayer.setOnReady(() -> {
            double millis = me.getDuration().toMillis();
            long seconds = TimeUnit.MILLISECONDS.toSeconds((long) millis);
            long minutes = seconds/60;
            durationProperty.set(minutes + ":" + seconds);
            mediaPlayer.setVolume(currentvolume);
        });
        mediaPlayer.setOnEndOfMedia(() -> {

            if (!repeatActive) {
                if (!currentPlaylist.isEmpty()) {
                    currentPlaylist.remove(0);
                    setMedia(currentPlaylist.get(0).getFilePath());
                    index.set(index.get() + 1);
                    try {
                        controller.selectNextSong();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    resume();
                }
            } else{
                setMedia(currentPlaylist.get(0).getFilePath());
                try {
                    controller.selectNextSong();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                resume();
            }
        });
    }


    /**
     * sætter musiken på pause
     */
    public void pause() {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    /**
     * starter sangen efter den er sat på pause
     */
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void setRepeatActive(boolean repeatActive) {
        this.repeatActive = repeatActive;
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
        double v = map(0,100,0,1,volume);
        mediaPlayer.setVolume(v);
        currentvolume = v;
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


    public void setCurrentPlaylist(List<dbSong> Playlist, int index) {
        this.index.set(index);
        this.currentPlaylist = Playlist.subList(index,Playlist.size());
    }

    /**
     * Mapper et tal mellem a1 og a2 til et nyt tal mellem b1 og b2.
     * @param a1    fra vores normale værdi
     * @param a2    til vores normale værdi
     * @param b1    fra ny værdi
     * @param b2    til ny værdi
     * @param s     input værdi mellem a1 og a2
     * @return      Returnere en værdi mellem b1 og b2 fra mellem a1 og a2.
     *              F.eks bruger vi funktionen til at mappe vores volumesliders værdi som går
     *              mellem 0-100 til vores mediaplayers set volume som går mellem 0 og 1.
     */
    private double map(double a1, double a2, double b1, double b2, double s){
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}