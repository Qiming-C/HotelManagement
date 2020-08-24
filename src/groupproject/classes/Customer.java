/*Class: Customer 
 *Group: Three men and a baby 
 *Description: the customer class contains all the infomations of a person and then is linked to the room object.
 *
 */
package groupproject.classes;



/**
 *  
 * @author qimingchen
 */
public class Customer
{ 
       //customer infomation
        private String firstName;
        private String lastName;
        private String checkInDate;
        private String checkOutDate;
        private String phoneNumber;
        private String address;     
        private String email;
        private Room room;
        
        
        
        
        
        //constructor for initializing all of member variable    
        public Customer()
        {
            firstName="qiming";
            lastName="chen";          
            phoneNumber="8573108528";
            address="quincy";
            email="jim@gmail.com";
            checkInDate=null;
            checkOutDate=null;
            
           
            
        }
        
        /*
         *   Initializes a customer with specified infomation
         *   @param firstName
         *   @param lastName
         *   @param checkInDate
         *   @param checkOutDate
         *   @param phoneNumber
         *   @param address
         *   @param email  
         */
        public Customer(String firstName,String lastName,String checkInDate,String checkOutDate,String phoneNumber,String address,String email,Room room)
        {
            this.firstName=firstName;
            this.lastName=lastName;
            this.checkInDate=checkInDate;
            this.checkOutDate=checkOutDate;
            this.phoneNumber=phoneNumber;
            this.address=address;
            this.email=email;
            this.room=room;
            
            
        }
        
        /*
            getter of room object
            @return room
        */
        
        public Room getRoomObject()
        {
            return room;
        }
        
        /*
            setter of firstname
            @param firstName
        */
        public void setFirstName(String firstName)
        {
            this.firstName=firstName;
        }
        
        
        /*
            getter of firstname
            @return the firstName
        */
        public String getFirstName()
        {
            return firstName;
        }
        
        
        /*
            setter of setting last name
            @param lastName
        */
        public void setLastName(String lastName)
        {
            this.lastName=lastName;
        }
        
        
        
        /*
            getter of getting the last name
            @return lastName;
        */
        public String getLastName()
        {
            return lastName;
        }
        
        //setter of setting phone number
        /*
            @param phoneNumber
        */
        public void setPhoneNumber(String phoneNumber)
        {
            this.phoneNumber=phoneNumber;
        }
        
        
        //getter of getting phone number
        /*
            @return phoneNumber
        */
        public String getPhoneNumber()
        {
            return phoneNumber;
        }
        
        //setter of setting the checkIn Date
        /*
            @param checkInDate
        */
        public void setCheckInDate(String checkInDate)
        {
            this.checkInDate=checkInDate;
        }
        
        //getter of getting the checkIn Date
        /*
            @return checkInDate
        */
        public String getCheckInDate()
        {
            return this.checkInDate;
        }
        
        //setter of setting the checkOut Date
        /*
            @param checkOutDate
        */
        public void setCheckOutDate(String checkOutDate)
        {
            this.checkOutDate=checkOutDate;
        }
        
        //getter of getting the checkIn Date
        /*
            @return checkOutDate
        */
        public String getCheckOutDate()
        {
            return this.checkOutDate;
        }
        
        
        //setter of setting the address
        /*
            @param address
        */
        public void setAddress(String address)
        {
            this.address=address;
        }
        
        //getter of getting the address
        /*
            @return address
        */
        public String getAddress()
        {
            return address;
        }
        
        //setter of setting the email
        /*
            @param email
        */
        public void setEmail(String email)
        {
            this.email=email;
        }
        
        //getter of getting the email
        /*
            @return email
        */
        public String getEmail()
        {
            return email;
        }
        
        /*
         *   get room number
         *   @return room Number
         */
        
        public String getRoomNumber()
        {
            return room.getRoomNumber();
        
        }
        
        /*
         *  get room type
         *  @return room Type
         */
        
        
        public String getRoomType()
        {
            return room.getRoomType();
        }
        
        /*
         *    get room Price
         *  @return room price
         */
    public int getRoomPrice()
        {
            return room.getRoomPrice();
        }
        
        /*
         *   get room STATUS
         *  @return true as using false as not one currently use
         */
        public boolean getRoomStatus()
        {
            return room.getRoomAvailable();
        }
        
        /*
            @return print out the info of customer
        */
        @Override
        public String toString()
        {
   
               return "The customer first name is :"+firstName+" "+lastName+" check In date:"+checkInDate.toString()+" check Out Date:"+checkOutDate.toString()+", phone number: "+
                       phoneNumber+", address is "+address+" , email is "+email+" Room Number is "+room.getRoomNumber()+" Room type is"+room.getRoomType()+" Room Price is"+room.getRoomPrice();
        }
        
       
}
    