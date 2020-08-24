/*Class: UserLogin
 *Group:Three men and a baby 
 *Description: UserLogin provides the GUI of User Login scenes 
 *that user need to enter their userID and password that need to match with database
 */
package groupproject.controllers;

import groupproject.classes.DatabaseOperations;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author qimingchen
 */
public class UserLoginController implements Initializable {

    @FXML
    private TextField userIdTextField;
    @FXML
    private TextField userPasswordTextField;
    @FXML
    private AnchorPane userLoginPane;

    //Instance of database
    private DatabaseOperations databaseOperation = new DatabaseOperations();
    @FXML
    private Text errorMessageTx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //connect to Database
            databaseOperation.connectTODB();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    //logIn, if match the username and password loading to MainStage scene
    private void logIn(ActionEvent event) throws SQLException, IOException {

        errorMessageTx.setVisible(false);

        //if find the userName and userPassword match in mysql load to mainStage
        if (databaseOperation.userLoginProcesses(userIdTextField.getText().trim(), userPasswordTextField.getText().trim())) {
            //close the userlogin stage
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

            //loading the mainstage
            loadMainStage();
        } //else display the error message to user
        //stop here at 04/15/2019 11:19pm
        else {
            errorMessageTx.setVisible(true);
        }

    }

    @FXML
    //go to Register scene
    private void goToRegister(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/groupproject/fxmls/registerView.fxml"));
        userLoginPane.getChildren().setAll(pane);

    }

    @FXML
    //quit program
    private void quitProgram(ActionEvent event) {
        Platform.exit();
    }

    /* 
     *loading to MainStage  
     */
    public void loadMainStage() throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/groupproject/fxmls/MainStage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Room Info");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
