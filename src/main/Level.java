package main;

import java.util.ArrayList;
import java.util.List;

public class Level {
	private String name;
	private String disp;
	public List<String> content = new ArrayList<String>();
	public Level(String levelName, String discription) {
		name = levelName;
		disp = discription;
	}
	
	public void writeLine(String line) {
		content.add(line);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDiscription() {
		return disp;
	}
}
