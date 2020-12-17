package gui.model;

import be.Playlist;
import bll.dbPlaylistManager;
import be.dbSong;

import java.sql.SQLException;
import java.util.List;

public class dbPlaylistModel {
    private final dbPlaylistManager dbplaylistManager = new dbPlaylistManager();

    /**
     * Returns a list of all playlists.
     * @return
     * @throws SQLException
     */
    public List<Playlist> getALlPlaylists() throws SQLException {
        return dbplaylistManager.getALlPlaylists();
    }

    /**
     * Returns a list of dbSongs related to the selected playlist name.
     * @param name
     * @return
     * @throws SQLException
     */
    public List<dbSong> getPlaylist(String name) throws SQLException {
        return dbplaylistManager.getPlaylist(name);
    }

    /**
     * Add a playlist to the database using the parameter name.
     * @param name
     * @throws SQLException
     */
    public void addPlaylist(String name) throws SQLException {
        dbplaylistManager.addPlaylist(name);
    }

    /**
     * Deletes the playlist specified by name in the database.
     * @param name
     * @throws SQLException
     */
    public void deletePlaylist(String name) throws SQLException {
        dbplaylistManager.deletePlaylist(name);
    }

    /**
     * Creates an association between playlistName and song in the database.
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void addSongToPlaylist(String playlistName,dbSong song) throws SQLException {
        dbplaylistManager.addSongToPlaylist(playlistName, song);
    }

    /**
     * Removes the association in the database between the playlistName and song.
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void removeSongFromPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistManager.removeSongFromPlaylist(playlistName, song);
    }

    /**
     * Updates the name of a playlist in the database.
     * @param oldName
     * @param newName
     * @throws SQLException
     */
    public void editPlaylistName(String oldName, String newName) throws SQLException {
        dbplaylistManager.editPlaylistName(oldName, newName);
    }

    /**
     * Returns a list of all the playlist names in the db.
     * @return
     * @throws SQLException
     */
    public List<String> getPlaylistNames() throws SQLException {
        return dbplaylistManager.getPlaylistNames();
    }

}
