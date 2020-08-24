/*Class: DataBaseOperations
 *Group:Three men and a baby 
 *Description: The databaseOperations class provides the operations of database
 *Primaryly access to access the customer information from mysql Table and also the userLogin Table
 *Attention: the user need to MODIFY the MYSQL ROOT NAME AND PASSWORD
 *LINE:37 LINE:250-251
 */
package groupproject.classes;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author qimingchen
 */
public class DatabaseOperations {

    //JDBC Driver NAME and DATABASE URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //connection instance
    private Connection connection;
    private Statement statement;

    //create the database 
    public void connectTODB() throws SQLException, ClassNotFoundException {
        //register JDBC driver
        Class.forName(JDBC_DRIVER);
        //connecting to mysql
        connection = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=EST5EDT", "root", "chenqiming");

        //creating database
        System.out.println("creating a database..");
        statement = connection.createStatement();

        //execute a request          
        String createDbStr = "Create database if not exists HotelManagement";
        statement.execute(createDbStr);
        System.out.println("database has been created successfully");
        //use database
        System.out.println("use hotel db");
        String useDB = "use hotelmanagement";
        statement.execute(useDB);
        //creating the table of Customer

        String createTbStr = "create table if not exists Customer(firstName varchar(25) not null, lastName varchar(25) not null, checkInDate varchar(50) not null,checkOutDate varchar(60) not null,phoneNumber varchar(20) not null,address varchar(50) not null,email varchar(50) not null,roomNumber varchar(10) not null,roomType varchar(10) not null, roomPrice double not null, foodExpense varchar(50),constraint pkRoomNum PRIMARY KEY(roomNumber));";;
        statement.execute(createTbStr);
        System.out.println("table has been created");
        //creating the table of user login inf
        String createLoginTbStr = "create table if not exists LoginInfo(userName varchar(50) not null,password varchar(50) not null,constraint pkUserID PRIMARY KEY(userName));";
        statement.execute(createLoginTbStr);
        System.out.println("table of login has been created");
        //creating the room table
        statement.execute("create table if not exists roomInfo(roomNo char(25), roomType char(25) , roomPrice double, roomAvailable boolean, primary key(roomNo))");
        System.out.println("table create...");
    }

    // database operation for checkIn process
    /*
        @return boolean  true means checkin successfully
     */
    public Boolean checkInProcesses(Customer customer, Text errorMessage) throws SQLException, ClassNotFoundException {

        boolean flag = false;
        //CHECK THE ROOM WHETHER IS BEING CREATED OR NOT
        if (!checkRoomStatus(customer.getRoomNumber())) {
            System.out.println("the room is not created yet");
            errorMessage.setVisible(true);
            errorMessage.setText("the room is not created yet");
            return flag;

        }

        //change the status of room 
        //change the room status in roomInfotable
        String roomInfoQuery = "select *from roominfo";
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet result = statement.executeQuery(roomInfoQuery);
        while (result.next()) {
            if (result.getString(1).equals(customer.getRoomNumber()) && result.getBoolean(4) == false) {
                result.updateBoolean(4, true);
                result.updateRow();

                //insert the record to customer table
                String query = "insert into Customer(firstName,lastName,checkInDate,checkOutDate,phoneNumber,address,email,roomNumber,roomType,roomPrice,foodExpense) values (?,?,?,?,?,?,?,?,?,?,?)";

                //create a statement 
                PreparedStatement preparedstatement = connection.prepareStatement(query);
                preparedstatement.setString(1, customer.getFirstName());
                preparedstatement.setString(2, customer.getLastName());
                preparedstatement.setString(3, customer.getCheckInDate());
                preparedstatement.setString(4, customer.getCheckOutDate());
                preparedstatement.setString(5, customer.getPhoneNumber());
                preparedstatement.setString(6, customer.getAddress());
                preparedstatement.setString(7, customer.getEmail());
                preparedstatement.setString(8, customer.getRoomNumber());
                preparedstatement.setString(9, customer.getRoomType());
                preparedstatement.setInt(10, customer.getRoomPrice());
                preparedstatement.setString(11, "0");
                preparedstatement.execute();
                System.out.println("user registration successed.");
                flag = true;
                return flag;
            }

        }

        System.out.println("the user registration failed");
        return flag;
    }

    //database operation for checkOut process
    //this process should get the value from tableview
    //calculate the food expenses and the days of night stay
    //the checkout procedure should take the roomNumber of the checkedIn customer then delete from database
    //find the bug:the record will delete the previous one 04/29/2019 qiming
    //04/29/2019: checkOut processes should change the room status
    public Customer CheckOutProcesses(TableView<Customer> customerTableView) throws SQLException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        String roomNumber = customer.getRoomNumber();
        System.out.println("the room number is " + roomNumber);
        String query = "Select *from customer";

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet resultSet = statement.executeQuery(query);

        //travsing the resultSet find the match value of roomNumber then delete it 
        while (resultSet.next()) {
            if (resultSet.getString("roomNumber").equals(roomNumber)) {
                System.out.println("roomNumber is" + resultSet.getString("roomNumber"));

                System.out.println("the record has been deleted in Databases");
            } else {
                System.out.println("Error: couldn't find the customer infomation in database");
            }
        }
        //return the customer info in order to process the payment procedure.
        return customer;
    }

    //validate the room status
    /*
        @return boolean indicates whether the room is being used or not.
     */
    public Boolean checkRoomStatus(String roomNum) throws SQLException {
        String query = "select *from roominfo";
        //result set
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        Boolean roomStatus = false;
        //check the existence first 
        //if(true) the room is existed

        //find the status of room
        while (result.next()) {
            if (result.getString(1).equals(roomNum)) {
                roomStatus = true;
                System.out.println("the room is avaiable.");
                return roomStatus;
            }

        }

        return roomStatus;

    }

    //when checking out, change the status of room
    /*
        @void 
     */
    public void changeRoomStatus(Customer customer) throws SQLException {
        String roomNumber = customer.getRoomNumber();
        System.out.println("the room number is " + roomNumber);
        String query = "Select *from roominfo";

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            if (result.getString(1).equals(roomNumber) && result.getBoolean(4)) {
                result.updateBoolean(4, false);
                result.updateRow();
                System.out.println("room status is change to false");
                return;
            }

        }

        System.out.println("room is not being changed, because the room is not using");

    }

    //database operation for loading the checked-In customer
    public void loadingProcesses(TableView customerTableView, TableView roomTableView) throws SQLException {
        //query string 
        String query = "select *from customer";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();
        //get the columns number in order to print correctly
        int columnsNumber = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i < columnsNumber; i++) {
                System.out.print(resultSet.getString(i) + " ");
            }
            Room room = new Room(resultSet.getString(8), resultSet.getString(9), resultSet.getInt(10), true);
            Customer customer = new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), room);
            customerTableView.getItems().add(customer);
            roomTableView.getItems().add(room);
        }

    }

    //database operation for registering
    public void registerProcesses(String userName, String password) throws SQLException {
        //query string
        String query = "insert into LoginInfo(userName,password) values(?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);

        //executre the statement
        preparedStatement.execute();

    }

    //userLogin Process need to query the database find if match the parameter
    public boolean userLoginProcesses(String userName, String password) throws SQLException {

        //query String    
        String query = "select *from LoginInfo";
        //create a rowset to traverse mysql 
        JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
        rowSet.setUrl("jdbc:mysql://localhost/hotelManagement?serverTimezone=EST5EDT");
        rowSet.setUsername("root");
        rowSet.setPassword("chenqiming");
        rowSet.setCommand(query);
        rowSet.execute();
        //boolean flag to indicate the status of finding 
        boolean isFound = false;

        //traversing the rowSet to find the match username and password
        while (rowSet.next()) {
            if (rowSet.getString("userName").equals(userName) && rowSet.getString("password").equals(password)) {
                isFound = true;
                return isFound;
            } else {
                isFound = false;
            }
        }
        return isFound;
    }

    //----The methods below its modifying the food ordering scene
    /*checking roomNumber and firstName for food ordering validation if exists return true 
        @return the process status
     */
    public boolean findRoomAndName(String roomNumber, String firstName, String foodExpense) throws SQLException {
        String query = "select *from Customer";

        //set the result set updatable and sensitive
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        ResultSet result = statement.executeQuery(query);
        //boolean flag to indicate the status of finding 
        boolean isFound = false;

        //traversing the rowSet to find the match username and password
        while (result.next()) {
            if (result.getString("roomNumber").equals(roomNumber) && result
                    .getString("firstName").equals(firstName)) {
                //get the price from databse if the customer already ordered before                           
                int currentPrice = Integer.parseInt(result.getString("foodExpense"));
                int addingPrice = Integer.parseInt(foodExpense);
                int newTotal = currentPrice + addingPrice;
                //convert to String
                foodExpense = String.valueOf(newTotal);
                //insert the food expense
                result.updateString("foodExpense", foodExpense);
                System.out.println("updating the food exepense to DB");

                result.updateRow();
                isFound = true;
                return isFound;
            } else {
                isFound = false;
            }
        }
        return isFound;
    }

    /*
        @return the foodexpense of customer
     */
    public String getFoodExpense(String firstName, String lastName) throws SQLException {
        String query = "select *from Customer";
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet result = statement.executeQuery(query);
        String foodExpense = null;

        //SUPER LAGGING PROBLEM THAT I WAS FACING!
        //WHEN THE TABLE ONLY 1 ROW EXISTS, THEN WHILE(RESULT.NEXT()) WILL NOT WORKING!!!
        //04/22/2019 Find out the bug, its because check Out process will delete the row, then when payment methods gets call does not have record in there.
        while (result.next()) {
            if (result.getString("firstName").equals(firstName) && result.getString("lastName").equals(lastName)) {
                foodExpense = result.getString("foodExpense");
                //delete record
                result.deleteRow();
                System.out.println("does it go in there?");
                return foodExpense;

            } else {
                foodExpense = "0";
            }

        }

        System.out.println("goes here");
        return foodExpense;

    }

    //--The methods below its modifying the room details scene
    //modifying the room info
    public boolean addNewRoom(Room room) throws SQLException {
        String query = "select *from roomInfo";
        String insert = "insert into roomInfo(roomNo,roomType,roomPrice,roomAvailable)  values(?,?,?,?) ";
        statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        boolean flag = false;

        if (!result.next()) {

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomType());
            preparedStatement.setInt(3, room.getRoomPrice());
            preparedStatement.setBoolean(4, room.getRoomAvailable());
            preparedStatement.execute();
            System.out.println("insert the new room details!");
            flag = false;
            return flag;
        } else {
            //move the cursor back to the previous position
            result.previous();
            while (result.next()) {
                //return false indicates that the room already exists
                if (result.getString(1).equals(room.getRoomNumber())) {
                    flag = true;
                    break;
                } else {
                    PreparedStatement preparedStatement = connection.prepareStatement(insert);
                    preparedStatement.setString(1, room.getRoomNumber());
                    preparedStatement.setString(2, room.getRoomType());
                    preparedStatement.setInt(3, room.getRoomPrice());
                    preparedStatement.setBoolean(4, room.getRoomAvailable());
                    preparedStatement.execute();
                    System.out.println("insert the new room details!");
                    flag = false;
                    return flag;
                }

            }
        }
        return flag;

    }

    //removing the room detail
    public boolean deleteRoom(TableView<Room> table) throws SQLException {
        Room removedItem = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(removedItem);

        //delete the information based on the room Number in the database
        String roomNumber = removedItem.getRoomNumber();
        String deleteQuery = "delete from roominfo where roomNo=?";
        boolean flag = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, roomNumber);
            preparedStatement.execute();
            System.out.println("delete succeed");
            flag = true;
            return flag;
        } catch (Exception ex) {
            ex.printStackTrace();
            return flag;
        }

    }

    //return room object from database
    public void updatingRoom(ObservableList<Room> data, TableView table) throws SQLException {
        String updateString = "select * from roomInfo";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(updateString);

        data = FXCollections.observableArrayList();
        while (resultSet.next()) {

            data.add(new Room(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4)));

        }
        table.setItems(data);
        System.out.println("update succeed!!");

    }

    //close the connection from Mysql
    public void closeConnection() throws SQLException {
        connection.close();
    }

}
