package game;

public class Place {
	
	private int[] position = new int[2];
	private Item item = new Item("Empty", "Space", "nothing");
	private Character creature = new Character("Nobody", "", "");
	private Terrain terrain;

	public static enum Terrain {
		MEADOW("Meadow", 0, 0, 1), 
		ROAD("Road", 1, 0, 0), 
		SWAMP("Swamp", -1, 1, -1), 
		WASTELAND("Wasteland", 1, -1, -1), 
		FOREST("Forest", -1, 1, 0);
		
		private final int combatMod;
		private final int magicMod;
		private final int moodMod;
		private final String name;
		
		Terrain(String name, int combatMod, int magicMod, int moodMod) {
			this.combatMod = combatMod;
			this.magicMod = magicMod;
			this.moodMod = moodMod;
			this.name = name;
		}
		
		public int getCombatMod() { return combatMod; }
		public int getMagicMod() { return magicMod; }
		public int getMoodMod() { return moodMod; }
		public String getName() { return name; }
		
	}
	
	//CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------
	
	
	
	public Place(int[] position, Item item, Character creature, Terrain terrain) {
		this(position, terrain);
		this.item = item;
		this.creature = creature;
	}
	
	public Place(int[] position, Terrain terrain) {
		this.position[0] = position[0];
		this.position[1] = position[1];
		this.terrain = terrain;
	}
	
	public Place(int[] position, Item item, Terrain terrain) {
		this(position, terrain);
		this.item = item;
	}
	
	public Place(int[] position, Item item) {
		this(position, item, Place.Terrain.MEADOW);
	}
	
	public Place(int[] position, Character creature, Terrain terrain) {
		this(position, terrain);
		this.creature = creature;
	}
	
	public Place(int[] position, Character creature) {
		this(position, creature, Place.Terrain.MEADOW);
	}
	
	public Place(int[] position) {
		this(position, Place.Terrain.MEADOW);
	}
	
	//CONSTRUCTORS END----------------------------------------------------------------------------------------------------------------------------
	
	public int[] getPosition() {
		return position;
	}
	
	public String getCoordinates() {
		return "(" + position[0] + ", " + position[1] + ")";
	}
	
	public Item getItem() {
		return item;
	}
	
	public void destroyItem() {
		item = new Item("Ash", "Dead things.", "nothing");
	}
	
	public Character getCreature() {
		return creature;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}
