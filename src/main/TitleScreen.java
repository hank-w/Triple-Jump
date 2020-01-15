package main;

import java.awt.Color;

import sprites.Player;

public class TitleScreen {
	public static GameButton startButton;
	public static GameButton loadButton;
	public static GameButton settingButton;
	public static GameButton fullscreenButton;
	public static GameButton menuButton;
	public static GameButton exitButton;
	public static GameButton uButton;
	public static GameButton dButton;
	public static GameButton lButton;
	public static GameButton rButton;
	
	public static GameTag uTag;
	public static GameTag dTag;
	public static GameTag lTag;
	public static GameTag rTag;
	
	public static GameTag icon;
	
	public static boolean gameOn;
	public static Player p;
	public static int select = -1;
	public static void setup() {
		gameOn = false;
		startButton = new GameButton("Play",350,100,100,50, "Start");
		Game.gameComponentList.add(startButton);
		settingButton = new GameButton("Settings",350,175,100,50, "Setting");
		Game.gameComponentList.add(settingButton);
		exitButton = new GameButton("Quit",350,250,100,50, "Exit");
		Game.gameComponentList.add(exitButton);
		
		icon = new GameTag(0,150,"Super Runner",Color.ORANGE,400,16);
		Game.gameComponentList.add(icon);
		
		p = new Player(100,100,50,50,90, 0, 0);
		Game.gameComponentList.add(p);
	}
	
	public static void setting() {
		fullscreenButton = new GameButton("Full Screen",50,100,150,50, "Fullscreen");
		Game.gameComponentList.add(fullscreenButton);
		
		menuButton = new GameButton("Back",50,250,100,50, "Setup");
		Game.gameComponentList.add(menuButton);
		
		//Up
		uButton = new GameButton("Forward",200,200,100,40, "SelectUp");
		Game.gameComponentList.add(uButton);
		
		uTag = new GameTag(320,190,"W",Color.BLACK,100,40);
		Game.gameComponentList.add(uTag);
		
		
		//Down
		dButton = new GameButton("Backward",200,250,100,40, "SelectDown");
		Game.gameComponentList.add(dButton);
		
		dTag = new GameTag(320,240,"S",Color.BLACK,100,40);
		Game.gameComponentList.add(dTag);
		
		
		//Left
		lButton = new GameButton("Left",200,300,100,40, "SelectLeft");
		Game.gameComponentList.add(lButton);
		
		lTag = new GameTag(320,290,"A",Color.BLACK,100,40);
		Game.gameComponentList.add(lTag);
		
		
		//Right
		rButton = new GameButton("Right",200,350,100,40, "SelectRight");
		Game.gameComponentList.add(rButton);
		
		rTag = new GameTag(320,340,"D",Color.BLACK,100,40);
		Game.gameComponentList.add(rTag);
	}
	
	public static void update() {
		if (uTag != null) {
			uTag.setText(String.valueOf((char)Game.keyList[0]));
			dTag.setText(String.valueOf((char)Game.keyList[1]));
			lTag.setText(String.valueOf((char)Game.keyList[2]));
			rTag.setText(String.valueOf((char)Game.keyList[3]));
		}
	}
}
