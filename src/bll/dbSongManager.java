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

    public void deleteSong(Song song) throws SQLException {
        dbmySongDAO.deleteSong(song);
    }

    public void updateSong(dbSong newSong) throws SQLException {
        dbmySongDAO.editSong(newSong);
    }

}
