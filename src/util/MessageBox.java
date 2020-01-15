package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Game;
import main.GameButton;
import main.ScaleManager;

public class MessageBox extends UiMessage {
	GameButton[] choiceList;
	
	String message;
	String speaker;
	ArrayList<String> outMessage = new ArrayList<String>();
	double scale;
	int count = 0;
	int typeIndex = 0;
	int breakPoint = 0;
	boolean updateComplete;
	Font font;
	public MessageBox(String message, GameButton[] choices, String speaker) {
		this.speaker = speaker;
		updateComplete = false;
		typeIndex = 0;
		count = 0;
		breakPoint = 0;
		choiceList = choices;
		this.message = message;
		scale = ScaleManager.getMin();
		font = new Font("Courier New", Font.BOLD, (int) (15 * scale));
		enterNewLine();
	}

	@Override
	public String getObjectType() {
		// TODO Auto-generated method stub
		return "MessageBox";
	}

	@Override
	public void updateView(Graphics g) {
		if (scale != ScaleManager.getMin()) {
			
			scale = ScaleManager.getMin();
		}
		font = new Font("Courier New", Font.BOLD, (int) (15 * scale));
		int xOffset = ScaleManager.screenOffsetX;
		int yOffset = ScaleManager.screenOffsetY;
		g.setColor(new Color(50,50,50,200));
		g.drawRect((int)(50 * scale) + xOffset, (int)(300 * scale) + yOffset, (int)(500 * scale), (int)(200 * scale));
		g.fillRect((int)(50 * scale) + xOffset, (int)(300 * scale) + yOffset, (int)(500 * scale), (int)(200 * scale));
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(speaker, (int)(60 * scale) + xOffset, (int)(320 * scale) + yOffset);
		
		
		
		for (int i = 0; i < outMessage.size(); i++) {
			g.setFont(font);
			g.drawString(outMessage.get(i), (int)(60 * scale) + xOffset, (int)((340 + i * 20)*scale) + yOffset);
		}
		
		
	}

	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		Game.paused = true;
		if (typeIndex <= message.length()) {
			if (count > 2) {
				count = 0;
				typeLetter();
			} else {
				count++;
			}
		} else {
			if (!updateComplete) {
				for (int i = 0; i < choiceList.length; i++) {
					Game.gameComponentList.add(choiceList[i]);
				}
				updateComplete = true;
			}
		}
	}

	private void typeLetter() {
		String currentMessage = message.substring(breakPoint,typeIndex);
		outMessage.set(outMessage.size() - 1, currentMessage);
		if (currentMessage.endsWith(" ") && currentMessage.length() > 48) {
			enterNewLine();
		}
		typeIndex++;
		
		
	}
	
	private void enterNewLine() {
		outMessage.add("");
		breakPoint = typeIndex;
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
	
	public void closeMessage() {
		Game.paused = false;
		for (int i = 0; i < choiceList.length; i++) {
			Game.gameComponentList.remove(choiceList[i]);
		}
		Game.gameComponentList.remove(this);
	}
}
