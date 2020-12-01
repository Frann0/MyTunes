package bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    }

    /**
     * fjerner sangen fra listen.
     * @param song sangen der skal fjernes
     */
    public void removeSong(Song song) {
        songs.remove(song);
        mediaManager.getPlayOrder().remove(song);
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
