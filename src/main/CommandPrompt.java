package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import sprites.Player;
import util.Message;
import util.VisualEffects;

public class CommandPrompt implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		enterPrompt(e.getActionCommand());
		
	}
	
	public void enterPrompt(String command) {
		
		if (command.startsWith("Message")) {
			Message.enterChoice(Integer.parseInt(command.substring(7)));
		}
		
		
		switch (command) {
		
		case "Start": {
			Game.clearScreen();
			Save.loadDirectory = null;
			Game.inGame = true;
			Game.inGameMenu();
			LevelManager.init();
			Game.paused = false;
			//Message.throwMessage("Hello, this a message test, welcome to the extremely early version of a game. Feel free to roam around the map...",new String[] {"Close", "I Want To Leave!!!"} , new Runnable[] {()-> Game.dummy(), ()-> enterPrompt("Quit To Title")}, "???:");
			break;
		}
		
		case "Setting": {
			Game.clearScreen();
			TitleScreen.setting();
			break;
		}
		
		case "Fullscreen": {
			TitleScreen.fullscreenButton.pressed = false;
			ControlListener.mouseDown = false;
			if (Game.isFullScreen) {
				Main.j.dispose();
				Main.j = new JFrame("Game");
				Main.j.setSize(600,500);
				Main.j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Main.j.add(Main.c);
				Main.j.addKeyListener(Main.cont);
				Main.j.setVisible(true);
				Game.isFullScreen = false;
			} else {
				Main.j.dispose();
				Main.j = new JFrame("Game");
				Main.j.setExtendedState(JFrame.MAXIMIZED_BOTH);
				Main.j.setUndecorated(true);
				Main.j.add(Main.c);
				Main.j.addKeyListener(Main.cont);
				Main.j.setVisible(true);
				Game.isFullScreen = true;
			}
			break;
		}
		
		case "Setup": {
			Game.clearScreen();
			TitleScreen.setup();
			break;
		}
		
		case "Exit": {
			System.exit(0);
		}
		
		case "keyDown0": {
			Player.keyUp = 1;
			break;
		}
		
		case "keyDown1": {
			Player.keyDown = 1;
			break;
		}
		case "keyDown2": {
			Player.keyLeft = 1;
			break;
		}
		
		case "keyDown3": {
			Player.keyRight = 1;
			break;
		}
		
		case "keyUp0": {
			Player.keyUp = 0;
			break;
		}
		
		case "keyUp1": {
			Player.keyDown = 0;
			break;
		}
		
		case "keyUp2": {
			Player.keyLeft = 0;
			break;
		}
		
		case "keyUp3": {
			Player.keyRight = 0;
			break;
		}
		
		case "SelectUp": {
			TitleScreen.select = 0;
			break;
		}
		
		case "SelectDown": {
			TitleScreen.select = 1;
			break;
		}
		
		case "SelectLeft": {
			TitleScreen.select = 2;
			break;
		}
		
		case "SelectRight": {
			TitleScreen.select = 3;
			break;
		}
		
		case "MouseDown": {
			break;
		}
		
		case "Pause Menu": {
			Game.enterPauseMenu();
			break;
		}
		
		case "Resume Pause": {
			Game.exitPauseMenu();
			break;
		}
		
		case "Quit To Title": {
			Game.init();
			TitleScreen.setup();
			LevelManager.map.clear();
			break;
		}
		
		case "Transition To Title": {
			VisualEffects.playTransition(255, Color.WHITE,()->Main.com.enterPrompt("Quit To Title"));
			break;
		}
		
		
		case "keyDown4": {
			Player.jumpDown = true;
			break;
		}
		
		case "keyUp4": {
			Player.jumpDown = false;
			break;
		}
		
		case "keyDown5": {
			Game.currentPlayer.sprinting = true;
			break;
		}
		
		case "keyUp5": {
			Game.currentPlayer.sprinting = false;
			break;
		}
		
		case "keyDown6": {
			Game.currentPlayer.attack = true;
			break;
		}
		
		case "keyUp6": {
			Game.currentPlayer.attack = false;
			break;
		}
		}
	}

}
