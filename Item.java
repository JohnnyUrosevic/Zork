public class Item {
	private String name;
	private String description;
	private double weight;
	
	//attributes
	public boolean canEat;
	public boolean opensDoor;
	public boolean canAttack;
	
	public Item(String n, String d, double w, boolean food, boolean key, boolean weapon) {
		name = n;
		description = d;
		weight = w;
		
		canEat = food;
		opensDoor = key;
		canAttack = weapon;
	}
	//constructor used for searching for items in inventory
	public Item(String n) {
		name = n;
	}
	
	public String inspect() {
		return description;
	}
	
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public boolean equals(Object o) { //equal if they have the same name
		Item i = (Item) o;
		return i.getName().equals(name);
	}
	
	
}
