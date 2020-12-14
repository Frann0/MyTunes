package bll;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private final List<dbSong> songs;

    /**
     * Constructs a playlist with the specified name.
     * @param name
     */
    public Playlist(String name) {
        this.name = name;
        songs = new ArrayList<>();
    }

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
     * Sets the name of the playlist.
     * @param name the new name of the playlist.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a list of all songs in the playlist.
     * @return observable list of type Song.
     */
    public ObservableList<Song> getSongs() {
        return null;
    }



    /**
     * Add a song to the playlist.
     * @param song , the song to be added.
     */
    public void addSong(dbSong song) {
        songs.add(song);
    }

    /**
     * Removes the song from the playlist.
     * @param song , the song to be removed.
     */
    public void removeSong(dbSong song) {
        songs.remove(song);

    }

    /*public StringProperty getTotalDuration(){
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

    }*/

    /**
     * Overwritten toString method.
     * @return name of playlist.
     */
    @Override
    public String toString() {
        return String.format("%s", getName());
    }

    /**
     * Add the songs in a playlist to a string.
     * @return string of all songs.
     */
    public String printPlaylist(){
        String name = this.name;
        String songs = "";
        for (dbSong song : this.songs){
            songs += song.getTitle() + " ,";
        }
        return name + songs;
    }
}
