package dal;

import bll.Song;
import bll.dbSong;


import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class dbSongDAO {

    private MyDatabaseConnector databaseConnector;

    public dbSongDAO(){databaseConnector = new MyDatabaseConnector();}

    /**
     * Return a list of all songs in the database, converted into dbSong objects.
     * @return
     * @throws SQLException
     */
    public List<dbSong> getAllSongs() throws SQLException {
        List<dbSong> allSongs = new ArrayList<>();

        try(Connection con = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Song;";

            Statement statement = con.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                while(resultSet.next()){
                    String songTitle = resultSet.getString("songtitle");
                    String category = resultSet.getString("category");
                    String duration = resultSet.getString("duration");
                    String filePath = resultSet.getString("filepath");
                    String artist = resultSet.getString("artist");
                    allSongs.add(new dbSong(songTitle, category, duration, filePath, artist));
                }
            }
        }
        return allSongs;
    }

    // Do we even need this????? :O LOLOLOLOL
    public int getNextAvailableSongId() throws SQLException {
        try(Connection con = databaseConnector.getConnection()){
            int id = -1 ;
            String sql = "SELECT Id FROM Song;";

            Statement statement = con.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();

                while(resultSet.next()){
                    id = resultSet.getInt("id");
                }

            }
            if(id == -1){
                return id;
            } else{
                return id +1;
            }
        }
    }

    /**
     * Add a new song to the database in accordance with the database column structure.
     * The injection uses prepared statement.
     * @param song
     * @throws SQLException
     */
    public void addSong(Song song) throws SQLException {

        String songTitle = song.getSongName();
        String genre = song.getGenre();
        String duration = song.getDuration().getValue();
        String filePath = song.getPath();
        String artist = song.getArtist();

        try(Connection con = databaseConnector.getConnection()) {
            //String sql = "INSERT INTO Song " + "Values ('" + songTitle + "', '" + category + "'," + durationInSeconds + ", '" + filePath + ;

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Song VALUES(?,?,?,?,?)");
            pSql.setString(1,songTitle);
            pSql.setString(2,genre);
            pSql.setString(3,duration);
            pSql.setString(4,filePath);
            pSql.setString(5, artist);

            pSql.execute();

        }
    }

    /**
     * Delete a song from the Song table in the database, IF the database subject is by the
     * specified artist, and has the specified song title.
     * @param song
     * @throws SQLException
     */
    public void deleteSong(Song song) throws SQLException {
        String title = song.getSongName();
        String artist = song.getArtist();

        try(Connection con = databaseConnector.getConnection()){

            String sql = "SELECT Id FROM Song;";

            Statement statement = con.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();



                String deleteSql = "DELETE FROM Song WHERE Songtitle='" + title + "' AND Artist='" + artist + "'";
                //String deleteSql = "DELETE FROM Song WHERE Id=" + 1;
                Statement deleteStatement = con.createStatement();
                deleteStatement.execute(deleteSql);

                }

            }
        }

    public void editSong(dbSong newSong) throws SQLException {
        String Title = newSong.getTitle();
        String Genre = newSong.getGenre();
        String Path = newSong.getFilePath();
        String Artist = newSong.getArtist();

        try(Connection con = databaseConnector.getConnection()){
            /*String SqlString = "UPDATE Song " +
                    "SET Songtitle=? , Category=? , Artist=? " +
                    "WHERE Filepath=?";*/

            Statement statement = con.createStatement();
            statement.addBatch("UPDATE Song SET Songtitle= '" + Title + "' WHERE Filepath= '" + Path + "'");
            statement.addBatch("UPDATE Song SET Artist= '" + Artist + "' WHERE Filepath= '" + Path + "'");
            statement.addBatch("UPDATE Song SET Category= '" + Genre + "' WHERE Filepath= '" + Path + "'");

            statement.executeBatch();

            if(statement != null){
                statement.close();
            }

        }

        }






    public static void main(String[] args) throws SQLException {
        dbSongDAO dbmySongDAO = new dbSongDAO();
        //dbmyTunesDAO.addSong("Jizzle", "Rock", 250, "c:/file2", "Bob Dylan");
        //dbmyTunesDAO.addSong("Jingle", "Disco", 250, "c:/file1", "Tarzan");
        //dbmyTunesDAO.addSong("YOLO", "Punk", 250, "c:/file3", "Bojack");
        //dbmyTunesDAO.addSong("Skit", "Classical", 250, "c:/file4", "Frann");

        //dbmyTunesDAO.deleteSong("Jingle", "Bob Dylan");
        List<dbSong> allSongs = dbmySongDAO.getAllSongs();
        for(dbSong song : allSongs){
            System.out.println(song);
        }

        //System.out.println(dbmyTunesDAO.getNextAvailableSongId());
    }

}
