/*
 * Jiafeng Li
 * Group project : Hotel management System
 * Parts of the Room checking system : check the room available, add and delete the room information.
 * Professor Miller 
 * Group member : Jiafeng Li, Qiming Chen, Shinhong Lau
 */
package groupproject.controllers;

import groupproject.classes.Room;
import groupproject.classes.DatabaseOperations;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jiafeng Li
 */
public class RoomSelectionUIController implements Initializable {

    //list
    private ObservableList<String> roomTypeList = FXCollections.observableArrayList("Select", "Single", "Double", "Queen", "King");

    @FXML
    private TableView<Room> table;
    @FXML
    private TableColumn<Room, String> room_No;
    @FXML
    private TableColumn<Room, String> tpye;
    @FXML
    private TableColumn<Room, String> price;
    @FXML
    private TableColumn<Room, String> available;
    @FXML
    private TextArea roomNoTF;
    @FXML
    private TextArea roomPTF;

    private String roomNo, roomType;
    private int roomPrice;
    private boolean roomAvailable;
    private DatabaseOperations databaseOperation = new DatabaseOperations();
    private ObservableList<Room> data;
    @FXML
    private Text messageRoomScene;
    @FXML
    private ComboBox<String> roomTypeComboBox;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomTypeComboBox.setValue("Select");
        roomTypeComboBox.setItems(roomTypeList);

        room_No.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        tpye.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        price.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        available.setCellValueFactory(new PropertyValueFactory<>("roomAvailable"));

        try {
            //connect to DB
            databaseOperation.connectTODB();
        } catch (SQLException | ClassNotFoundException ex) {
        }
    }

    @FXML
    // add room information
    private void addNewInfo(ActionEvent event) throws SQLException {

        roomNo = roomNoTF.getText().trim();
        roomType = roomTypeComboBox.getSelectionModel().getSelectedItem();
        roomPrice = Integer.parseInt(roomPTF.getText().trim());
        //by default when you modify the room, its false means not used.
        roomAvailable = false;

        Room room = new Room(roomNo, roomType, roomPrice, roomAvailable);
        try {
            //if success add to table        
            if (!databaseOperation.addNewRoom(room)) {
                table.getItems().add(room);
                data = FXCollections.observableArrayList(room);
                messageRoomScene.setText("");
                messageRoomScene.setText("add successfully!");

            } else {
                messageRoomScene.setText("");
                messageRoomScene.setText("the table already exists");
            }
        } catch (SQLException ex) {
            ex.setNextException(ex);
        }

    }

    @FXML
    //delete the room information 
    private void deleteInfo(ActionEvent event) throws SQLException {

        //calling delete method in databaseOperation class
        if (databaseOperation.deleteRoom(table)) {
            messageRoomScene.setText("");
            messageRoomScene.setText("the room has been deleted");

        } else {
            messageRoomScene.setText("");
            messageRoomScene.setText("the room is not existed");
        }
    }

    @FXML
    //update the whole information in the table view
    private void updateInfo(ActionEvent event) throws SQLException, ClassNotFoundException {

        databaseOperation.updatingRoom(data, table);

    }

    @FXML
    //clear the tableview screen
    private void clearScreenInfo(ActionEvent event) {

        table.getItems().clear();
        messageRoomScene.setText("");
    }

    @FXML
    private void exitRoomScene(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    

}
