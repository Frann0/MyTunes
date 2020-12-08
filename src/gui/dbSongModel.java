package gui;

import bll.Song;
import bll.dbSong;
import bll.dbSongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class dbSongModel {
    dbSongManager dbsongManager;
    private ObservableList<dbSong> songs;
    private Song oldSong;

    public dbSongModel() throws SQLException {
        dbsongManager = new dbSongManager();
        songs = FXCollections.observableArrayList();
        songs.addAll(dbsongManager.getAllSongs());
    }


    public void addSong(Song song) throws SQLException {
        dbsongManager.addSong(song);
    }

    public void setOldSong(Song oldSong) {
        this.oldSong = oldSong;
    }

    public Song getOldSong() {
        return oldSong;
    }

    public ObservableList<dbSong> getSongs() {
        return songs;
    }


    public void deleteSong(Song song) throws SQLException {
        songs.remove(song);
        dbsongManager.deleteSong(song);
    }

    public void updateSong(dbSong newSong) throws SQLException {
        dbsongManager.updateSong(newSong);
        updateSongList();
    }

    private void updateSongList() throws SQLException {
        songs.clear();
        songs.addAll(dbsongManager.getAllSongs());
    }

}
