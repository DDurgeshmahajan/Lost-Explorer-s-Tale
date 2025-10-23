import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    private Location currentLocation;
    private ArrayList<String> inventory;
    private boolean gameWon;

    public GameManager() {
        inventory = new ArrayList<>();
        setupWorld();
    }

    private void setupWorld() {
        Location jungleEntrance = new Location("Jungle Entrance", "You stand at the edge of a dense jungle.", false);
        Location riverBank = new Location("River Bank", "The clear river flows gently here.", false);
        Location hiddenCave = new Location("Hidden Cave", "Dark and mysterious, the cave beckons you to explore.", true);
        Location ancientTemple = new Location("Ancient Temple", "The ancient temple stands in ruins, but a faint glow can be seen from within.", false);
        Location waterfall = new Location("Waterfall", "A majestic waterfall cascades into a deep pool.", false);

        jungleEntrance.addItem("machete");
        riverBank.addItem("rusty key");
        hiddenCave.addItem("ancient map");
        ancientTemple.addItem("glowing crystal");

        jungleEntrance.addExit("north", riverBank);
        jungleEntrance.addExit("east", hiddenCave);
        riverBank.addExit("south", jungleEntrance);
        hiddenCave.addExit("west", jungleEntrance);
        hiddenCave.addExit("north", waterfall);
        waterfall.addExit("south", hiddenCave);
        waterfall.addExit("east", ancientTemple);
        ancientTemple.addExit("west", waterfall);

        currentLocation = jungleEntrance;
    }

    public void runGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Lost Explorer's Tale!\n");
        System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
        System.out.println("Exits: " + currentLocation.getExitsAsString());
        System.out.println(currentLocation.getItemsAsString());

        while (!gameWon) {
            System.out.print("What do you do? > ");
            String command = scanner.nextLine().trim().toLowerCase();
            processCommand(command);
        }

        System.out.println("Congratulations! You have completed the game.");
        scanner.close();
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ", 2);
        String action = parts[0];

        switch (action) {
            case "go":
                if (parts.length == 2) {
                    String direction = parts[1];
                    Location nextLocation = currentLocation.getExit(direction);

                    if (nextLocation == null) {
                        System.out.println("You can't go that way.");
                    } else if (nextLocation.isLocked()) {
                        System.out.println("The path is blocked. Perhaps a key is needed.");
                    } else {
                        currentLocation = nextLocation;
                        System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                        System.out.println("Exits: " + currentLocation.getExitsAsString());
                        System.out.println(currentLocation.getItemsAsString());
                    }
                } else {
                    System.out.println("Invalid command. Usage: go <direction>");
                }
                break;
            case "look":
                System.out.println(currentLocation.getName() + ": " + currentLocation.getDescription());
                System.out.println("Exits: " + currentLocation.getExitsAsString());
                System.out.println(currentLocation.getItemsAsString());
                break;
            case "inventory":
            case "i":
                if (inventory.isEmpty()) {
                    System.out.println("Your inventory is empty.");
                } else {
                    System.out.println("Inventory: " + String.join(", ", inventory));
                }
                break;
            case "take":
                if (parts.length == 2) {
                    String item = parts[1];
                    if (currentLocation.removeItem(item)) {
                        inventory.add(item);
                        System.out.println("You pick up the " + item + ".");
                    } else {
                        System.out.println("There is no " + item + " here.");
                    }
                } else {
                    System.out.println("Invalid command. Usage: take <item>");
                }
                break;
            case "use":
                if (parts.length == 2) {
                    String item = parts[1];
                    if (inventory.contains(item)) {
                        if (item.equals("rusty key") && currentLocation.isLocked()) {
                            currentLocation.setLocked(false);
                            System.out.println("You use the rusty key to unlock the path.");
                        } else {
                            System.out.println("Using the " + item + " has no effect here.");
                        }
                    } else {
                        System.out.println("You don't have a " + item + ".");
                    }
                } else {
                    System.out.println("Invalid command. Usage: use <item>");
                }
                break;
            case "quit":
            case "exit":
                gameWon = true;
                System.out.println("Thank you for playing Lost Explorer's Tale! Goodbye!");
                break;
            default:
                System.out.println("Invalid command. Try again.");
        }

        if (currentLocation.getName().equals("Ancient Temple") && inventory.contains("glowing crystal")) {
            gameWon = true;
        }
    }
}