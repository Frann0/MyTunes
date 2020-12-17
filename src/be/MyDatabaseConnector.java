package be;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

public class MyDatabaseConnector {

    private SQLServerDataSource dataSource;

    public MyDatabaseConnector(){
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunes2000");
        dataSource.setUser("CSe20A_23");
        dataSource.setPassword("DiscoDucks");
        dataSource.setPortNumber(1433);
    }

    /**
     * Establish a connection to the database.
     * @return the connection to the data source.
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }


}
