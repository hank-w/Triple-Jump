package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Save {
	static String path;
	public static String loadDirectory;
	static List<GameComponent> saveMenu = new ArrayList<GameComponent>();
	static List<GameComponent> loadMenu = new ArrayList<GameComponent>();
	public static void init() {
		path = (new File(".").getAbsolutePath()).replace(".", "");
	}
	
	public static boolean findSaveFile(String name, String directory) {
		File dir = new File(directory);
		
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File[] list = dir.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].getName().equals(name)) {
				return true;
			}
		}
		
		
		return false;
	}
	
	public static File getSaveFile(String name, String directory) {
		File dir = new File(directory);
		File[] list = dir.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].getName().equals(name)) {
				return list[i];
			}
		}
		
		File generated = new File(directory + name);
		try {
			generated.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generated;
	}
	
	
	
	public static void removeLoadComponents() {
		for (GameComponent e : loadMenu) {
			Game.gameComponentList.remove(e);
		}
		loadMenu.clear();
		Game.paused = false;
	}
	
	
}
