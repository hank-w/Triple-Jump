package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import sprites.Player;
import util.VisualEffects;

public class Game {
	
	
	private static boolean isPlaying;
	
	public static List<GameComponent> gameComponentList;
	
	public static List<GameComponent> pauseMenuItems;
	
	public static List<GameComponent> gui;
	
	public static boolean isFullScreen;
	
	public static int[] keyList;
	
	public static boolean inGame;
	
	public static boolean loading;
	
	public static boolean paused;
	
	public static Player currentPlayer;
	
	private static GameTag levelIndicator;
	
	public static void init() {
		Canvas.background = Color.WHITE;
		gameComponentList = new ArrayList<GameComponent>();
		pauseMenuItems = new ArrayList<GameComponent>();
		gui = new ArrayList<GameComponent>();
		isFullScreen = false;
		inGame = false;
		keyList = new int[7];
		keyList[0] = 'W';
		keyList[1] = KeyEvent.VK_DOWN;//'S';
		keyList[2] = KeyEvent.VK_LEFT;//'A';
		keyList[3] = KeyEvent.VK_RIGHT;//'D';
		keyList[4] = KeyEvent.VK_UP;//' ';
		keyList[5] = 'Z';
		keyList[6] = 'X';
		loading = true;
		paused = false;
		Save.init();
	}
	
	public static void inGameMenu() {
		GameButton menuButton = new GameButton("Menu", 10, 10, 100, 40, "Pause Menu");
		gameComponentList.add(menuButton);
		gui.add(menuButton);
		
		levelIndicator = new GameTag(250,10,LevelManager.currentLevel,Color.MAGENTA, 100, 20);
		gameComponentList.add(levelIndicator);
		gui.add(levelIndicator);
		
		
		GameButton resumeButton = new GameButton("Resume", 250, 200, 100, 40, "Resume Pause");
		pauseMenuItems.add(resumeButton);
		
		GameButton quitButton = new GameButton("Quit", 250, 250, 100, 40, "Quit To Title");
		pauseMenuItems.add(quitButton);
	}
	
	public static void enterPauseMenu() {
		for (GameComponent e : gui) {
			gameComponentList.remove(e);
		}
		
		for (GameComponent e : pauseMenuItems) {
			gameComponentList.add(e);
		}
		paused = true;
	}
	
	public static void exitPauseMenu() {
		for (GameComponent e : gui) {
			gameComponentList.add(e);
		}
		for (GameComponent e : pauseMenuItems) {
			gameComponentList.remove(e);
		}
		paused = false;
	}
	
	public static void clearScreen() {
		Canvas.background = Color.WHITE;
		gameComponentList.clear();
	}
	
	public static void start() {
		
		isPlaying = true;
		
		while (isPlaying) {
			
			update();
			Main.c.repaint();
			try {
				Thread.sleep(5);
			} catch(InterruptedException e) {
				
			}
			
		}
	}
	
	public static void update() {
		if (TitleScreen.gameOn == false) {
			TitleScreen.update();
		}
		
		if (inGame) {
			generalGameUpdate();
			VisualEffects.update();
		}
		for (int i = 0; i < gameComponentList.size(); i++) {
			if (!paused) {
				gameComponentList.get(i).updateTick();
			} else {
				if (!gameComponentList.get(i).getObjectType().equals("Player") && !gameComponentList.get(i).getObjectType().equals("Inventory")) {
					gameComponentList.get(i).updateTick();
				}
//				if (gameComponentList.get(i).getObjectType().equals("Button") || gameComponentList.get(i).getObjectType().equals("GameTag") || gameComponentList.get(i).getObjectType().equals("MessageBox") || gameComponentList.get(i).getObjectType().equals("DelayTrigger")) {
//					gameComponentList.get(i).updateTick();
//				}
			}
			if (i >= gameComponentList.size()) {
				break;
			}
			switch (gameComponentList.get(i).getObjectType()) {
			
			
			
			}
		}
	}
	
	public static void generalGameUpdate() {
		
		LevelManager.updateLevel();
	}
	
	public static void onKeyDown(int key) {
		if (TitleScreen.select != -1) {
			keyList[TitleScreen.select] = key;
			TitleScreen.select = -1;
		}
		for (int i = 0; i < keyList.length; i++) {
			if (key == keyList[i]) {
				Main.com.enterPrompt("keyDown" + i);
				break;
			}
		}
	}
	
	public static void onKeyUp(int key) {
		for (int i = 0; i < keyList.length; i++) {
			if (key == keyList[i]) {
				Main.com.enterPrompt("keyUp" + i);
				break;
			}
		}
	}
	
	public static void stop() {
		isPlaying = false;
	}
	
	public static void levelChange() {
		if (Save.loadDirectory == null) {
			levelIndicator.setText(LevelManager.currentLevel);
		} else {
			levelIndicator.setText(LevelManager.currentLevel + "; Save Slot:" + Save.loadDirectory.substring(Save.loadDirectory.length() - 2, Save.loadDirectory.length() - 1));
		}
	}
	
	
	public static void dummy() {
		
	}
	
}
