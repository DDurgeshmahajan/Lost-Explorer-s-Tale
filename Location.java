import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private boolean locked;
    private ArrayList<String> items;
    private HashMap<String, Location> exits;

    public Location(String name, String description, boolean locked) {
        this.name = name;
        this.description = description;
        this.locked = locked;
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }

    public void addExit(String direction, Location destination) {
        exits.put(direction, destination);
    }

    public Location getExit(String direction) {
        return exits.get(direction);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public boolean removeItem(String item) {
        return items.remove(item);
    }

    public String getItemsAsString() {
        if (items.isEmpty()) {
            return "There are no items here.";
        } else {
            return "You see the following items: " + String.join(", ", items);
        }
    }

    public String getExitsAsString() {
        return String.join(", ", exits.keySet());
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}