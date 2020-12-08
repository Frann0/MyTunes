package bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PlaylistHandler {
    private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    /**
     * getter funktion for at få alle playlister der er lavet.
     * @return en liste af playlister, som eksistere pt.
     */
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Tilføjer en ny playliste.
     * @param name navnet på den nye playliste der skal laves.
     */
    public void addPlaylist(String name, MediaManager mediaManager){
        //playlists.add(new Playlist(name, mediaManager, songs));
    }

    /**
     * fjerner playlisten fra listen af nuværende playlister.
     * @param index fjerner playlisten med det given index
     */
    public void removePlaylist(int index){
        playlists.remove(index);
    }

    /**
     * Opdatere vores playliste, ved at sætte en ny title på den.
     * @param index indexed på den valgte playliste.
     * @param name det nye navn på playlisten.
     * @param name det nye navn på playlisten.
     */
    public void updatePlaylist(int index, String name){
        playlists.get(index).setName(name);
    }

}
