import java.util.ArrayList;
import java.util.Collections;

public class Inventory {
	
	private static ArrayList<Item> inventory = new ArrayList<>();
	private static double weight = 0;
	
	public static final double MAX_WEIGHT = 5.0;
	
	public static double getWeight() {
		return weight;
	}
	
	public static void printInventory() {
		if (inventory.size() == 0) {
			System.out.println("Empty.");
		}
		for (Item i : inventory) {
			System.out.println(i.getName());
		}
	}
	
	public static String drop(String name) {
		Item target = new Item(name);
		if(inventory.contains(target)) {
			int i = inventory.indexOf(target);
			Item item = inventory.remove(i);
			Commands.currentRoom.takeFromPlayer(item);
			
			weight -= item.getWeight();
			
			return "Dropped the " + name;
		}
		else {
			return "I don't have a " + name;
		}
	}
	
	public static String add(Item i) {
		if (weight + i.getWeight() < 5) {
			inventory.add(i);
			weight += i.getWeight();
			
			Collections.sort(inventory, (a, b) -> a.getName().compareTo(b.getName()));
			
			return "Added to my inventory";
		}
		else {
			Commands.currentRoom.takeFromPlayer(i);
			return "My inventory is too heavy";
		}
	}
	
	public static String inspect(String name) {
		Item target = new Item(name);
		if(inventory.contains(target)) {
			int i = inventory.indexOf(target);
			return inventory.get(i).inspect();
		}
		else {
			return "I don't have a " + name;
		}
	}
	
	public static String eat(String name) {
		Item target = new Item(name);
		if(inventory.contains(target)) {
			int i = inventory.indexOf(target);
			Item item = inventory.remove(i);
			
			if (item.canEat) {
				//maybe do something useful
				return "Ate the " + name;
			}
			else {
				return "The " + name + " does not look like it tastes very good";
			}
		}
		else {
			return "I don't have a " + name;
		}
	}
	
	public static String swing(String name) {
		Item target = new Item(name);
		if(inventory.contains(target)) {
			int i = inventory.indexOf(target);
			Item item = inventory.get(i);
			
			if (item.canAttack) {
				//maybe do something useful
				return "Swung the " + name;
			}
			else {
				return "The " + name + " is not a weapon";
			}
		}
		else {
			return "I don't have a " + name;
		}
	}
	
	
	public static boolean findKey(String name) {
		Item target = new Item(name);
		if(inventory.contains(target)) {
			int i = inventory.indexOf(target);
			Item item = inventory.get(i);
			
			if (item.opensDoor) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}
	
}
