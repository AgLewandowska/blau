package game;

import java.io.Console;

//methods return false, 0, empty string, z for default.

public class DecisionMaker {
	
	boolean answer;
	Console c = System.console();
	

	
	public DecisionMaker() {}
	
	public boolean boolDecide() {
	
		if (c != null) {
			String ans = c.readLine("[Y/N]");
			switch (ans.toLowerCase()) {
		
			case "yes" : case "y" : case "sure" : 
			case "ok" : case "true" : case "1" : 
			case "right" : case "a" : case "okay" :
			case "please do" : case "i can" : {
				answer = true;
				break;
			}
				
			case "no" : case "n" : case "nope" : 
			case "0" : case "-1" : case "b" : 
			case "left" : case "go away" : 
			case "not" : case "don't" : case "do not" :
			case "false" : {
				answer = false;
				break;
			}
			default : { answer = false; }
			} //end of switch.
			
		} else { 
			System.err.println("Console not found."); 
			answer = false;
			}
		
		return answer;
	}
	
	public int intDecide() {
		if (c != null) {
			String ans = c.readLine();
			return Integer.valueOf(ans);
		
		}
		else {
			System.err.println("Console not found.");
			return 0;
		}
	}
	
	public char charDecide() {

		if (c != null) {
			String ans = c.readLine();
			return ans.toLowerCase().charAt(0);
		}
		else {
			System.err.println("Console not found.");
			return 'z';
		}
	}

}
