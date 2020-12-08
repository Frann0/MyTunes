package bll;

import dal.dbSongDAO;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbSongManager {
    dbSongDAO dbmySongDAO;

    public dbSongManager(){
        dbmySongDAO = new dbSongDAO();
    }

    public void addSong(dbSong song) throws SQLException {
        dbmySongDAO.addSong(song);
    }

    public List<dbSong> getAllSongs() throws SQLException {
        List<dbSong> allDbSongs = dbmySongDAO.getAllSongs();

        return allDbSongs;


    }

    public void deleteSong(dbSong song) throws SQLException {
        dbmySongDAO.deleteSong(song);
    }

    public void updateSong(dbSong newSong) throws SQLException {
        dbmySongDAO.editSong(newSong);
    }

}
