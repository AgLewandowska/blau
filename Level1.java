package game;

import static java.lang.Math.random;

import java.util.ArrayList;

public class Level1 extends World {
	
	public Level1() {
		mapSize = 4;
		mapIndices = new int[mapSize];
		for (int i = 0; i < mapSize; i++) {
			mapIndices[i] = i;
		}
		map = new Place[mapSize][mapSize];
		
		for (int i : mapIndices) {
			for (int j : mapIndices) {
				int seedItem = (int)(random()*10);
				int seedCreature = (int)(random()*10);
				int seedCreatureLvl = (int)(random()*10);
				int seedTerrain = (int)(random()*4);
				int[] pos = {i, j};
				
				if (j == 3 && i == 3) {
					Character boss = new Character("Etienne", "the Demon", "Boss"); 
					boss.setLevel(1);
					boss.setPosition(pos);
					boss.setHP(20);
					boss.setMP(20);
					Item key = new Item("Key", "(1) Pick the right door.", "guarded key to level 2");
					boss.inv.addToInventory(key);
					map[i][j] = new Place(pos, new Item("Gold", "One gold piece.", "guarded gold"),
							boss, Place.Terrain.MEADOW);
				} 
				
				else if (j < mapSize && i < mapSize) {
					Item loneItem = seedItem < 3 ? new Item("Gold", "One gold piece.", "gold") : 
						new Item("Twig", "For bonfires.", "none");
					Item guardedItem = new Item("Gold", "One gold piece.", "guarded gold");
					Character guard = seedCreature < 2 ? new Character("Goblin", "", "Monster") : 
						new Character("Nobody", "", "");
					Place.Terrain terrain = Place.Terrain.values()[seedTerrain];
					
					if (!guard.existance) {
					map[i][j] = new Place(pos, loneItem, guard, terrain);
					} 
					else {
					guard.setPosition(pos);
						if (seedCreatureLvl < 2) { guard.setLevel(0); }
						else					 { guard.setLevel(1); }
						guard.inv.addToInventory(guardedItem);
					map[i][j] = new Place(pos, loneItem, guard, terrain);
					}
				}
				System.out.println(map[i][j].getCoordinates() + " " + map[i][j].getTerrain().getName()
						+ ": " + map[i][j].getCreature().getName() + ", " 
						+ map[i][j].getItem().getItemName()); 	//This is only for testing, 
				                                              	//remove for real game, make 
																//print landscape method, and looking around
																//methods.
				
			}
		}
	}
	
	
//SPELLS-----------------------------------------------------------------------------------------------------------------------------------------
	
	private SpellBook spellBook = new SpellBook();
	
//SPELLBOOK CLASS DEFINITION WITH ANONYMOUS SPELL CLASSES TO DEFINE SPELLS

	public class SpellBook {
		
		private ArrayList<SpellInterface> spellList = new ArrayList<SpellInterface>();
		
		public SpellBook() {
			spellList.add(teleport);
			spellList.add(killCreature);
		} //constructor
		
		public void printSpells() {
			spellList.forEach(item -> { System.out.println(item.getName());
										System.out.println("- " + item.getDescription());
									});
		}
			
		public SpellInterface teleport = new SpellInterface() {
			private String spellName = "Telport";
			private String spellDescription = "Teleports to given coordinates. Beware of monsters.";
			private int spellLevel = 1;
			private int manaCost = 2;
			
			public void castSpell(int cOne, int cTwo, Player caster, Character... people) {
				int[] pos = {cOne, cTwo};
				if (caster.getMP() >= manaCost && caster.getLevel() >= spellLevel) {
					caster.setPosition(pos);
					caster.setMP(caster.getMP() - manaCost);
					System.out.println("Teleport has been cast. "
							+ "You are now at coordinates: " + caster.getCoordinates());
					boolean surprise;
					if (!caster.killThenAsk){
						surprise = true;
						caster.setRage(true);
					} else { surprise = false; }
					caster.enter(map[pos[0]][pos[1]]);
					if (surprise) {
						caster.setRage(false);
					}
				} else if (caster.getMP() < manaCost) {
					System.out.println("Not enough mana. You must walk, like an average person.");
				} else if (caster.getLevel() < spellLevel) {
					System.out.println("You are not good enough to cast this spell. Be careful.");
				}
			}
			
			public String getName() {
				return spellName;
			}
			
			public String getDescription() {
				return spellDescription;
			}
			
			public int getSpellLevel() {
				return spellLevel;
			}
			
			public int getManaCost() {
				return manaCost;
			}
		};

		public SpellInterface killCreature = new SpellInterface() {
			public String spellName = "Kill Creature";
			public String spellDescription = "Murder. Item is destroyed.";
			public int spellLevel = 1;
			public int manaCost = 1;
			
			//extra methods can only be used locally, this is using interface as a type, 
			//not implementing an interface.
			public void castSpell(int cOne, int cTwo, Player caster, Character... people) {//creature
				if (caster.getMP() >= manaCost && caster.getLevel() >= spellLevel) {
					caster.setMP(caster.getMP() - manaCost);
					people[0].setHP(0);
					people[0].existance = false;
					map[cOne][cTwo].destroyItem();
					System.out.println("Kill Creature has been cast. You are now a murderer.");
				} else if (caster.getMP() < manaCost) {
					System.out.println("Not enough mana, you monster.");
				} else if (caster.getLevel() < spellLevel) {
					System.out.println("Not good enough.");
				}
			}
			
			public String getName() {
				return spellName;
			}
			
			public String getDescription() {
				return spellDescription;
			}
			
			public int getSpellLevel() {
				return spellLevel;
			}
			
			public int getManaCost() {
				return manaCost;
			}
			
		};

		
	}//end of SpellBook
	
	
	
	
//MAIN METHOD WHERE THINGS HAPPEN-----------------------------------------------------------------------------------------------------------------	
	
	public static void main(String[] args) {
		
		Level1 currentLevel = new Level1();
		Player blau = new Player("Blau", "Everest");
		
		blau.move(currentLevel, 'N');
		blau.move(currentLevel, 'E');
		blau.move(currentLevel, 'E');
		blau.speak("hello.");
		
		currentLevel.spellBook.teleport.castSpell(2, 2, blau);
		
		blau.inv.printItems();
		blau.inv.removeFromInventory(0);
		blau.inv.printItems();
		blau.lookAround(currentLevel);
		
		DecisionMaker d = new DecisionMaker();
		int a = d.intDecide();
		System.out.println(a);

	}

}
