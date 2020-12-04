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

    public dbSongModel() throws SQLException {
        dbsongManager = new dbSongManager();
        songs = FXCollections.observableArrayList();
        songs.addAll(dbsongManager.getAllSongs());
    }


    public void addSong(Song song) throws SQLException {
        dbsongManager.addSong(song);
    }

    public ObservableList<dbSong> getSongs() {
        return songs;
    }

    /*
    public void deleteSong(String title, String genre, int duration, String filePath, String artist) throws SQLException {
        dbSong song = new dbSong(title, genre, duration, filePath, artist);
        songs.remove(song);
        dbsongManager.deleteSong(title, artist);
    }*/

    public void updateSong(dbSong song) throws SQLException {
        dbsongManager.updateSong(song);
        updateSongList();
    }

    private void updateSongList() throws SQLException {
        songs.clear();
        songs.addAll(dbsongManager.getAllSongs());
    }

}
