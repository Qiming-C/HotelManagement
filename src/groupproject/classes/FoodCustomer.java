/*Class: FoodCustomer
 *Group:Three men and a baby 
 *Description: foodCustomer class its provides the food infomation.
 */
package groupproject.classes;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;



/**
 *
 * @author Shing Hong Lau
 */
public class FoodCustomer implements Serializable {
    
    private String foodType;
    private String foodName;
    private int foodPrices;
    private int quantity;

    private String[] setType = {"Food","Food","Food","Food","Drink","Drink","Drink","Drink" };
    private String[] setName = {"Chicken Wings", "Rice", "Noodles", "Boil Egg", "Cola", "Green Tea", "Honey Beer", "Red Wine"};
    private int[] setPrice ={4,2,2,1,2,2,4,6};
    
    // IO streams 
    static DataOutputStream toServer = null; 
    static DataInputStream fromServer = null; 
     
    public FoodCustomer(){    
    };
    
    public FoodCustomer(String type,String name, int price){
        this.foodType = type;
        this.foodName = name;
        this.foodPrices = price;
    }
    
    public FoodCustomer(String type,String name, int quantity, int price){
        this.foodType = type;
        this.foodName = name;
        this.quantity = quantity;
        this.foodPrices = price;
    }
    
    public String getFoodType(){return foodType;    }
    
    public int getQuantity(){  return quantity;    }
    
    public String getFoodName(){ return foodName;    }
    
    public int getFoodPrices(){ return foodPrices;    }
    
    public int getTotalPrices(){ return quantity * foodPrices; }
    
    public void setQuantity(int num){ this.quantity = num;  }
    
    public int listLength(){ return setType.length;    }
    
    public String dType(int i){  return setType[i];  }
    
    public String dName(int i){  return setName[i];  }
        
    public int dPrice(int i){ return setPrice[i]; }

public void sendTest(String a, String b, String c){
    
        String[] StringList = {a, b, c};
        
        try {              
            // convert the string to the sendable format
            for(int i = 0; i< 3; i++){
                byte[] data = StringList[i].getBytes("UTF-8");
                toServer.writeInt(data.length);    
                toServer.write(data); 
            }
            
            toServer.flush();
            
            // convert the received info to String
            for(int j = 0; j< 1; j++){
                int length = fromServer.readInt();
                byte[] data=new byte[length];
                fromServer.readFully(data);
                StringList[j]=new String(data,"UTF-8"); 
                System.out.println("Answer from Server "+ StringList[j]);
            }

    }   catch (IOException ex) { System.err.println(ex); }
}

public static void main(String[] args)
    throws SQLException, ClassNotFoundException {
    
    }
    

//mysql  -uroot -p
//Hi1234
}
             