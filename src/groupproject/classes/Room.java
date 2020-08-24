/*Class: Room
 *Group:Three men and a baby 
 *Description: Room class provides the room info
 *Room has roomType ,roomPrice, roomNumber, and roomStatus
 */
package groupproject.classes;

/**
 *
 * @author JEFF
 */
public  class Room {

        private String roomType;
        private int roomPrice;      
        private String roomNumber;
        private boolean roomAvailable;

        public Room()
        {
        roomNumber="123";
        roomPrice=100;
        roomType="Single";
        roomAvailable=false;// false indicates that the room is available
        
        }   
        /*
            @param roomnumber
        */
        public Room(String roomNumber,String roomType,int roomPrice,boolean roomAvailable) 
        {
            this.roomNumber = roomNumber;
            this.roomType=roomType;
            this.roomPrice=roomPrice;
            this.roomAvailable=roomAvailable;
        }
        
       

        //Variable setter
        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public void setRoomPrice(int roomPrice) {
            this.roomPrice = roomPrice;
        }
            
        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public void setRoomAvailable(boolean roomAvailable) {
            this.roomAvailable =roomAvailable;
        }
        
        //getter 
        
        public String getRoomType() {
            return this.roomType;
        }

        public int getRoomPrice() {
            return this.roomPrice;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public boolean getRoomAvailable() {
            return this.roomAvailable;
        }

        public void to_string() {
            System.out.println("Customer info: room number is" + roomNumber + " room type is " + roomType + " room price is " + roomPrice + " room available status:" +roomAvailable);
        }
    }
