package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GameTag extends GameComponent {

	int x;
	int y;
	int w;
	int h;
	int rawW;
	int rawH;
	String t;
	Font font;
	Color color;
	
	public GameTag(int x, int y, String text, Color color, int width, int height) {
		this.x = x;
		this.y = y;
		rawW = width;
		rawH = height;
		this.w = (int) (rawW * ScaleManager.getMin());
		this.h = (int) (rawH * ScaleManager.getMin());
		int fontSize = 0;
		t = text;
		fontSize = h;
		font = new Font("Courier New", Font.BOLD, fontSize);
		this.color = color;
	}
	
	public void setText(String newText) {
		t = newText;
	}
	
	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "GameTag";
	}

	@Override
	public void updateView(Graphics g) {
		// TODO Auto-generated method stub
		if (w != (int)(rawW * ScaleManager.getMin())) {
			w = (int) (rawW * ScaleManager.getMin());
			h = (int) (rawH * ScaleManager.getMin());
			int fontSize = 0;
			fontSize = h;
			font = new Font("Courier New", Font.BOLD, fontSize);
		}
		
		g.setColor(color);
		int screenX = (int)(x * ScaleManager.getMin() + ScaleManager.screenOffsetX);
		int screenY = (int)(y * ScaleManager.getMin() + ScaleManager.screenOffsetY);
		g.setFont(font);
		g.drawString(t, screenX + w / 6, screenY + font.getSize() + h/6);
	}

	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
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
		this.x = x;
		this.y = y;
	}

}
