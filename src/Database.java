import java.sql.Connection;
import java.sql.DriverManager;

class DBController {

    // Fill in login credentials
    private static final String USER     = "";
    private static final String PASSWORD = "";
    private static final String SERVER   = "jdbc:mysql://ambari-head.csc.calpoly.edu/";

    // Connection to the database, singleton
    private static Connection connect;

    private static Connection getConnect ( ) {

        if ( connect == null ) {
            try {
                Class.forName ( "com.mysql.jdbc.Driver" );
                connect = DriverManager.getConnection (
                        SERVER + USER + "?user=" + USER + "&password=" + PASSWORD
                );
                connect.setAutoCommit ( false );
            } catch ( Exception e ) {
                System.out.println ( e.getMessage ( ) );
            }
        }

        return connect;
    }
}