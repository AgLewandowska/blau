package game;

//NPC is a subclass

public class Character {
	
	public boolean existance;
	public boolean killThenAsk;
	protected String firstName;
	protected String surName;
	protected int level;
	
	protected int hP;
	protected int mP;
	
	protected int[] position;

	public String role;
	public Inventory inv; //each subclass will have it's own instantiation of this, or overwrite.
	
	public Character() {}
	
	public Character(String firstName, String surName, String role) { //new constructor for each subclass
		if (firstName == "Nobody") {
			existance = false;
		} else {
			existance = true;
		}
		killThenAsk = true;
		this.firstName = firstName;
		this.surName = surName;
		level = 1;
		hP = 10*level;
		mP = 10*level;
		position = new int[2];
		position[0] = 0;
		position[1] = 0;
		this.role = role;
		inv = new Inventory(3);
		if (role.toLowerCase() == "guard" || role.toLowerCase() == "monster") {
			inv.addToInventory(new Item("Gold", "One gold piece.", "guarded gold"));
		} else if (role.toLowerCase() == "boss") {
			inv.addToInventory(new Item("Sword", "For killing things.", "guarded weapon"));
		}
	}
	
	//increase level method to change hP, mP, inventoryCapacity to 10*level or something.
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurName() {
		return surName;
	}
	
	public String getName() {
		String name = firstName + " " + surName;
		return name;
	}
	
	public String getCoordinates() {
		return "(" + position[0] + ", " + position[1] + ")";
	}
	
	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int[] newPosition) {
		position = newPosition;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getHP() {
		return hP;
	}
	
	public void setHP(int newHP) {
		hP = newHP;
	}
	
	public int getMP() {
		return mP;
	}
	
	public void setMP(int newMP) {
		mP = newMP;
	}
	
	public void setLevel(int newLevel) {
		if (level <= newLevel) {
			level = newLevel;
			hP = 10*level;
			mP = 10*level;
			inv.setInventoryCapacity(10*level);
		} else { System.out.println("Warning! New level may only be above current level!"); }
	}
	
	
	
	//actions--------------------------------------------------------------------------------------
	
	
	public void setRage(boolean on) {
		killThenAsk = on;
	}

	
	public void speak(String words) {
		System.out.println(this.firstName + " : " + words);
	}
	

	
}

