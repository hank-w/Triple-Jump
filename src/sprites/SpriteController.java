package sprites;

import sprites.gamesprites.Fireball;
import sprites.gamesprites.Goomba;
import sprites.gamesprites.GrayFlower;
import sprites.gamesprites.Koopa;
import sprites.gamesprites.RedFlower;
import sprites.gamesprites.RedMushroom;
import sprites.gamesprites.Shell;
import sprites.gamesprites.SpikedBall;

public class SpriteController {
	public static GameSprite createSprite(String type, int x, int y) {
		switch (type) {
		case "b": {
			return new SpikedBall(type,x,y,1);
		}
		
		case "t": {
			return new GrayFlower(type,x,y);
		}
		
		case "f": {
			return new Fireball(type,x,y,1);
		}
		
		case "r": {
			return new RedFlower(type,x,y);
		}
		
		case "s": {
			return new Shell(type, x, y);
		}
		
		case "m": {
			return new RedMushroom(type, x, y);
		}
		
		case "g": {
			return new Goomba(type, x, y);
		}
		
		case "k": {
			return new Koopa(type, x, y);
		}
		
		}
		return new GameSprite(type,x,y);
	}
}
