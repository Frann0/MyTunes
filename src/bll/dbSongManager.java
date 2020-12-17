package bll;

import be.dbSong;
import dal.dbSongDAO;
import java.sql.SQLException;
import java.util.List;

public class dbSongManager {
    dbSongDAO dbmySongDAO;

    public dbSongManager(){
        dbmySongDAO = new dbSongDAO();
    }

    /**
     * Adds "song" to the database.
     * @param song
     * @throws SQLException
     */
    public void addSong(dbSong song) throws SQLException {
        dbmySongDAO.addSong(song);
    }

    /**
     * Retrieves a list of all songs in the database.
     * @return
     * @throws SQLException
     */
    public List<dbSong> getAllSongs() throws SQLException {

        List<dbSong> allDbSongs = dbmySongDAO.getAllSongs();
        return allDbSongs;
    }

    /**
     * Deletes "song" from the database.
     * @param song
     * @throws SQLException
     */
    public void deleteSong(dbSong song) throws SQLException {
        dbmySongDAO.deleteSong(song);
    }

    /**
     * Updates the song info of a selected song in the database.
     * @param newSong
     * @throws SQLException
     */
    public void updateSong(dbSong newSong) throws SQLException {
        dbmySongDAO.editSong(newSong);
    }

}
