package gui;

import bll.Playlist;
import bll.dbPlaylistManager;
import bll.dbSong;
import bll.dbSongManager;

import java.sql.SQLException;
import java.util.List;

public class dbPlaylistModel {
   private dbPlaylistManager dbplaylistManager;



    // TODO getallplaylist
    public List<Playlist> getALlPlaylists() throws SQLException {
        return dbplaylistManager.getALlPlaylists();
    }

    // TODO getsongfromplaylist
    public List<dbSong> getPlaylist(String name) throws SQLException {
        return dbplaylistManager.getPlaylist(name);
    }

    // TODO addPlaylist
    public void addPlaylist(String name) throws SQLException {
        dbplaylistManager.addPlaylist(name);
    }
    // TODO deletePlaylist
    public void deletePlaylist(String name) throws SQLException {
        dbplaylistManager.deletePlaylist(name);
    }
    // TODO addSongToPlaylist
    public void addSongToPlaylist(String playlistName,dbSong song) throws SQLException {
        dbplaylistManager.addSongToPlaylist(playlistName, song);
    }
    // TODO removeSongFromPlaylist
    public void removeSongFromPlaylist(String playlistName, dbSong song) throws SQLException {
        dbplaylistManager.removeSongFromPlaylist(playlistName, song);
    }
    // TODO editPlaylistName
    public void editPlaylistName(String oldName, String newName) throws SQLException {
        dbplaylistManager.editPlaylistName(oldName, newName);
    }

}
