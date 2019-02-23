import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Main extends Application {

    public static void main ( String[] args ) {

        launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception {

        // create javafx window
        Parent root = FXMLLoader.load ( getClass ( ).getResource ( "sample.fxml" ) );
        primaryStage.setTitle ( "Bicycle Inventory" );
        primaryStage.setScene ( new Scene ( root, 300, 275 ) );
        primaryStage.show ( );

        // simple query with "where" clause
        ResultSet test = DBController.select ( "*", Tables.CUSTOMERS, "cname LIKE 'Ac%'", null, "cname" );
        if ( test != null ) {
            while ( test.next ( ) ) {
                System.out.println ( "Name: " + test.getString ( Tables.CNAME ) );
            }
        }

        System.out.println ( );

        // aggregation query with ordering
        ResultSet test2 = DBController.select ( Tables.PDATE + ", SUM(" + Tables.PAMOUNT + ") AS " + Tables.PAMOUNT,
                Tables.PAYMENTS, null, Tables.PDATE, Tables.PDATE );
        if ( test2 != null ) {
            System.out.println ( String.format ( "%-12s%-9s", "Date", "Payment" ) );
            while ( test2.next ( ) ) {
                System.out.println ( String.format ( "%-12s%9.2f", test2.getString ( Tables.PDATE ),
                        test2.getDouble ( Tables.PAMOUNT ) ) );
            }
        }
    }
}
