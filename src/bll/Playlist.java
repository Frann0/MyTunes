package bll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Playlist {
    private String name;
    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private final MediaManager mediaManager;

    /**
     * Vores constructor, som tager et navn ind på den playliste vi vil lave.
     * @param name
     */
    public Playlist(String name, MediaManager mediaManager) {
        this.name = name;
        this.mediaManager = mediaManager;
    }

    /**
     * Getter for navnet på playlisten
     * @return navnet på playlisten
     */
    public String getName() {
        return name;
    }

    /**
     * setter for navnet på playlisten
     * @param name det nye navn på playlisten
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for alle sang elementer der er i den nuværende playliste.
     * @return en observable liste med type Song.
     */
    public ObservableList<Song> getSongs() {
        return songs;
    }



    /**
     * Tilføjer en sang til vores liste.
     * @param song Den sang der skal tilføjes
     */
    public void addSong(Song song) {
        songs.add(song);
        //System.out.println(song.getSongName());
    }

    /**
     * fjerner sangen fra listen.
     * @param song sangen der skal fjernes
     */
    public void removeSong(Song song) {
        songs.remove(song);
        if (mediaManager.getPlayOrder().contains(song))
        mediaManager.getPlayOrder().remove(song);
    }

    public StringProperty getTotalDuration(){
        StringProperty time = new SimpleStringProperty();
        long millis = 0;
        for (Song s : songs){
            millis += TimeUnit.SECONDS.toMillis(s.getDurationInSeconds());
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - (hours * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - (minutes * 60);
        time.set(hours + ":" + minutes + ":" + seconds);
        return time;

    }

    /**
     * overrider toString metoden. Bliver egentligt ikke brugt.
     * brugte den til at prøve at få lidt padding på top baren.
     * @return navnet på playlisten.
     */
    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
