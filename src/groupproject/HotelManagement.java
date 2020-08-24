/*Class: HotelManagement
 *Group:Three men and a baby 
 *Groupmates:ShongHong Lau, Jiafeng Li, Qiming Chen
 *Description: The hotelmanagement class loading the userLogin.fxml then start the program
 *The mainStage provides the checkIn,checkOut,Loading,Exit button
 *User need to enter  some infomations of Customer, then select checkIN button that could add to the database,
 *CheckOut button, user needs to load the Checked-in customer infomation from database then select the checking-out Customer, 
 *MeanWhile,the payment procedure will start
 *05/01/2019 : there is bug whenenver we switch the window of mainstage back and forth the output will have some error coming out,
 *However, the program is running fine. 
 */

package groupproject;

import groupproject.classes.DatabaseOperations;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author qimingchen
 */
public class HotelManagement extends Application {
    
    //
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/groupproject/fxmls/UserLogin.fxml"));
        DatabaseOperations databaseOperation=new DatabaseOperations();
        
        //connect to database
        databaseOperation.connectTODB();
        
        //new Thread for connecting to food ordering 
       
        
        
        Scene scene = new Scene(root);    
        stage.setScene(scene);
        stage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
        
        
        
    }
    
}
