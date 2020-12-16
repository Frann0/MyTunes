package bll;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final String name;
    private final List<dbSong> songs;

    /**
     * Constructs a playlist with a list of dbSongs.
     * @param name
     * @param playlist
     */
    public Playlist(String name, List<dbSong> playlist){
        this.name = name;
        songs = playlist;

    }

    /**
     * Returns the name of the playlist.
     * @return Name of the playlist.
     */
    public String getName() {
        return name;
    }

    /**
     * Overwritten toString method.
     * @return name of playlist.
     */
    @Override
    public String toString() {
        return String.format("%s", getName());
    }

}
