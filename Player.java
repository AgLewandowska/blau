package game;

public class Player extends Character {
	
	PlayerInventory inv; //this is overwritten from Character.
	boolean retreat;
	DecisionMaker decision = new DecisionMaker();
		
	public Player(String firstName, String surName) {
		existance = true;
		killThenAsk = true;
		this.firstName = firstName;
		this.surName = surName;
		level = 1;
		hP = 10;
		mP = 10;
		position = new int[2];
		position[0] = 0;
		position[1] = 0;
		role = "Player";
		inv = new PlayerInventory(10);
		retreat = true;
	}
	
	public void move(World world, char direction) {
		switch (direction) {
		case 'N': { if (position[1] == world.mapSize-1) { 
						System.out.println("You are at the edge of the world, you cannot move further.");
						break;
					} 
			++position[1];
			break;
		}
		case 'E': { if (position[0] == world.mapSize-1) { 
						System.out.println("You are at the edge of the world, you cannot move further.");
						break;
					} 
			++position[0];
			break;
		}
		case 'S': { if (position[1] == 0) {
						System.out.println("You are at the edge of the world, you cannot move further.");
						break;
					} 
			--position[1];
			break;
		}
		case 'W': { if (position[0] == 0) {
						System.out.println("You are at the edge of the world, you cannot move further.");
						break;
					} 
			--position[0];
			break;
		}
		} 
		enter(world.map[position[0]][position[1]]);
	}
	
	public void enter(Place place) {
		if (place.getTerrain().getMoodMod() == -1) {
			killThenAsk = true;
		} else if (place.getTerrain().getMoodMod() == 1) {
			killThenAsk = false;
		}
		if (killThenAsk && place.getCreature().existance || place.getCreature().killThenAsk && place.getCreature().existance) {
			this.fightCreature(place);
		} 
		else if (place.getCreature().existance){
			this.speak("Hello");
		} 
		else if (place.getItem().getItemProperty() != "nothing") {
			inv.addToInventory(place.getItem());
		}
			//and everything else you can do at a place.
	}
	
	public void fightCreature(Place place) {
		do {
			
			int creatureWeapon = 0;
			for (Item item : place.getCreature().inv.getArray()) {
				if (item.getItemProperty().toLowerCase() == "weapon") {
					creatureWeapon = 1;
				}
			}
			int playerWeapon = 0;
			for (Item item : inv.getArray()) {
				if (item.getItemProperty().toLowerCase() == "weapon") {
					playerWeapon = 1;
				}
			}
			
			int creatureCombatValue = (int)(Math.random()*2) * (2*place.getCreature().getLevel() + creatureWeapon);
			int playerCombatValue = (int)(Math.random()*3) * (2*getLevel() + playerWeapon + place.getTerrain().getCombatMod());
			
			if (creatureCombatValue < playerCombatValue) { 
							place.getCreature().setHP(place.getCreature().getHP() - 1); 
							System.out.println("Creature HP: " + place.getCreature().getHP());
			}
			else if (creatureCombatValue > playerCombatValue) {
							--hP ;
							System.out.println("Your HP: " + hP);
			}
			
			if (place.getCreature().getHP() <= 0) {
				place.getCreature().existance = false;
				System.out.println("You have killed " + place.getCreature().getName() + ".");
				acquireInventory(place.getCreature());
				break;
				}
			if (hP == 1 && retreat) {
				System.out.println("You are almost dead. Step back. Try talking.");
				break;
			}
		} while (hP > 0);
		
		if (hP == 0) {
			System.out.println("You have been killed. Goodbye.");
			existance = false;
		}
	}
	
	public void acquireInventory(Character creature) {
		for (Item item : creature.inv.inventoryArray) {
			this.inv.addToInventory(item);
			creature.inv.removeFromInventory(creature.inv.inventoryArray.indexOf(item));
		}
	}
	
	public void lookAround(World currentWorld) {
		for (Place[] place1 : currentWorld.map) {
			for (Place place2 : place1) {
				if ( 1 >= Math.abs(this.position[0] - place2.getPosition()[0]) &&
						1 >= Math.abs(this.position[1] - place2.getPosition()[1]) ) {
					System.out.println(place2.getCoordinates() + ": " + place2.getCreature().getName() + ", " + place2.getTerrain().getName());
				}
			}
		}
	}
	
	public void talk(Place place) {
		if ( place.getCreature() instanceof Character && 
				place.getCreature().getFirstName().toLowerCase() != "nobody"  && 
				place.getCreature().existance ) {
			
			Boolean victory = false;
			Boolean conversation = true;
			
			Boolean close;
			if (Math.abs(place.getPosition()[0] - position[0]) <= 1 && Math.abs(place.getPosition()[1] - position[1]) <=1 ) {
				close = true;
			} else {
				close = false;
				System.out.println("You are very far away and may be misheard or ignored. Be considerate.");
			}
			
			if (close) { speak("Hello."); 
			place.getCreature().speak("Greetings. Who are you and why are you here? This is my home.");
					int playerPoints = 0;			
				while ((!victory) && conversation) {
				
					System.out.println("A: 'I'm a traveller.'");
					System.out.println("B: 'That is none of your business.'");
					System.out.println("C: 'Who are you?'");
					System.out.println("D: No reply.");
					
					char ans = decision.charDecide();
				
					switch (String.valueOf(ans).toLowerCase().charAt(0)) {
					case 'a' : { //I'm a traveller.
						playerPoints++;
						place.getCreature().speak("Fair enough, where are you going?");
						System.out.println("A: 'Nowhere in particular.'");
						System.out.println("B: 'Towards adventure!'");
						System.out.println("C: 'Hahahaha, that's a secret, if you don't mind.'");
						System.out.println("D: 'That's none of your business.'");
						System.out.println("E: 'Who are you?'");
						System.out.println("F: No reply.");
						
						ans = decision.charDecide();
						
						switch(String.valueOf(ans).toLowerCase().charAt(0)) {
						case 'a' : { //Nowhere in particular.
							place.getCreature().speak("Well then you don't need to be here, get out of my land.");
						}
						case 'b' : { //Towards adventure!
							playerPoints++;
							place.getCreature().speak("Wonderful! Remember to be kind!");
						}
						case 'c' : { //Hahahaha, that's a secret, if you don't mind!.
							playerPoints++;
							place.getCreature().speak("Fair enough, be on your way! Take this shortcut!");
						}
						}
						
						break;
						}
					case 'b' : { //That is none of your business.
						playerPoints--;
						place.getCreature().speak("You are tresspassing on my property, please leave.");
						System.out.println("A: 'Can you show me another way to get past?'");
						System.out.println("B: 'I will fight you if I have to.'");
						System.out.println("C: 'Please, there is no other way, and I must reach my destination.'");
						System.out.println("D: 'Who are you to tell me what to do!?'");
						System.out.println("E: 'Sure, sorry for the intrusion.'");
						System.out.println("F: No reply.");
						
						break;
					}
					case 'c' : { //Who are you?
						playerPoints--;
						place.getCreature().speak("This is my home.");
						System.out.println("A: 'Can you show me the way through this land? I must reach my destination.'");
						System.out.println("B: 'I'm sorry for the intrusion.'");
						System.out.println("C: 'Please, there is no other way, and I must reach my destination.'");
						System.out.println("D: 'So!? I just want to get past!'");
						System.out.println("E: 'Do you have a name?'");
						System.out.println("F: No reply.");
						
						break;
					}
					case 'D' : { //No reply.
						place.getCreature().speak("Are you okay? I asked who are you and what are you doing here?");
						continue;
					}
				}
			
			}
							
							
			} else { 
				speak("HELLO!"); 
				System.out.println("Your voice echoes across the landscape. The creature does not hear you.");
			}


		}
	}
	
	public void react(Place.Terrain terrain, String charMood) {
		switch (charMood) {
		case "fight" : 
		
		}
	}

}
