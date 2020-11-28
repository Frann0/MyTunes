package bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Playlist {
    private String name;
    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private final ArrayList<Song> playOrder = new ArrayList<>();

    /**
     * Vores constructor, som tager et navn ind på den playliste vi vil lave.
     * @param name
     */
    public Playlist(String name) {
        this.name = name;
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
     * Getter for vores nuværende playorder.
     * @return Arrayliste for vores playorder.
     */
    public ArrayList<Song> getPlayOrder() {
        return playOrder;
    }

    /**
     * Tilføjer en sang til vores liste.
     * @param song Den sang der skal tilføjes
     */
    public void addSong(Song song) {
        songs.add(song);
        playOrder.add(song);
    }

    /**
     * fjerner sangen fra listen.
     * @param song sangen der skal fjernes
     */
    public void removeSong(Song song) {
        songs.remove(song);
        playOrder.remove(song);
    }

    /**
     * Shuffler vores playorder liste, så sangene er på en tilfældig plads.
     * @param shuffleActive håndtere om playorderen skal shuffles eller ej.
     */
    public void shuffle(boolean shuffleActive){
        playOrder.clear();
        playOrder.addAll(songs);
        if(shuffleActive) {
            Collections.shuffle(playOrder);
        }
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
