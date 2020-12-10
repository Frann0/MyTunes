package bll;

import dal.dbPlaylistDAO;

import java.sql.SQLException;
import java.util.List;

public class dbPlaylistManager {
    private dbPlaylistDAO dbplaylistDAO;

    public dbPlaylistManager(){
        dbplaylistDAO = new dbPlaylistDAO();
    }


    public List<Playlist> getALlPlaylists() throws SQLException {
        return dbplaylistDAO.getAllPlaylists();
    }


    public List<dbSong> getPlaylist(String name) throws SQLException {
        return dbplaylistDAO.getPlaylist(name);
    }


    public void addPlaylist(String name) throws SQLException {
        dbplaylistDAO.addPlaylist(name);
    }

    public void deletePlaylist(String name) throws SQLException {
        dbplaylistDAO.deletePlaylist(name);
    }


    public void addSongToPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistDAO.addSongToPlaylist(playlistName, song);
    }

    // TODO removeSongFromPlaylist
    public void removeSongFromPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistDAO.removeSongFromPlaylist(playlistName, song);
    }
    // TODO editPlaylistName
    public void editPlaylistName(String oldName, String newName) throws SQLException {
        dbplaylistDAO.editPlaylistName(oldName, newName);
    }

    public List<String> getPlaylistNames() throws SQLException {
        return dbplaylistDAO.getPlaylistNames();
    }


}
