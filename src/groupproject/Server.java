/*Class: SERVER
 *Group:Three men and a baby 
 *Description: server.class provides the connection for customer who wants to order food. 
 *Server will receive the object of foodclass then do the database process
 *The advantanges of separaing the server and mainstage, allows the manager can just open the server to accept request from client
 *MainStage will not need to process the food ordering from customer, the server stores the infomation in Mysql
 *any futher process will read from Mysql.
 */
package groupproject;
import groupproject.classes.DatabaseOperations;
import groupproject.classes.FoodCustomer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author qimingchen
 */
public class Server extends Application {

    DatabaseOperations databaseOperation = new DatabaseOperations();
    int foodExpense;
    //text area for displaying the contents 
        TextArea textConsole = new TextArea();
        //button to quit server
        Button quit = new Button("quit");
        //VBOX FOR BINDS THE LAYOUT
        VBox layout = new VBox();
    

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {

        

        layout.getChildren().addAll(new ScrollPane(textConsole), quit);
        //create a scene then show the stage
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        //connect to databse
        databaseOperation.connectTODB();

        //connect to Client
        new Thread(() -> {

            try {
                //server Socket
                ServerSocket serverSocket = new ServerSocket(8888);

                //receive procedure is 1.roomNumber, FirstName 2.nums of Objects 3.food class
                while (true) 
                {
                    try{
                        
                        //socket for connection request
                        Socket socket = serverSocket.accept();
                        Platform.runLater(()
                                -> textConsole.appendText("Receive customer Ordering......at:" + new Date() + "\n"));
                        
                        //data output stream send the status to the Client whether the process is successed or not
                        DataOutputStream toClientStatus = new DataOutputStream(socket.getOutputStream());
                        
                        //receive the datas from Client which are RoomNumber, Customer FirstName, and Food Object
                        DataInputStream fromClientRoomNum = new DataInputStream(socket.getInputStream());
                        DataInputStream fromClientFirstName = new DataInputStream(socket.getInputStream());
                        DataInputStream fromClientObjectNum = new DataInputStream(socket.getInputStream());
                        DataInputStream fromClientTotal = new DataInputStream(socket.getInputStream());
                        ObjectInputStream fromClientObject = new ObjectInputStream(socket.getInputStream());
                        
                        try{
                            
                            //step 1:receive the object of food
                            
                            int objectNums= fromClientObjectNum.readInt();
                            List<FoodCustomer> orderList;
                            orderList = new ArrayList<>();
                            
                            
                            //step 2:receive the roomNumber and firstName
                            String roomNumber = fromClientRoomNum.readUTF();
                            String firstName = fromClientFirstName.readUTF();
                            
                            //step3: receive list of objects
                            for (int i = 0; i < objectNums; i++) {
                                
                                orderList.add((FoodCustomer) fromClientObject.readObject());
                                
                                System.out.println("receive the food objects ");
                            }
                            
                            //receive the food expense
                            foodExpense=fromClientTotal.readInt();
                            System.out.println("food expense total is :" + foodExpense);
                            String total=String.valueOf(foodExpense);
                            System.out.println("total is "+total);
                            //then verify the infomation of the customer
                            boolean isSuccess = databaseOperation.findRoomAndName(roomNumber, firstName,total);
                            
                            if (isSuccess) {
                                //write 1 indicates the food expense is added to the customer.
                                toClientStatus.writeInt(1);
                                toClientStatus.flush();
                            } else {
                                //write 2 indicatas the food expense is not added to the customer.
                                toClientStatus.writeInt(2);
                                toClientStatus.flush();
                            }
                            
                            
                        }catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        System.out.println("connect to server ");
        
        
        //set up the action of quit button
        quit.setOnAction(e -> {
            try {
                System.exit(0);
            } catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    
    
       public void connectToClient() {

        new Thread(() -> {

            try {
                //server Socket
                ServerSocket serverSocket = new ServerSocket(8888);

                //socket for connection request
                Socket socket = serverSocket.accept();
                Platform.runLater(()
                        -> textConsole.appendText("Server started at" + new Date() + "\n"));

                //data output stream send the status to the Client whether the process is successed or not
                DataOutputStream toClientStatus = new DataOutputStream(socket.getOutputStream());

                //receive the datas from Client which are RoomNumber, Customer FirstName, and Food Object
                DataInputStream fromClientRoomNum = new DataInputStream(socket.getInputStream());
                DataInputStream fromClientFirstName = new DataInputStream(socket.getInputStream());
                DataInputStream fromClientObjectNum = new DataInputStream(socket.getInputStream());
                DataInputStream fromClientTotal = new DataInputStream(socket.getInputStream());
                ObjectInputStream fromClientObject = new ObjectInputStream(socket.getInputStream());

                //receive procedure is 1.roomNumber, FirstName 2.nums of Objects 3.food class
                while (true) {

                    
                    //step 1:receive the object of food
                    
                    int objectNums= fromClientObjectNum.readInt();
                    List<FoodCustomer> orderList = new ArrayList<>();
             
                    
                    //step 2:receive the roomNumber and firstName
                    String roomNumber = fromClientRoomNum.readUTF();
                    String firstName = fromClientFirstName.readUTF();
                    
                    //step3: receive list of objects
                    for (int i = 0; i < objectNums; i++) {

                        orderList.add((FoodCustomer) fromClientObject.readObject());
                    }
                 

                    foodExpense=fromClientTotal.readInt();
                    System.out.println("food expense total is :" + foodExpense);

                    //then verify the infomation of the customer 
                    boolean isSuccess = databaseOperation.findRoomAndName(roomNumber, firstName, String.valueOf(foodExpense));

                    if (isSuccess) {
                        //write 1 indicates the food expense is added to the customer.
                        toClientStatus.writeInt(1);
                        toClientStatus.flush();
                    } else {
                        //write 2 indicatas the food expense is not added to the customer.
                        toClientStatus.writeInt(2);
                        toClientStatus.flush();
                    }

                }

            } catch (IOException | ClassNotFoundException ex) {
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }).start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}


   