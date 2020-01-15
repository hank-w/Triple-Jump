package main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class GameComponent {
	public abstract String getObjectType();
	
	public abstract void updateView(Graphics g);
	
	public abstract void updateTick();
	
	public abstract Color getColor();
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract void move(int x, int y);
}
