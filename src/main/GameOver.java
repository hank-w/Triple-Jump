package main;

import java.awt.Color;

import util.VisualEffects;

public class GameOver {
	public static void enterTransition() {
		VisualEffects.playTransition(255, Color.BLACK,()->setup());
		
	}
	
	public static void setup() {
		Game.gameComponentList.clear();
		Game.inGame = false;
		Canvas.background = Color.BLACK;
		GameTag infoTag = new GameTag(150, 100, "Game Over", Color.RED, 300, 30);
		GameButton titleButton = new GameButton("Title Screen", 230, 300, 100, 40, "Transition To Title");
		GameButton restartButton = new GameButton("Restart", 230, 350, 100, 40, "Start");
		Game.gameComponentList.add(titleButton);
		Game.gameComponentList.add(restartButton);
		Game.gameComponentList.add(infoTag);
	}
}
