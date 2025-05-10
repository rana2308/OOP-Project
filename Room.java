package pharmacymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<String> availablePeriods;

    // Static list to hold all room instances
    private static List<Room> rooms = new ArrayList<>();

    public Room(String name) {
        this.name = name;
        this.availablePeriods = new ArrayList<>();
        // Add the new room to the static list
        rooms.add(this);
    }

    public String getName() {
        return name;
    }

    public void addPeriod(String period) {
        availablePeriods.add(period);
    }

    public List<String> getAvailablePeriods() {
        return availablePeriods;
    }

    // Removes the specified period from availablePeriods list
    public boolean removePeriod(String period) {
        return availablePeriods.remove(period);
    }

    public static List<Room> getRooms() {
        return rooms; // Return the list of rooms
    }

    @Override
    public String toString() {
        return name;
    }
}
