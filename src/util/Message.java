package util;

import main.Game;
import main.GameButton;

public class Message {
	
	static Runnable[] choices;
	static UiMessage currentMessage;
	public static void throwMessage(String message, String[] choice, Runnable[] actions, String speaker) {
		GameButton[] buttonList = new GameButton[choice.length];
		for (int i = 0; i < choice.length; i++) {
			buttonList[i] = new GameButton(choice[i], 100, 430 - i*25, choice[i].length() * 18, 18, "Message" + i);
		}
		MessageBox info = new MessageBox(message, buttonList, speaker);
		Game.gameComponentList.add(info);
		choices = actions;
		currentMessage = info;
	}
	
	public static void throwInfo(String message, String[] choice, Runnable[] actions) {
		GameButton[] buttonList = new GameButton[choice.length];
		for (int i = 0; i < choice.length; i++) {
			buttonList[i] = new GameButton(choice[i], 100, 430 - i*25, choice[i].length() * 18, 18, "Message" + i);
		}
		InstantInfo info = new InstantInfo(message, buttonList);
		Game.gameComponentList.add(info);
		choices = actions;
		currentMessage = info;
	}
	
	public static void enterChoice(int index) {
		choices[index].run();
		currentMessage.closeMessage();
	}
	
}
