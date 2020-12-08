package gui;

import bll.Song;
import bll.dbSong;
import bll.dbSongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class dbSongModel {
    dbSongManager dbsongManager;
    //private ObservableList<dbSong> songs;
    private ObservableList<Song> allSongs;
    private Song oldSong;

    public dbSongModel() throws SQLException {
        dbsongManager = new dbSongManager();
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(dbsongManager.getAllSongs());
    }


    public void addSong(dbSong song) throws SQLException {
        dbsongManager.addSong(song);
    }

    public void setOldSong(Song oldSong) {
        this.oldSong = oldSong;
    }

    public Song getOldSong() {
        return oldSong;
    }

    public ObservableList<Song> getSongs() {
        return allSongs;
    }


    public void deleteSong(Song song) throws SQLException {
        allSongs.remove(song);
        dbsongManager.deleteSong(song);
    }

    public void updateSong(dbSong newSong) throws SQLException {
        dbsongManager.updateSong(newSong);
        updateSongList();
    }

    private void updateSongList() throws SQLException {
        allSongs.clear();
        allSongs.addAll(dbsongManager.getAllSongs());
    }

}
