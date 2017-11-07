package game;

import java.util.ArrayList;

//this stores items, note that there is only one player inventory it could be a nested or 
//anonymous class. NO BECAUSE THATS TOO MUCH CODE IN ONE CLASS.

public class PlayerInventory extends Inventory {
	
	DecisionMaker decision;
	
	//constructor
	public PlayerInventory(int startingCapacity) {
		decision = new DecisionMaker();
		invFull = false;
		inventoryCapacity = startingCapacity;
		inventoryArray = new ArrayList<Item>();
		inventoryArray.add(0, new Item("SpellBook Symbol", 
				"Item which represents the spellbook, but is not actually a spellbook.", "spellbook"));
	}
	
	//Original add method does not print items and descriptions.
	@Override
	public void addToInventory(Item item) {
		if (!invFull) {
			boolean added = false;
			for (int i = 1; i < inventoryArray.size(); i++) {
				if (inventoryArray.get(i).getItemProperty() == "nothing") {
					inventoryArray.set(i, item);
					added = true;
					checkInventoryStatus();
					System.out.println("You have acquired: " + item.getItemName());
					System.out.println("Description: " + item.getItemDescription());	
					break;
				}
			}
			if (inventoryArray.size() < inventoryCapacity && !added) {
				inventoryArray.add(item);
				added = true;
				checkInventoryStatus();
				System.out.println("You have acquired: " + item.getItemName());
				System.out.println("Description: " + item.getItemDescription());
			}
		} 
		else { 
			System.out.println("Inventory is full, remove items? [y/n]");
			
			boolean decision = this.decision.boolDecide();
			
				if (decision) { 
					printItems(true);
					System.out.println("Index to remove: ");
					int ansInt = this.decision.intDecide();
					while (ansInt >= inventoryCapacity || ansInt == 0) { 
						System.out.println("Invalid index or spellbook, try again: ");
						ansInt = this.decision.intDecide();
					}
					removeFromInventory(ansInt);
					addToInventory(item);
				}
				else if (!decision) { System.out.println("Item lost."); }
		}	
	}//end of addToInventory method
	
	@Override
	public void removeFromInventory(int... index) {
		for (int i : index) {
			if (i == 0) { 
				System.out.println("Cannot remove spellbook.");
				break; 
			}
			
			if ( i < inventoryArray.size()) {
				inventoryArray.set(i, new Item("Empty", "Space", "nothing"));
				System.out.println(inventoryArray.get(i).getItemName() + " has been removed from inventory.");
			}
			else { System.err.println("Index " + i + " out of bounds, "
					+ "cannot remove item from creature inventory."); }	
		}
	}
	
}
