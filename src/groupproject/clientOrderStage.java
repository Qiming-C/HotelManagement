/*Class: clientOrderStage
 *Group:Three men and a baby 
 *Description: The clientOrder Stage is GUI of Client for Customer ordering 
 *Where displays the list of food ,customer need to add those to the shopping cart, and then enter room number and firstName
 *Server will receive the information modify the roomnumber and firstname if exsists send back the message to Client.
 */
package groupproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Shing Hong Lau
 */
public class clientOrderStage extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/groupproject/fxmls/FoodDocumentForClient.fxml"));
        Scene scene = new Scene(root);   
        stage.setScene(scene);
        stage.show();
        
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
