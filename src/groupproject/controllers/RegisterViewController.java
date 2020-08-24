/*Class: RegisterController
 *Group:Three men and a baby 
 *Description: The registerController will generate the GUI of Registration.
 *User needs to enter userName and password in order to log in to the mainstage
 */
package groupproject.controllers;

import groupproject.classes.DatabaseOperations;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author qimingchen
 */
public class RegisterViewController implements Initializable {

    @FXML
    private AnchorPane registerPane;
    @FXML
    private TextField userIDRegister;
    @FXML
    private PasswordField passwordRegister;
    @FXML
    private Text errorText;

    //instance of databaseOperations
    DatabaseOperations databaseOperation = new DatabaseOperations();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //connect to database
            databaseOperation.connectTODB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void registerBt(ActionEvent event) throws IOException, SQLException {
        //getting user and password from textField;
        String userName = userIDRegister.getText();
        String password = passwordRegister.getText();

        //
        try {
            errorText.setVisible(false);
            databaseOperation.registerProcesses(userName, password);
            //once register success back to UserLogin view
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/groupproject/fxmls/UserLogin.fxml"));
            registerPane.getChildren().setAll(pane);
            databaseOperation.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //display the error
            errorText.setVisible(true);
        }

    }

    @FXML
    private void exitBt(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/groupproject/fxmls/UserLogin.fxml"));
        registerPane.getChildren().setAll(pane);
    }

}
