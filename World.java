package game;

//Boss must always be on a MEADOW to make talking compulsory.

public class World {
	
	public World() {}
	
	
	protected Place[][] map;
	protected int mapSize;
	protected int[] mapIndices;
	public Player player;
	
	interface SpellInterface {
		public void castSpell(int cOne, int cTwo, Player caster, Character... people);
		public String getName();
		public String getDescription();
		public int getSpellLevel();
		public int getManaCost();
	}

}
