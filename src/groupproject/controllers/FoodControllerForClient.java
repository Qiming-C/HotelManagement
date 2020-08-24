/*Class: FoodControllerForClient
 *Group:Three men and a baby 
 *Description: This controller is making the functionality of the Client ordering procedure
 */
package groupproject.controllers;

import groupproject.classes.FoodCustomer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.binding.Bindings;
import javafx.scene.text.Text;

/**
 *
 * @author Shing Hong Lau
 */
public class FoodControllerForClient implements Initializable {
    
    DataOutputStream toServerSize = null;
    DataOutputStream toServerRoom = null;
    DataOutputStream toServerName = null;
    DataOutputStream toServerTotal = null;
    ObjectOutputStream toO = null;
    DataInputStream fromS = null;
    int size = 0;
    
    @FXML
    private TextField mFoodQuantity;
    
    @FXML
    private TextField mRoom;
    
    @FXML
    private Label mTotalPrice;
    
    @FXML
    private TableView<FoodCustomer> displayList;
    @FXML
    private TableColumn<?, ?> listType;
    @FXML
    private TableColumn<?, ?> listName;
    @FXML
    private TableColumn<?, ?> listPrices;
    
    @FXML
    private TableView<FoodCustomer> FoodListView;
    @FXML
    private TableColumn<?, ?> orderType;
    @FXML
    private TableColumn<?, ?> orderName;
    @FXML
    private TableColumn<?, ?> orderQuantity;
    @FXML
    private TableColumn<?, ?> orderPrices;
    
    private FoodCustomer foodC = new FoodCustomer();
    private int totalPrices = 0;
    @FXML
    private TextField mFirst;
    @FXML
    private Text errorMessage;
    
    @FXML
    private void mEnter(ActionEvent event) {
        System.out.println("this is enter ");
        //connect to Server

        try {
            
            Socket socket = new Socket("localhost", 8888);
            toServerSize = new DataOutputStream(socket.getOutputStream());
            toServerName = new DataOutputStream(socket.getOutputStream());
            toServerRoom = new DataOutputStream(socket.getOutputStream());
            toServerTotal = new DataOutputStream(socket.getOutputStream());
            toO = new ObjectOutputStream(socket.getOutputStream());
            
            fromS = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("socket problem\n");
            ex.printStackTrace();
        }

        //list of food
        System.out.println("connect server");
        
        System.out.println("after connect");
        
        System.out.println("try send info");
        size = FoodListView.getItems().size();
        System.out.println("size is " + size
        );
        
        new Thread(() -> {
            try {

                //send the size of list
                toServerSize.writeInt(size);

                //send the roomnumber
                toServerRoom.writeUTF(mRoom.getText().trim());
                //send the firstName
                toServerName.writeUTF(mFirst.getText().trim());

                //send list of objects
                for (int i = 0; i < FoodListView.getItems().size(); i++) {
                    toO.writeObject((FoodCustomer) FoodListView.getItems().get(i));
                }

                //send the total price even the name is size
                toServerTotal.writeInt(totalPrices);
                
                toServerSize.flush();
                toServerRoom.flush();
                toServerName.flush();
                toServerTotal.flush();
                toO.flush();

                //receive the process status
                int processStatus = fromS.readInt();
                System.out.println("process Status is " + processStatus);
                
                if (processStatus == 2) {
                    errorMessage.setText("Error: Room and Name are incorrect!");
                } else if (processStatus == 1) {
                    errorMessage.setText("Purchase successed!");
                    
                }
                
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }
    
    @FXML
    private void mDelete(ActionEvent event) {
        totalPrices = 0;
        
        if (Bindings.isEmpty(FoodListView.getItems()).get()) {
            System.out.println("Is empty");
            
        } else {
            FoodCustomer selectedItem = FoodListView.getSelectionModel().getSelectedItem();
            FoodListView.getItems().remove(selectedItem);
            
            FoodListView.getItems().forEach((FoodCustomer item) -> totalPrices += item.getTotalPrices());
            mTotalPrice.setText(String.valueOf(totalPrices));
        }
        
    }
    
    ;
    
    @FXML
    private void mAdd(ActionEvent event) {

        //reset the total prices is zero
        totalPrices = 0;

        //default the riminder if it is empty    
        if (mFoodQuantity.getText().isEmpty()
                || Integer.parseInt(mFoodQuantity.getText()) <= 0) {
            mFoodQuantity.setText("1");
        }
        
        FoodCustomer selectedItem = displayList.getSelectionModel().getSelectedItem();
        selectedItem.setQuantity(Integer.parseInt(mFoodQuantity.getText().trim()));
        
        FoodListView.getItems().add(selectedItem);
        
        FoodListView.getItems().forEach((FoodCustomer item) -> totalPrices += item.getTotalPrices());
        mTotalPrice.setText(String.valueOf(totalPrices));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orderType.setCellValueFactory(new PropertyValueFactory<>("foodType"));
        orderName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        orderPrices.setCellValueFactory(new PropertyValueFactory<>("foodPrices"));
        orderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        listType.setCellValueFactory(new PropertyValueFactory<>("foodType"));
        listName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        listPrices.setCellValueFactory(new PropertyValueFactory<>("foodPrices"));

        //display the list 
        for (int i = 0; i < foodC.listLength(); i++) {
            FoodCustomer temp = new FoodCustomer(foodC.dType(i), foodC.dName(i), foodC.dPrice(i));
            displayList.getItems().add(temp);
        }
        
    }
    
};

//  Integer.parseInt();      FoodListView.setItems(getFood());    
/**
 *
 */
