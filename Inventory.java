package game;

import java.io.Console;
import java.util.ArrayList;

public class Inventory {
	
	Console c = System.console();

	protected int inventoryCapacity;
	protected ArrayList<Item> inventoryArray;
	protected boolean invFull;
	
	//constructors
	public Inventory() {}
	
	public Inventory(int startingCapacity) {
		inventoryCapacity = startingCapacity;
		inventoryArray = new ArrayList<Item>();
		invFull = false;
	}
	//end of constructors
	
	public ArrayList<Item> getArray() {
		return inventoryArray;
	}
	
	public void addToInventory(Item item) {
		if (!invFull) {
			boolean added = false;
			for (int i = 0; i < inventoryArray.size(); i++) {
				if (inventoryArray.get(i).getItemProperty() == "nothing") {
					inventoryArray.set(i, item);
					added = true;
					checkInventoryStatus();
					break;
				}
			}
			if (inventoryArray.size() < inventoryCapacity && !added) {
				inventoryArray.add(item);
				added = true;
				checkInventoryStatus();
			}
		}
	}//end of addToInventory method
	
	public void checkInventoryStatus() {
		if (inventoryArray.size() == inventoryCapacity) {
			invFull = true;
		}
	}
	
	public void removeFromInventory(int...index) {
		for (int i : index) {
			if ( i < inventoryArray.size()) {
				inventoryArray.set(i, new Item("Empty", "Space", "nothing"));
			}
			else { System.err.println("Index " + i + " out of bounds, cannot remove item from creature inventory."); }
		}
	}
	
	//this is overridden in player inventory. No it is not.
	public void printItems() {
		System.out.println("Inventory capacity: " + inventoryCapacity);
		inventoryArray.forEach(item -> { if (item.getItemProperty() != "nothing") {
											System.out.println(inventoryArray.indexOf(item) + " " + item.getItemName());
										 }
									   } );
	}
	public void printItems(Boolean descriptions) {
		if (!descriptions) { printItems(); }
		else {
			System.out.println("Inventory capacity: " + inventoryCapacity);
			inventoryArray.forEach(item -> { if (item.getItemProperty() != "nothing") {
												System.out.println(inventoryArray.indexOf(item) + " " + item.getItemName());
											 }
										   } );
		}
	}
	
	public void removeEmptyItems() {
		inventoryArray.removeIf( item -> { return (item.getItemProperty() == "nothing"); } ); 
											
	}
	
	public void setInventoryCapacity(int newCapacity) {
		removeEmptyItems();
		if (inventoryCapacity > newCapacity && newCapacity < inventoryArray.size()) {
			int diff = inventoryArray.size() - newCapacity;
			System.out.println("Must remove " + diff + " items.");
			if (c != null) {
				while (newCapacity < inventoryArray.size()) {
				
					printItems(true);
					String ans2 = c.readLine("Index to remove: ");
					int ansInt = Integer.valueOf(ans2);
					while (ansInt >= inventoryCapacity || ansInt == 0) {
						System.out.println("Invalid index or spellbook, try again: ");
						ansInt = Integer.valueOf(c.readLine());
					}
					removeFromInventory(ansInt);
					removeEmptyItems();
				} 
			} else { System.out.println("Console does not exist."); }
		}
		inventoryCapacity = newCapacity;
	}

}
