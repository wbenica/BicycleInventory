import java.sql.*;

class DBController {

    // Fill in login credentials
    private static final String USER     = "wbenica";
    private static final String PASSWORD = "014707702";
    private static final String SERVER   = "jdbc:mysql://ambari-head.csc.calpoly.edu/";

    // Tables
    private static final String     CUSTOMERS = "Lab3_Customers";
    // Connection to the database, singleton
    private static       Connection connect;

    private static Connection getConnect ( ) {

        if ( connect == null ) {
            try {
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

    static void select ( ) {

        Connection conn = getConnect ( );

        try {
            ResultSet rs;
            Statement statement = conn.createStatement ( );
            rs = statement.executeQuery ( "SELECT * FROM " + CUSTOMERS );
            while ( rs.next ( ) ) {
                String custName = rs.getString ( 3 );
                System.out.println ( "Name: " + custName );
            }
        } catch ( SQLException e ) {
            e.printStackTrace ( );
        }
    }
}