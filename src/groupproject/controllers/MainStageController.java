/*Class: MainStageController
 *Group:Three men and a baby 
 *Description: the mainstage controller will generate the GUI of Main Stage, that contains the menuBar for roomDetails
 *and Ordering service.Displaying the infomation of customer
 */
package groupproject.controllers;

import groupproject.classes.Room;
import groupproject.classes.DatabaseOperations;
import groupproject.classes.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class MainStageController implements Initializable {

    private ObservableList<String> roomTypeList = FXCollections.observableArrayList("Select", "Single", "Double", "Queen", "King");
    private DatabaseOperations mysqlProcesses = new DatabaseOperations();
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TableColumn<?, ?> firstNameCol;
    @FXML
    private TableColumn<?, ?> lastNameCol;
    @FXML
    private TableColumn<?, ?> phoneCol;
    @FXML
    private TableColumn<?, ?> addressCol;
    @FXML
    private TableColumn<?, ?> emailCol;
    @FXML
    private TableColumn<?, ?> checkInCol;
    @FXML
    private TableColumn<?, ?> checkOutCol;
    @FXML
    private TableColumn<?, ?> roomNumCol;
    @FXML
    private TableColumn<?, ?> roomTypeCol;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private ComboBox roomTypeChoice;
    @FXML
    private TableView<Customer> tableView;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private TextField roomNumberTextField;
    @FXML
    private TextField roomPriceTextField;
    @FXML
    private TableView<Room> roomTableView;
    @FXML
    private TableColumn<?, ?> roomPriceCol;
    @FXML
    private Text roomFeeText;
    @FXML
    private Text daysText;
    @FXML
    private Text totalText;
    @FXML
    private Text taxText;
    @FXML
    private Text roomExpenseText;
    @FXML
    private Text foodExpenseText;
    @FXML
    private Text errorMessageText;
    @FXML
    private MenuBar menuBar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomTypeChoice.setValue("Select");
        roomTypeChoice.setItems(roomTypeList);

        //setting the cell of each Column that propertyValueFactory should make sure that 
        //the variable name should be consisted with memeber variable name otherwise the tableview can not 
        //display the value
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roomNumCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomPriceCol.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));

        //connect to database
        try {
            mysqlProcesses.connectTODB();
        } catch (SQLException ex) {
            Logger.getLogger(MainStageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainStageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void checkIn(ActionEvent event) throws SQLException, ClassNotFoundException {
        //get the date from datePicker convert to String
        String checkInDateString = checkInDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String checkOutDateString = checkOutDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        //instance of room    
        Room room = new Room(roomNumberTextField.getText().trim(), roomTypeChoice.getSelectionModel().getSelectedItem().toString(), Integer.parseInt(roomPriceTextField.getText().trim()), true);

        //instance of Customer
        Customer customer = new Customer(firstNameTextField.getText().trim(), lastNameTextField.getText().trim(), checkInDateString, checkOutDateString,
                phoneTextField.getText().trim(), addressTextField.getText().trim(), emailTextField.getText().trim(), room);
        
        //checkIn the customer into Mysql
        //if the customer or the room is already existed then display the errorMessage.
        
       
      
        if(mysqlProcesses.checkInProcesses(customer,errorMessageText))
        {
             errorMessageText.setVisible(false);
        
        roomTableView.getItems().add(room);
        tableView.getItems().add(customer); 
        //clean the textfield content
        firstNameTextField.clear();
        lastNameTextField.clear();
        phoneTextField.clear();
        addressTextField.clear();
        emailTextField.clear();
        roomNumberTextField.clear();
        roomPriceTextField.clear();
        roomTypeChoice.setValue(null);
        checkInDatePicker.setValue(null);
        checkOutDatePicker.setValue(null);
        
        
        }
        else
        {
           errorMessageText.setVisible(true);
        }
    }

    //loading the record from database
    //we could just loading the record automatically
    //but considering the privacy,then we decide let user to take an action.
    @FXML
    private void loadingRecord(ActionEvent event) throws SQLException {
        //loading the record to tableview
        //prevent the duplicated results show in the table
        //clear the tableview everytime
        tableView.getItems().clear();
        roomTableView.getItems().clear();
        mysqlProcesses.loadingProcesses(tableView, roomTableView);
    }

    //checkOut process
    //updating the payment details text.
    @FXML
    private void checkingOut(ActionEvent event) throws SQLException, ParseException {
        //everyTime checkOut is clicked, clear the tableview, load again
        Customer customer = mysqlProcesses.CheckOutProcesses(tableView);
        //payment Processe
        payment(customer);
        //change the status of room
        mysqlProcesses.changeRoomStatus(customer);
        
        //clear the customertableview and roomtable
        tableView.getItems().clear();
        roomTableView.getItems().clear();       
        
        mysqlProcesses.loadingProcesses(tableView, roomTableView);
        
        
    }

    @FXML
    private void loggingOut(ActionEvent event) throws SQLException {
        mysqlProcesses.closeConnection();
        Platform.exit();
    }

    //payment process to display in the BOARD
    private void payment(Customer customer) throws ParseException, SQLException {
        long days = daysCalculater(customer);
        DecimalFormat df = new DecimalFormat("#.00");
        double roomExpense = days * customer.getRoomPrice();
        double taxExpense = roomExpense * 0.07;
        double totalExpense = roomExpense + taxExpense;
        System.out.println("customer first name is"+customer.getFirstName());
        String foodExpense=mysqlProcesses.getFoodExpense(customer.getFirstName().trim(),customer.getLastName().trim());
        System.out.println("food Expense is"+foodExpense );
        //get the customer food expense
        foodExpenseText.setText(foodExpense);        
        roomFeeText.setText(df.format(customer.getRoomPrice()));
        roomExpenseText.setText(df.format(roomExpense));
        daysText.setText(df.format(days));
        taxText.setText(df.format(taxExpense));
        totalText.setText(df.format(totalExpense));

    }

    /*
        calculate the day difference
    */
    private long daysCalculater(Customer customer) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date checkInDate = formatter.parse(customer.getCheckInDate());
        java.util.Date checkOutDate = formatter.parse(customer.getCheckOutDate());
        long difference = checkOutDate.getTime() - checkInDate.getTime();
        //int daysBetween = (int) (difference / (1000 * 60 * 60 * 24));
        long daysBetween = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        System.out.println("the days between is " + daysBetween);

        return daysBetween;
    }

    /*      
        loading the room Stage
    */

    @FXML
    private void loadingRoomStage(ActionEvent event)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/groupproject/fxmls/RoomSelectionUI.fxml"));
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

    /*
    
    */
    @FXML
    private void backToLogin(ActionEvent event) throws IOException 
    {
        //close current stage
        
        ((Stage) (((MenuBar)menuBar).getScene().getWindow())).close();
        
       
        //loading login stage
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/groupproject/fxmls/UserLogin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.initStyle(StageStyle.UNDECORATED);
            stage2.setTitle("User Login");
            stage2.setScene(new Scene(root1,579,427));
            stage2.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

}
