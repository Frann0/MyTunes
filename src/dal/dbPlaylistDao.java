package dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbPlaylistDao {

    private MyDatabaseConnector databaseConnector;

    public dbPlaylistDao(){
        databaseConnector = new MyDatabaseConnector();
    }

    public void addPlaylist(String name) throws SQLException {
        try (Connection con = databaseConnector.getConnection()) {
            //String sql = "INSERT INTO Song " + "Values ('" + songTitle + "', '" + category + "'," + durationInSeconds + ", '" + filePath + ;

            PreparedStatement pSql = con.prepareStatement("INSERT INTO PlaylistList VALUES(?)");
            pSql.setString(1, name);
            pSql.execute();
        }
    }


    public static void main(String[] args) throws SQLException {
        dbPlaylistDao db = new dbPlaylistDao();
        db.addPlaylist("Test1");
    }

}
