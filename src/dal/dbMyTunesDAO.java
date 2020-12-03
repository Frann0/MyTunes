package dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;


import dal.db.MyDatabaseConnector;

import javax.swing.plaf.nimbus.State;
import java.sql.*;


public class dbMyTunesDAO {

    private MyDatabaseConnector databaseConnector;

    public dbMyTunesDAO(){databaseConnector = new MyDatabaseConnector();}

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


    public void addSong(String songTitle, String category, int durationInSeconds, String filePath, String artist) throws SQLException {

        try(Connection con = databaseConnector.getConnection()) {
            //String sql = "INSERT INTO Song " + "Values ('" + songTitle + "', '" + category + "'," + durationInSeconds + ", '" + filePath + ;

            PreparedStatement pSql = con.prepareStatement("INSERT INTO Song VALUES(?,?,?,?,?)");
            pSql.setString(1,songTitle);
            pSql.setString(2,category);
            pSql.setInt(3,durationInSeconds);
            pSql.setString(4,filePath);
            pSql.setString(5, artist);

            pSql.execute();

        }
    }

    /**
     * Delete a song from the Song table in the database, IF the database subject is by the
     * specified artist, and has the specified song title.
     * @param title
     * @param artist
     * @throws SQLException
     */
    public void deleteSong(String title, String artist) throws SQLException {

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






    public static void main(String[] args) throws SQLException {
        dbMyTunesDAO dbmyTunesDAO = new dbMyTunesDAO();
        //dbmyTunesDAO.addSong("Jingle", "Rock", 250, "c:/file2", "Bob Dylan");
        //dbmyTunesDAO.addSong("Yeah Man", "Beatles", 1950);
        //dbmyTunesDAO.addSong("Disco Tango", "Joey Moe", 1950);

        //dbmyTunesDAO.deleteSong("Jingle", "Bob Dylan");

        //System.out.println(dbmyTunesDAO.getNextAvailableSongId());
    }

}
