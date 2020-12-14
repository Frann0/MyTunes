package gui;

import bll.dbSong;
import bll.dbSongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class dbSongModel {
    dbSongManager dbsongManager;
    private ObservableList<dbSong> allSongs;
    private dbSong oldSong;

    public dbSongModel() throws SQLException {
        dbsongManager = new dbSongManager();
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(dbsongManager.getAllSongs());
    }

    /**
     * Adds a song to the database, and updates the contents of allSongs.
     * @param song
     * @throws SQLException
     */
    public void addSong(dbSong song) throws SQLException {
        dbsongManager.addSong(song);
        updateSongList();
    }

    /**
     * Sets old song to the song selected for edit in the GUI. This oldSong is
     * used as a reference for updating the song info.
     * @param oldSong
     */
    public void setOldSong(dbSong oldSong) {
        this.oldSong = oldSong;
    }

    /**
     * Returns oldSong. Used by EditSongViewController.
     * @return
     */
    public dbSong getOldSong() {
        return oldSong;
    }

    /**
     * Returns an observable list of allSongs.
     * @return
     */
    public ObservableList<dbSong> getSongs() {
        return allSongs;
    }

    /**
     * Deletes song from the database and allSongs, then updates the
     * contents of allSongs.
     * @param song
     * @throws SQLException
     */
    public void deleteSong(dbSong song) throws SQLException {
        allSongs.remove(song);
        dbsongManager.deleteSong(song);
        updateSongList();
    }

    /**
     * Updates a song's info in the database.
     * @param newSong
     * @throws SQLException
     */
    public void updateSong(dbSong newSong) throws SQLException {
        dbsongManager.updateSong(newSong);
        updateSongList();
    }

    /**
     * Updates the content of allSongs.
     * @throws SQLException
     */
    private void updateSongList() throws SQLException {
        allSongs.clear();
        allSongs.addAll(dbsongManager.getAllSongs());
    }
}
