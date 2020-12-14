package bll;

import dal.dbPlaylistDAO;

import java.sql.SQLException;
import java.util.List;

public class dbPlaylistManager {
    private dbPlaylistDAO dbplaylistDAO;

    public dbPlaylistManager(){
        dbplaylistDAO = new dbPlaylistDAO();
    }

    /**
     * Get a list of all playlists in the database.
     * @return
     * @throws SQLException
     */
    public List<Playlist> getALlPlaylists() throws SQLException {
        return dbplaylistDAO.getAllPlaylists();
    }

    /**
     * Get the songs in the specified playlist from the database.
     * @param name
     * @return
     * @throws SQLException
     */
    public List<dbSong> getPlaylist(String name) throws SQLException {
        return dbplaylistDAO.getPlaylist(name);
    }

    /**
     * Add a playlist to the database.
     * @param name
     * @throws SQLException
     */
    public void addPlaylist(String name) throws SQLException {
        dbplaylistDAO.addPlaylist(name);
    }

    /**
     * Delete the playlsit from the database.
     * @param name
     * @throws SQLException
     */
    public void deletePlaylist(String name) throws SQLException {
        dbplaylistDAO.deletePlaylist(name);
    }

    /**
     * Add "song" the the playlist "playlistName".
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void addSongToPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistDAO.addSongToPlaylist(playlistName, song);
    }

    /**
     * Remove "song" from playlist "playlistName".
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void removeSongFromPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistDAO.removeSongFromPlaylist(playlistName, song);
    }

    /**
     * Updates playlist name from "oldName" to "newName".
     * @param oldName
     * @param newName
     * @throws SQLException
     */
    public void editPlaylistName(String oldName, String newName) throws SQLException {
        dbplaylistDAO.editPlaylistName(oldName, newName);
    }

    /**
     * Get a list of strings with all playlist names.
     * @return
     * @throws SQLException
     */
    public List<String> getPlaylistNames() throws SQLException {
        return dbplaylistDAO.getPlaylistNames();
    }


}
