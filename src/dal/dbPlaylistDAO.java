package dal;

import bll.Playlist;
import bll.dbSong;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbPlaylistDAO {

    private List<Playlist> allPlaylists;

    private MyDatabaseConnector databaseConnector;

    public dbPlaylistDAO(){
        databaseConnector = new MyDatabaseConnector();
        allPlaylists = new ArrayList<>();
    }

    /**
     * Create a new playlist in the database.
     * @param name
     * @throws SQLException
     */
    public void addPlaylist(String name) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO PlaylistList VALUES(?)");
            pSql.setString(1, name);
            pSql.execute();
        }
    }

    /**
     * Delete playlist from the database, specified by the parameter name.
     * @param name
     * @throws SQLException
     */
    public void deletePlaylist(String name) throws SQLException {

        try(Connection con = databaseConnector.getConnection()){

            PreparedStatement pSql = con.prepareStatement("DELETE FROM PlaylistList WHERE PlName=?");
            pSql.setString(1, name);
            pSql.execute();

        }
    }

    /**
     * Changes the name of the playlist selected in the GUI from oldName to newName.
     * @param oldName
     * @param newName
     * @throws SQLException
     */
    public void editPlaylistName(String oldName, String newName) throws SQLException {
        try(Connection con = databaseConnector.getConnection()){

            PreparedStatement pSql = con.prepareStatement("UPDATE PlaylistList SET PlName=? WHERE PlName=?");
            pSql.setString(1, newName);
            pSql.setString(2, oldName);
            pSql.execute();

            PreparedStatement pSql2 = con.prepareStatement("UPDATE Playlist SET PlaylistName=? Where PlaylistName=?");
            pSql2.setString(1, newName);
            pSql2.setString(2, oldName);
            pSql2.execute();

        }
    }

    /**
     * Returns a list of all dbSongs associated with the playlist's name.
     * @param name
     * @return
     * @throws SQLException
     */
    public List<dbSong> getPlaylist(String name) throws SQLException {

        ArrayList<String> songIds = new ArrayList<>();
        List<dbSong> playlist = new ArrayList<>();

        try(Connection con = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Playlist;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    if (resultSet.getString("playlistname").equals(name)) {
                        songIds.add(resultSet.getString("songid"));
                    }
                }
            }

            String sql2 = "SELECT * FROM Song;";
            Statement statement2 = con.createStatement();

            if (statement.execute(sql2)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    for(int i = 0; i < songIds.size(); i++){
                        if (resultSet.getString("id").equals(songIds.get(i))){
                            String title = resultSet.getString("songtitle");
                            String genre = resultSet.getString("category");
                            String duration = resultSet.getString("duration");
                            String filepath = resultSet.getString("filepath");
                            String artist = resultSet.getString("artist");

                            playlist.add(new dbSong(title, genre, duration, filepath, artist));
                        }
                    }
                }
            }
        }
        return playlist;
    }

    /**
     * Returns a list of all playlists in the database.
     * @return
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists() throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        List<String> playlistNames = new ArrayList<>();


        try(Connection con = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Playlist;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    if (!playlistNames.contains(resultSet.getString("playlistname"))) {
                        playlistNames.add(resultSet.getString("playlistname"));
                    }
                }
            }
        }

        for (int i = 0; i < playlistNames.size(); i++){
            List<dbSong> songs = getPlaylist(playlistNames.get(i));
            playlists.add(new Playlist(playlistNames.get(i), songs));
        }
        return playlists;
    }

    /**
     * Removes the selected song from the selected playlist.
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void removeSongFromPlaylist(String playlistName, dbSong song) throws SQLException {
        String songID = "";

        try(Connection con = databaseConnector.getConnection()){

            String sql = "SELECT * FROM Song;";
            Statement statement = con.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                while(resultSet.next()){
                    if(resultSet.getString("filepath").equals(song.getFilePath())){
                        songID = resultSet.getString("id");
                    }
                }
            }
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Playlist WHERE SongId=? AND PlaylistName=?");
            pSql.setString(1, songID);
            pSql.setString(2, playlistName);
            pSql.execute();
        }
    }

    /**
     * Adds a song to a playlist. An association is created between a playlist name,
     * and a songId in the database.
     * @param playlistName
     * @param song
     * @throws SQLException
     */
    public void addSongToPlaylist(String playlistName, dbSong song) throws SQLException {
        String songID = "";

        try(Connection con = databaseConnector.getConnection()){

            String sql = "SELECT * FROM Song;";
            Statement statement = con.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                while(resultSet.next()){
                    if(resultSet.getString("filepath").equals(song.getFilePath())){
                        songID = resultSet.getString("id");
                    }
                }
            }

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Playlist VALUES(?,?)");
            pSql.setString(1, playlistName);
            pSql.setString(2, songID);
            pSql.execute();
        }
    }

    /**
     * Returns a list of all playlist names in the database.
     * @return
     * @throws SQLException
     */
    public List<String> getPlaylistNames() throws SQLException {

        List<String> playlistNames = new ArrayList<>();

        try(Connection con = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM PlaylistList;";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                        playlistNames.add(resultSet.getString("PlName"));
                    }
                }
            }
        return playlistNames;
        }

}
