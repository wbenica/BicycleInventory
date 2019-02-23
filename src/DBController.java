import java.sql.*;

class DBController {

    // Fill in login credentials
    private static final String USER     = "wbenica";
    private static final String PASSWORD = "014707702";
    private static final String SERVER   = "jdbc:mysql://ambari-head.csc.calpoly.edu/";

    // Connection to the database, singleton
    private static Connection connect;

    /**
     * Performs a select query. The arguments should be the exact text that would appear in the given clause, using
     * constants from the Tables interface for table and column names. Where, grouping, and ordering can be null if
     * they aren't used in the query.
     * On error, prints error message to stdout and returns null.
     *
     * @param fields   (required) column names or aggregate functions to select
     * @param tables   (required) tables to select from
     * @param where    "where" clause; if none, then enter null
     * @param grouping "group by" clause; if none, then enter null
     * @param ordering "order by" clause; if none, then enter null
     * @return a ResultSet containing the results of the query
     * @see ResultSet
     */
    static ResultSet select ( String fields, String tables, String where, String grouping, String ordering ) {

        Connection conn = getConnect ( );

        try {
            ResultSet rs;
            Statement statement = conn.createStatement ( );
            String    sb        = getSelectStatement ( fields, tables, where, grouping, ordering );
            rs = statement.executeQuery ( sb );
            return rs;
        } catch ( SQLException e ) {
            System.out.println ( e.getMessage ( ) );
            return null;
        }
    }

    /**
     * Generates a select statement as a string. It gets called internally by select(), otherwise it only really
     * needs to be used when trying to perform a nested query, where the result can be passed as part of a "from" or
     * "where" clause. Where, grouping, and ordering can be null if they aren't used in the query.
     *
     * @param fields   (required) column names or aggregate functions to select
     * @param tables   (required) tables to select from
     * @param where    "where" clause; if none, then enter null
     * @param grouping "group by" clause; if none, then enter null
     * @param ordering "order by" clause; if none, then enter null
     * @return The select statement as a string
     */
    static String getSelectStatement ( String fields, String tables, String where, String grouping,
            String ordering ) {

        StringBuilder sb = new StringBuilder ( );
        sb.append ( "SELECT " ).append ( fields ).append ( "\n" );
        sb.append ( "FROM " ).append ( tables ).append ( "\n" );
        if ( where != null ) {
            sb.append ( "WHERE " ).append ( where ).append ( "\n" );
        }
        if ( grouping != null ) {
            sb.append ( "GROUP BY " ).append ( grouping ).append ( "\n" );
        }
        if ( ordering != null ) {
            sb.append ( "ORDER BY " ).append ( ordering ).append ( "\n" );
        }
        sb.append ( ";\n" );
        return sb.toString ( );
    }

    /**
     * Internal accessor for the class's database Connection.
     *
     * @return a Connection to the database
     * @see Connection
     */
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
}