package bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PlaylistHandler {
    private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }

    public void addPlaylist(String name){
        playlists.add(new Playlist(name));
    }

    public void removePlaylist(int index){
        playlists.remove(index);
    }

    public void updatePlaylist(int index, String name){
        playlists.get(index).setName(name);
    }

}
