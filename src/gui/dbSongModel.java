package gui;

import bll.dbSongManager;

import java.sql.SQLException;

public class dbSongModel {
    dbSongManager dbsongManager;

    public dbSongModel(){
        dbsongManager = new dbSongManager();
    }

    // TODO her skal observable list med sange ligge

    public void addSong(String title, String category, int durationInSeconds, String filepath, String artist) throws SQLException {
        dbsongManager.addSong(title, category, durationInSeconds, filepath, artist);
    }
}
