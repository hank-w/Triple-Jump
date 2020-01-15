package main;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sprites.GameSprite;
import sprites.Player;
import sprites.SpriteController;
import sprites.Tile;
import sprites.TileController;

public class LevelManager {
	
	public static List<Level> changeList = new ArrayList<Level>();
	
	//In Jar Resource
	public static String path = Main.class.getResource("").toString();
	
	static String beginningLevel = "level1";
	
	public static List<ArrayList<Tile>> map = new ArrayList<ArrayList<Tile>>();
	public static List<GameSprite> spriteList = new ArrayList<GameSprite>();
	public static int mapWidth;
	public static int mapHeight;
	static Player p;
	public static String mapInfo = "";
	public static String currentLevel = " ";
	public static void init() {
		changeList = new ArrayList<Level>();
		loadLevel(beginningLevel,"entranceL");
	}
	
	public static void loadLevel(String levelName, String entrance) {
		map.clear();
		spriteList.clear();
		currentLevel = levelName;
		Player.worldLockX = false;
		Player.worldLockY = false;
		
		//Choose Save Access Path
		Level changedLevel = null;
		for (int i = 0; i < changeList.size(); i++) {
			if (changeList.get(i).getName().equals(levelName)) {
				changedLevel = changeList.get(i);
			}
		}
		
		if (changedLevel != null) {
			//=========================================================================
			//These are Level Input Method used when the level has been changed ingame
			//=========================================================================
			//Changed input
			String ln;
			mapWidth = 0;
			mapHeight = 0;
			for (int i = 0; i < changedLevel.content.size(); i++) {
				
				ln = changedLevel.content.get(i);
				if (ln.length() > mapWidth) {
					mapWidth = ln.length();
				}
				ArrayList<Tile> row = new ArrayList<Tile>();
				for (int j = 0; j < ln.length(); j++) {
					String id = findTileID(ln,j);
					if (!id.toLowerCase().equals(id)) {
						Tile tile = TileController.createTile(id, j, map.size());
						row.add(tile);
					} else {
						Tile tile = TileController.createTile(" ", j, map.size());
						row.add(tile);
						//Create Sprite
						GameSprite sprite = SpriteController.createSprite(id, j * Tile.rawsize, map.size() * Tile.rawsize);
						spriteList.add(sprite);
					}
				}
				map.add(row);
				mapHeight++;
			}
			mapInfo = changedLevel.getDiscription();
		} else {
			//=========================================================================
			//These are Level Input Method used with File Loading
			//=========================================================================
			//Scanner input
			Scanner in = null;
			File load = new File(Save.loadDirectory + levelName + ".txt");
			if (Save.loadDirectory != null && load.exists()) {
				//Save File Input
				try {
					in = new Scanner(load);
				} catch (FileNotFoundException e) {
					
				}
			} else {
				//Internal Default Input
				InputStream path= Main.class.getResourceAsStream("levels/" + levelName + ".txt");
				in = new Scanner(path);
			}
			String ln;
			mapWidth = 0;
			mapHeight = 0;
			while (true) {
				try {
					ln = in.nextLine();
					if (ln.startsWith("/")) {
						mapInfo = ln.substring(1);
						break;
					}
				} catch(Exception e) {
					break;
				}
				if (ln.length() > mapWidth) {
					mapWidth = ln.length();
				}
				ArrayList<Tile> row = new ArrayList<Tile>();
				for (int i = 0; i < ln.length(); i++) {
					String id = findTileID(ln,i);
					if (!id.toLowerCase().equals(id)) {
						Tile tile = TileController.createTile(id, i, map.size());
						row.add(tile);
					} else {
						Tile tile = TileController.createTile(" ", i, map.size());
						row.add(tile);
						//Create Sprite
						GameSprite sprite = SpriteController.createSprite(id, i * Tile.rawsize, map.size() * Tile.rawsize);
						spriteList.add(sprite);
					}
//					Tile tile = TileController.createTile(id, i, map.size());
//					//Tile tile = TileController.createTile(ln.substring(i, i + 1), i, map.size());
//					row.add(tile);
//					//row.add(new Tile(ln.substring(i, i + 1), i, map.size()));
				}
				map.add(row);
				mapHeight++;
			}
			in.close();
		}
		int[] entranceCoordinates = getEntranceCoordinate(mapInfo,entrance);
		Game.gameComponentList.remove(p);
		p = new Player((int)ScaleManager.getxConstant()/2 - 10, (int)ScaleManager.getyConstant()/2 - 10, 30, 30, 0, entranceCoordinates[0], entranceCoordinates[1]);
		Game.gameComponentList.add(p);
		Game.levelChange();
	}
	
	private static String findTileID(String line, int index) {
		if (line.substring(index).startsWith("$")) {
			
			for (int i = index; i < line.length(); i++) {
				if (!line.substring(i, i+1).equals("$")) {
					System.out.println(line.substring(i, i + (i - index)));
					return line.substring(i, i + (i - index));
				}
			}
		}
		return line.substring(index, index + 1);
	}
	
	private static int[] getEntranceCoordinate(String mapInfo, String entrance) {
		int resX = Integer.parseInt(dataSearch(mapInfo, entrance + "X"));
		int resY = Integer.parseInt(dataSearch(mapInfo, entrance + "Y"));
		return new int[] {resX,resY};
	}
	
	public static void exitLevel(String mapInfo, String exit, String entrance) {
		String targetLevel = dataSearch(mapInfo,exit);
		if (targetLevel.equals("")) {
			return;
		} else if (targetLevel.equals("death")) {
			Player.die();
			return;
		}
		writeToTemp();
		//Load Target Level
		String targetEntrance = dataSearch(mapInfo,entrance);
		loadLevel(targetLevel,targetEntrance);
	}
	
	public static void writeToTemp() {
		//Save Level To Memory
		
		//Remove Duplicate
		try {
		for (Level e : changeList) {
			if (e.getName().equals(currentLevel)) {
				changeList.remove(e);
			}
		}
		} catch (Exception e) {
			
		}
		
		//Generate Level
		Level level = new Level(currentLevel, mapInfo);
		for (ArrayList<Tile> t : map) {
			String line = "";
			for (Tile e : t) {
				line += e.getID();
			}
			level.writeLine(line);
		}
		changeList.add(level);
	}
	
	public static String dataSearch(String input, String key) {
		String[] list = input.split(";");
		for (int i = 0; i < list.length; i++) {
			String[] element = list[i].split("=");
			if (element[0].equals(key)) {
				return element[1];
			}
		}
		return "";
	}
	
	public static void updateLevel() {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				map.get(i).get(j).update();
			}
		}
		
		for (int i = 0; i < spriteList.size(); i++) {
			spriteList.get(i).update();
		}
	}
	
	public static void drawMap(Graphics g) {
		try {
			for (int i = 0; i < map.size(); i++) {
				for (int j = 0; j < map.get(i).size(); j++) {
					
//					if (map.get(i).get(j).getY() < p.y) {
//						p.updateView(g);
//					}
					map.get(i).get(j).draw(g);
	
				}
			}
			
			for (int i = 0; i < spriteList.size(); i++) {
				spriteList.get(i).draw(g);
			}
		} catch (Exception e) {
			
		}
	}
	
	public static void drawTopMap(Graphics g) {
		try {
			for (int i = 0; i < map.size(); i++) {
				for (int j = 0; j < map.get(i).size(); j++) {
				
//					if (map.get(i).get(j).getY() < p.y) {
//						p.updateView(g);
//					}
					map.get(i).get(j).drawFloating(g);
			
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	public static void redirectInfo(String key, String target) {
		int entry = mapInfo.indexOf(key);
		mapInfo = mapInfo.substring(0,entry + key.length() + 1) + target + mapInfo.substring(mapInfo.indexOf(";", entry));
	}
}
