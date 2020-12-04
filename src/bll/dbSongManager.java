package bll;

import dal.dbSongDAO;

import java.sql.SQLException;
import java.util.List;

public class dbSongManager {
    dbSongDAO dbmySongDAO;

    public dbSongManager(){
        dbmySongDAO = new dbSongDAO();
    }

    public void addSong(Song song) throws SQLException {
        dbmySongDAO.addSong(song);
    }

    public List<dbSong> getAllSongs() throws SQLException {
        return dbmySongDAO.getAllSongs();
    }

    public void deleteSong(String title, String artist) throws SQLException {
        dbmySongDAO.deleteSong(title, artist);
    }

    public void updateSong(dbSong song){
        dbmySongDAO.editSong(song);
    }

}
