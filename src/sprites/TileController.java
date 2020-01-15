package sprites;

import sprites.blocks.*;

public class TileController {
	public static Tile createTile(String type, int x, int y) {
		switch (type) {
		
		case "W": {
			return new EmptyBrick(type,x,y);
		}
		
		case "T": {
			return new QuestionBlock2(type,x,y);
		}
		
		case "Q": {
			return new QuestionBlock(type,x,y);
		}
		
		case "E": {
			return new Empty(type,x,y);
		}
		
		case "B": {
			return new Brick(type,x,y);
		}
			
		case "F": {
			return new Barrier(type,x,y);
		}
		
		case "M": {
			return new BrickInvisible(type,x,y);
		}
		
		case "N": {
			return new NormalBrick(type,x,y);
		}
		
		case "G": {
			return new GroundBlock(type,x,y);
		}
		
		}
		return new Tile(type,x,y);
	}
}
