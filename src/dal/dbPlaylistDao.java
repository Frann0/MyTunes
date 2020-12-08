package dal;

import bll.Playlist;
import bll.dbSong;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbPlaylistDao {

    private List<Playlist> allPlaylists;

    private MyDatabaseConnector databaseConnector;

    public dbPlaylistDao(){
        databaseConnector = new MyDatabaseConnector();
        allPlaylists = new ArrayList<>();
    }

    public void addPlaylist(String name) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            //String sql = "INSERT INTO Song " + "Values ('" + songTitle + "', '" + category + "'," + durationInSeconds + ", '" + filePath + ;

            PreparedStatement pSql = con.prepareStatement("INSERT INTO PlaylistList VALUES(?)");
            pSql.setString(1, name);
            pSql.execute();
        }
    }


    public void deletePlaylist(String name) throws SQLException {

        try(Connection con = databaseConnector.getConnection()){

            PreparedStatement pSql = con.prepareStatement("DELETE FROM PlaylistList WHERE PlName=?");
            pSql.setString(1, name);
            pSql.execute();

        }
    }

    public void editPlaylistName(String oldName, String newName) throws SQLException {
        try(Connection con = databaseConnector.getConnection()){

            PreparedStatement pSql = con.prepareStatement("UPDATE PlaylistList SET PlName=? WHERE PlName=?");
            pSql.setString(1, newName);
            pSql.setString(2, oldName);
            pSql.execute();

        }
    }


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

    // TODO
    public void getAllPlaylists(){
        List<Playlist> allPlaylists = new ArrayList<>();

    }

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


    public static void main(String[] args) throws SQLException {
        dbPlaylistDao db = new dbPlaylistDao();
        db.addPlaylist("Test1");
    }

}
