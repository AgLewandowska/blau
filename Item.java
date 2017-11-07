package game;

//subclasses for armour, weapons, misc etc... Or nested classes? Spellbook is not an item it is in inventory.

public class Item {
	
	private String itemName;
	private String itemDescription;
	private String itemProperty;
	
	//no inherent position, this is just floating somewhere
	
	public Item() {}
	
	public Item(String name, String description, String property) {
		itemName = name;
		itemDescription = description;
		itemProperty = property;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public String getItemProperty() {
		return itemProperty;
	}


}
