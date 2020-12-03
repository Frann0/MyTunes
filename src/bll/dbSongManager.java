package bll;

import dal.dbMyTunesDAO;

import java.sql.SQLException;

public class dbSongManager {
    dbMyTunesDAO dbmyTunesDAO;

    public dbSongManager(){
        dbmyTunesDAO = new dbMyTunesDAO();
    }

    public void addSong(String title, String category, int durationInSeconds, String filepath, String artist) throws SQLException {
        dbmyTunesDAO.addSong(title, category, durationInSeconds, filepath, artist);
    }

}
