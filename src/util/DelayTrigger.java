package util;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import main.GameComponent;

public class DelayTrigger extends GameComponent {
	private int count;
	private Runnable action;
	public DelayTrigger(Runnable action, int delay) {
		count = delay;
		this.action = action;
	}
	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "DelayTrigger";
	}
	@Override
	public void updateView(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateTick() {
		count--;
		if (count == 0) {
			action.run();
			Game.gameComponentList.remove(this);
		}
		
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	public static void runDelayedAction(Runnable action, int delay) {
		DelayTrigger d = new DelayTrigger(action, delay);
		Game.gameComponentList.add(d);
	}
}
