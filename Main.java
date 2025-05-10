package pharmacymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    	Room room1 = new Room("Room A");
    	room1.addPeriod("8:00 AM - 9:00 AM");
    	room1.addPeriod("9:00 AM - 10:00 AM");

    	Room room2 = new Room("Room B");
    	room2.addPeriod("10:00 AM - 11:00 AM");

    	Room room3 = new Room("Room C");
    	room3.addPeriod("11:00 AM - 12:00 PM");

    	// Fill DataStore with all available hours
    	for (Room room : Room.getRooms()) {
    	    DataStore.allAvailableHours.addAll(room.getAvailablePeriods());
    	}

    	EventManagmentSystem.main(args);
    }
}




