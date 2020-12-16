package dal;

import bll.dbSong;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class dbSongDAO {

    private final MyDatabaseConnector databaseConnector;

    public dbSongDAO() {
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Return a list of all songs in the database, converted into dbSong objects.
     * @return
     * @throws SQLException
     */
    public List<dbSong> getAllSongs() throws SQLException {
        List<dbSong> allSongs = new ArrayList<>();
        Media m = new Media(new File("src/Resources/buffer.mp3").toURI().toString());
        MediaPlayer mp = new MediaPlayer(m);

        Connection con = databaseConnector.getConnection();
        String sql = "SELECT * FROM Song;";

        Statement statement = con.createStatement();

        try {
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();

                while (resultSet.next()) {
                    String songTitle = resultSet.getString("songtitle");
                    String category = resultSet.getString("category");
                    String duration = resultSet.getString("duration");
                    String filePath = resultSet.getString("filepath");
                    String artist = resultSet.getString("artist");
                    allSongs.add(new dbSong(songTitle, category, duration, filePath, artist));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return allSongs;
    }

    /**
     * Delete all playlist associations to song in the database.
     * @param song
     * @throws SQLException
     */
    public void removeSongFromAllPlaylists(dbSong song) throws SQLException {

        String songId = "";
        String filePath = song.getFilePath();

        try(Connection con = databaseConnector.getConnection()){

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Song");

            if(pSql.execute()){
                ResultSet resultSet = pSql.getResultSet();

                while(resultSet.next()){
                    if(resultSet.getString("filepath").equals(filePath)){
                        songId = resultSet.getString("id");
                    }
                }
            }

            PreparedStatement pSql2 = con.prepareStatement("DELETE FROM Playlist WHERE SongId=?");
            pSql2.setString(1, songId);
            pSql2.execute();
        }
    }

    /**
     * Add a new song to the database in accordance with the database column structure.
     * The injection uses prepared statement.
     *
     * @param song
     * @throws SQLException
     */
    public void addSong(dbSong song) {
        MediaPlayer mp = new MediaPlayer(new Media(song.getFilePath()));
        mp.setOnReady(() -> {
            String songTitle = song.getTitle();
            String genre = song.getGenre();
            String duration = song.getDurationString();
            String filePath = song.getFilePath();
            String artist = song.getArtist();

            try (Connection con = databaseConnector.getConnection()) {

                PreparedStatement pSql = con.prepareStatement("INSERT INTO Song VALUES(?,?,?,?,?)");
                pSql.setString(1, songTitle);
                pSql.setString(2, genre);
                pSql.setString(3, duration);
                pSql.setString(4, filePath);
                pSql.setString(5, artist);

                pSql.execute();
            } catch (SQLServerException throwables) {
                throwables.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    /**
     * Delete a song from the Song table in the database, IF the database subject is by the
     * specified artist, and has the specified song title.
     *
     * @param song
     * @throws SQLException
     */
    public void deleteSong(dbSong song) throws SQLException {
        removeSongFromAllPlaylists(song);
        String title = song.getTitle();
        String artist = song.getArtist();

        try (Connection con = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Song;";

            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                PreparedStatement pSql = con.prepareStatement("DELETE FROM Song WHERE Songtitle=? AND Artist=?");
                pSql.setString(1,title);
                pSql.setString(2,artist);
                //String deleteSql = "DELETE FROM Song WHERE Songtitle='" + title + "' AND Artist='" + artist + "'";
                //Statement deleteStatement = con.createStatement();
                pSql.execute();

            }

        }
    }

    /**
     * With reference to the song's filepath, this method will update the song's
     * title, genre and artist in the database.
     * @param newSong
     * @throws SQLException
     */
    public void editSong(dbSong newSong) throws SQLException {
        String Title = newSong.getTitle();
        String Genre = newSong.getGenre();
        String Path = newSong.getFilePath();
        String Artist = newSong.getArtist();

        try (Connection con = databaseConnector.getConnection()) {

            Statement statement = con.createStatement();
            statement.addBatch("UPDATE Song SET Songtitle= '" + Title + "' WHERE Filepath= '" + Path + "'");
            statement.addBatch("UPDATE Song SET Artist= '" + Artist + "' WHERE Filepath= '" + Path + "'");
            statement.addBatch("UPDATE Song SET Category= '" + Genre + "' WHERE Filepath= '" + Path + "'");

            statement.executeBatch();

            if (statement != null) {
                statement.close();
            }
        }
    }

}
