import java.util.Scanner;

public class Commands {
	public static final String[] COMMANDS = {"north", "west", "east", "south", "inventory", "take", "drop", "weight", "inspect", "help", "talk", "attack", "quit", "examine", "leave", "use", "eat", "swing", "insert"};
	//public static final String[] OBJECTS = {"gold coin", "sword", "gears", "cherries","sails","patches","water","engine","revolver"};
	
	public static Room[][] map;
	
	public static int row;
	public static int col;
	public static int lastRow;
	public static int lastCol;
	
	public static Room currentRoom;
	
	public static void main(String[] args) {

		//init map
		map = new Room[5][5];
		
		row = 2;
		col = 2;
		
		lastRow = 2;
		lastCol = 2;
	
		//default rooms
		map[0][0] = new Room();
		map[0][1] = new Room();
		map[0][3] = new Room();
		map[0][4] = new Room();
		map[1][3] = new Room();
		map[1][4] = new Room();
		map[1][0] = new Room();
		map[1][1] = new Room();
		map[3][0] = new Room();
		map[3][1] = new Room();
		map[3][3] = new Room();
		map[3][4] = new Room();
		map[4][0] = new Room();
		map[4][1] = new Room();

		
		//rooms that matter
		map[0][2] = new Room(false, true, false, false, "Cliff", "You reach a dead end at a cliff. Below you see a large town with civilization. Item: blueberries", new Item("blueberries", "Freshly picked blueberries", 0.5, true, false, false));
		map[1][2] = new Room(true, true, false, false, "Abandoned house", "A burnt out fireplace resides to your left. The mantel contains pictures of a married couple. On the right is a table with assorted drawings. Item: bread", new Item("bread", "Freshly baked bread", 1, true, false, false));
		map[2][2] = new Room(true, true, true, true, "Train Station", "A basic train station filled with passengers. A sign reads \"Town north, Plains East, Factory South, and Market East.\" Underneath the sign is a metal bench with a potted plant nearby.");
		map[2][1] = new Room(false, false, true, true, "Plains", "The field is large and open with roaming wildlife and beautiful cherry trees. To the west you can see a large object. Item: Cherries", new Item("cherries", "Freshly picked cherries", 0.5, true, false, false));
		map[2][0] = new Room(false, false, false, true, "Crashed Airship", "You find a crashed Airship which is mostly intact. The sails are torn and there is a hole where the engine should be. Item: strawberries", new Item("strawberries", "Freshly picked strawberries", 0.5, true, false, false));
		map[2][3] = new Room(false, false, true, true, "Market", "A small building with assorted shelves. The only person present is the clerk.");
		map[2][4] = new Room(false, false, true, false, "Mechanic shop", "A shop with a skilled mechanic. Assorted mechanisms litter the building. Item: Key", new Item("key", "A large key that looks like it might be important", 1.0, false, true, false));
		map[3][2] = new Room(true, true, false, false, "Factory", "A factory with a large impeding door. Items: stick", new Item("stick", "A large wooden stick. Could work as a makeshift sword if you tried hard enough", 1.5, false, false, true), false, true, false, false);
		map[4][2] = new Room(true, false, false, true, "Army Base", "There's no sign of any active life in the base, yet it's obvious that someone or something had been here. There is an unsettling lack of dust, and nothing is in any state of disrepair. There is a door to your right which is slightly ajar. Item: rock", new Item("rock", "Something to weigh you down", 3.0, false, false, false));
		map[4][3] = new Room(false, false, true, false, "Armory", "You enter the armory to find a contingent of soldiers wearing metal plating and facemasks. Suddenly, you realize that they're standing around a mass of metal, which, under closer examination, appears to be some kind of battle mech. Item: bustersword", new Item("bustersword", "An advanced military grade weapon, very large making it cumbersome to carry but has a lot of power to its swing", 4.0, false, false, true));
		map[4][4] = new Room(false, false, false, false, "The End", "You have beaten the game!");
		
		//game variables
		Scanner s = new Scanner(System.in);
		boolean running = true;
		boolean valid;
		String[] tokens;
		String input = "";
		
		while(running) {	
		
			currentRoom = map[row][col];
			System.out.println(currentRoom.getName());
			System.out.println(currentRoom.getDescription());
		
			//get commands
			do {
				System.out.print("Enter Command: ");
				input = s.nextLine();
				tokens = input.split("\\s+");
				valid = checkCommand(tokens[0]);
				if (!valid) {
					System.out.println("I don't know how to " + tokens[0]);
				}
			} while (!valid);
			String command = tokens[0].toLowerCase();
			//System.out.println(command);
			if (command.equals("north")) {
				north(currentRoom);
			}
			else if (command.equals("east")) {
				east(currentRoom);
			}
			else if (command.equals("west")) {
				west(currentRoom);
			}
			else if (command.equals("south")) {
				south(currentRoom);
			}
			else if (command.equals("quit")) {
				System.out.println("Bye bye!~");
				running = false;
			}
			else if (command.equals("help")) {
				help();
			}
			else if (command.equals("leave")) {
				leave();
			}
			else if(command.equals("examine")) {
					examine(currentRoom);
			}
			else if(command.equals("weight")) {
				System.out.println("I am carrying " + Inventory.getWeight() + " lbs. I can carry " + Inventory.MAX_WEIGHT + " lbs.");
			}
			else if(command.equals("inventory")) {
				Inventory.printInventory();
			}
			else {
				String object;
				if (tokens.length > 1) {
					object = tokens[1].toLowerCase();
				}
				else {
					object = "nothing";
				}
				if(command.equals("take")) {
					take(object);
				}
				else if(command.equals("drop")) {
					drop(object);
				}
				else if(command.equals("eat")) {
					eat(object);
				}
				else if(command.equals("use") || command.equals("insert")) {
					open(object);
				}
				else if(command.equals("swing")) {
					swing(object);
				}
				else if(command.equals("inspect")) {
					inspect(object);
				}
			}
		}
		s.close();
	}

	private static void south(Room r) {
		if(r.getCanGoSouth()) {
			if (r.getSouthLocked()) {
				System.out.println("That way is locked");
			}
			else {
				lastRow = row;
				row++;	
			}
		}
		else {
			System.out.println("You can't go that way");
		}
	}
	private static void west(Room r) {
		if(r.getCanGoWest()) {
			if (r.getWestLocked()) {
				System.out.println("That way is locked");
			}
			else {
				lastCol = col;
				col++;
			}
		}
		else {
			System.out.println("You can't go that way");
		}
	}
	private static void east(Room r) {
		if(r.getCanGoEast()) {
			if (r.getEastLocked()) {
				System.out.println("That way is locked");
			}
			else {
				lastCol = col;
				col--;
			}
		}
		else {
			System.out.println("You can't go that way");
		}
	}
	private static void north(Room r) {
		if(r.getCanGoNorth()) {
			if (r.getNorthLocked()) {
				System.out.println("That way is locked");
			}
			else {
				lastRow = row;
				row--;
			}
		}
		else {
			System.out.println("You can't go that way");
		}
	}
	public static boolean checkCommand(String input) {
		input = input.toLowerCase();
		for (int i = 0; i < COMMANDS.length; i++) {
			if (input.equals(COMMANDS[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static void help() {
		System.out.println("Available commands: north west east south inventory take drop weight quit examine leave use eat swing insert");
	}
	
	public static void leave() {
		int tempr = row;
		int tempc = col;
		
		row = lastRow;
		col = lastCol;
		
		lastRow = tempr;
		lastCol = tempc; //no
	}
	
	public static void take(String object) {
		Item item = currentRoom.playerTake(object);
		
		if (item == null) {
			System.out.println("There is no " + object);
		}
		else {
			System.out.println(Inventory.add(item));
		}
	}
	
	public static void drop(String object) {
		System.out.println(Inventory.drop(object));
	}

	
	public static void examine(Room r) {
		System.out.println(r);
	}
	
	private static void inspect(String object) {
		System.out.println(Inventory.inspect(object));
	}
	
	private static void open(String object) {
		if (Inventory.findKey(object)) {
			System.out.println(currentRoom.unlock());
		}
		else {
			System.out.println("I do not have that key");
		}
	}
	
	private static void eat(String object) {
		System.out.println(Inventory.eat(object));
	}
	
	private static void swing(String object) {
		System.out.println(Inventory.swing(object));
		if (row == 4 && col == 3) {
			System.out.println("Your " + object + " has cut through the mech and the weapon is now futile! The civilians are saved!");
			col++;
			lastRow = row;
			lastCol = col;
		}
	}
}