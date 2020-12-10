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
    private ObservableList<dbSong> allSongs;
    private dbSong oldSong;

    public dbSongModel() throws SQLException {
        dbsongManager = new dbSongManager();
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(dbsongManager.getAllSongs());
    }


    public void addSong(dbSong song) throws SQLException {
        dbsongManager.addSong(song);
        updateSongList();
    }

    public void setOldSong(dbSong oldSong) {
        this.oldSong = oldSong;
    }

    public dbSong getOldSong() {
        return oldSong;
    }

    public ObservableList<dbSong> getSongs() {
        return allSongs;
    }


    public void deleteSong(dbSong song) throws SQLException {
        allSongs.remove(song);
        dbsongManager.deleteSong(song);
        updateSongList();
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
