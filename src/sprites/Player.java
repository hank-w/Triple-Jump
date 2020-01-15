package sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.Canvas;
import main.Game;
import main.GameComponent;
import main.GameOver;
import main.GameTag;
import main.LevelManager;
import main.ScaleManager;
import main.SpriteSheetController;
import main.resources.images.ResourcePath;
import sprites.gamesprites.Fireball;
import sprites.gamesprites.Shell;
import sprites.gamesprites.SpikedBall;
import util.DelayTrigger;

public class Player extends GameComponent {

	private static int animationDelay = 20;
	public static final int defaultLength = 30;
	public static final int defaultWidth = 30;
	private static final float gravity = -0.095f;
	
	public static double velY = 0;
	public static double velX = 0;
	public static boolean onGround = false;
	
	
	public static int keyUp = 0;
	public static int keyDown = 0;
	public static int keyLeft = 0;
	public static int keyRight = 0;
	
	public double x;
	public double y;
	
	public double rawx;
	public double rawy;
	
	public static double worldX;
	public static double worldY;
	
	public static double resizedworldX;
	public static double resizedworldY;
	
	public double direction;
	
	public int movementX;
	public int movementY;
	
	public int length;
	public int width;
	
	public int hlength;
	public int hwidth;
	
	public int heightOffset;
	
	public static boolean worldLockX = false;
	public static boolean worldLockY = false;
	public static boolean jumpDown = false;
	
	private int count = 0;
	private int phase = 0;
	
	public int rawLength;
	private int rawWidth;
	
	private double scale;
	public boolean deathAnimationPlaying;
	@SuppressWarnings("unused")
	private BufferedImage deathAnimation;
	
	public int mode;
	
	public static int buffType = 0;
	public int invulnerableFrame = 0;
	public boolean sprinting;
	public double maxAccel;
	public boolean attack;
	
	public boolean isAttacking;
	private int count2;
	
	public static GameTag coordPointer;
	public Player(int px, int py, int l, int w, int d,int wX, int wY) {
		scale = ScaleManager.getMin();
		//Unaffected by scale
		rawx = px;
		rawy = py;
		direction = d;
		
		rawLength = l;
		rawWidth = w;
		
		
		length = (int) (rawLength * ScaleManager.getMin());
		width = (int) (rawWidth * ScaleManager.getMin());
		
		worldX = wX;
		worldY = wY;
		
		resizedworldX = (worldX * ScaleManager.getMin());
		resizedworldY = (worldY * ScaleManager.getMin());
		
		if (worldX < 0) {
			rawx = px + worldX;
		}
		
		if (worldY < 0) {
			rawy = py + worldY;
		}
		
		
		if (LevelManager.mapWidth != 0) {
			if (resizedworldX > LevelManager.mapWidth * Tile.size - ScaleManager.getWidth()) {
				rawx = (px + worldX - LevelManager.mapWidth * Tile.rawsize + ScaleManager.getxConstant());
			}
			
			if (resizedworldY > LevelManager.mapHeight * Tile.size - ScaleManager.getHeight()) {
				rawy = (py + worldY - LevelManager.mapWidth * Tile.rawsize + ScaleManager.getyConstant());
			}
		}
		x = (int)(rawx * ScaleManager.getMin());
		y = (int)(rawy * ScaleManager.getMin());
		
		deathAnimationPlaying = false;
		Game.currentPlayer = this;
		mode = 0;
		//
//		Game.gameComponentList.remove(coordPointer);
//		coordPointer = new GameTag(200, 30, worldX + " " + worldY, Color.MAGENTA, 130, 20);
//		
//		Game.gameComponentList.add(coordPointer);
		//
		buffType = 0;
		sprinting = false;
		attack = false;
		isAttacking = false;
		Canvas.background = Color.CYAN;
	}
	
	
	public static int[] calculateCorner(int deltaX, int deltaY, double direction2) {
		int[] vector = new int[2];
		
		int r = (int)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		double a = Math.toDegrees(Math.atan2(deltaY,deltaX));
		double a_ = Math.toRadians(a + direction2);
		vector[0] = (int) (Math.sin(a_) * r);
		vector[1] = (int) (Math.cos(a_) * r);
		return vector;
	}
	
	
	
	@Override
	public String getObjectType() {
		
		return "Player";
	}

	@Override
	public void updateView(Graphics g) {
		//int[] spriteSheetPosition = SpriteSheetController.spriteSheetCoord(SpriteSheetController.playerSpriteSheet, phase, 4, 4);
		int[] spriteSheetPosition = SpriteSheetController.spriteSheetCoord(SpriteSheetController.playerSpriteSheet, phase + buffType * 30, 15, 14);
		double canvasX = x + ScaleManager.screenOffsetX;
		double canvasY = y + ScaleManager.screenOffsetY;
		
		int gLength = length + 15;
		int gWidth = width + 15;
		if (buffType != 0) {
			gLength = (int)(length/1.5) + 15;
			gWidth = width + 15;
		}
		
		//hitbox
		//g.drawRect((int)x, (int)y, width, length);
		
		int dX = 0;
		int dY = 0;
		
		if (buffType != 0 && mode == 10) {
			dY = 10;
			
		}
		double tdirection = direction;
		if (buffType == 6) {
			dY -= 10;
			if (mode == 10 && velX != 0) {
				if (count2 >= 50) {
					count2 = 0;
				} else {
					count2++;
				}
				if (count2 > 25) {
					if (direction == -1) {
						dX = -10;
					} else {
						dX = 10;
					}
					direction = -direction;
					
				}
			}
		}
		if (deathAnimationPlaying) {
			if (y < ScaleManager.getHeight() + 20) {
				int[] spriteSheetPositionD = SpriteSheetController.spriteSheetCoord(SpriteSheetController.playerSpriteSheet, 29, 15, 14);
				g.drawImage(SpriteSheetController.playerSpriteSheet, (int)canvasX-25 + dX, (int)canvasY-10 + dY, (int)canvasX+gLength -5 + dX, (int)canvasY+gWidth + 10 + dY, spriteSheetPositionD[0], spriteSheetPositionD[1], spriteSheetPositionD[2], spriteSheetPositionD[3], null);
				//g.drawImage(deathAnimation, (int)canvasX-10, (int)canvasY-10, (int)length + 5, (int)width + 5, null);
				y += velY;
				velY += 0.1;
			}
		} else {
			if (direction == -1) {
				g.drawImage(SpriteSheetController.playerSpriteSheet, (int)canvasX+gWidth - 5 + dX, (int)canvasY-10 + dY, (int)canvasX-25 + dX, (int)canvasY+gLength + 10 + dY, spriteSheetPosition[0], spriteSheetPosition[1], spriteSheetPosition[2], spriteSheetPosition[3], null);
			} else {
				g.drawImage(SpriteSheetController.playerSpriteSheet, (int)canvasX-10 + dX, (int)canvasY-10 + dY, (int)canvasX+gWidth + 10 + dX, (int)canvasY+gLength + 10 + dY, spriteSheetPosition[0], spriteSheetPosition[1], spriteSheetPosition[2], spriteSheetPosition[3], null);				
			}
			
			
		}
		direction = tdirection;
	}

	@Override
	public void updateTick() {
		if (jumpDown) {
			if (Player.onGround) {
				Player.onGround = false;
				Player.velY = -4.9f;
			}
		}
//		coordPointer.setText("Position: (" + y + "," + worldY + ")");
		length = (int) (rawLength * ScaleManager.getMin());
		width = (int) (rawWidth * ScaleManager.getMin());
		
		if (scale != ScaleManager.getMin()) {
			x = rawx * ScaleManager.getMin();
			y = rawy * ScaleManager.getMin();
			length = (int) (rawLength * ScaleManager.getMin());
			width = (int) (rawWidth * ScaleManager.getMin());
			scale = ScaleManager.getMin();
		} else {
			rawx = x/scale;
			rawy = y/scale;
		}
		
		resizedworldX = (int)(worldX * scale);
		resizedworldY = (int)(worldY * scale);
		
		
		if (count < animationDelay) {
			count += 1;
		}
		if (!Game.inGame) {
			x += keyRight-keyLeft;
			y += keyDown-keyUp;
			if (x > ScaleManager.getWidth()) {
				x = -40;
			} else if (x < -width) {
				x = ScaleManager.getWidth();
			}
			
			if (y > ScaleManager.getHeight()) {
				y = -40;
			} else if (y < -length) {
				y = ScaleManager.getHeight();
			}
		} else {
			boolean canMoveRight = true;
			boolean canMoveLeft = true;
			boolean canMoveY = true;
			if (deathAnimationPlaying) {
				canMoveRight = false;
				canMoveLeft = false;
				canMoveY = false;
			}
			boolean interacting = false;
			int collisionGapX = (int) (20 * scale);
			int collisionGapY = (int) (3 * scale);
			hlength = length;
			hwidth = width;
			heightOffset = 0;
			if (mode == 10) {
				heightOffset = hlength / 2;
			}
			for (int i = 0; i < LevelManager.map.size(); i++) {
				for (int j = 0; j < LevelManager.map.get(i).size(); j++) {
					int testX = LevelManager.map.get(i).get(j).getX();
					int testY = LevelManager.map.get(i).get(j).getY();
					if (LevelManager.map.get(i).get(j).canCollide) { //Collision Detection
						//colliding right
						if (x + hwidth + 5 > testX && x + hwidth < testX + collisionGapX && y + hlength > testY && y + heightOffset < testY + Tile.size) {
							if (Player.buffType == 6 && Game.currentPlayer.mode == 10 && Game.currentPlayer.velX != 0 && LevelManager.map.get(i).get(j).bounce(0)) {
								
							} else {
								canMoveRight = false;
								reversePositionX();
							}
						}
						//colliding left
						if (x - 5 < testX + Tile.size && x > testX + Tile.size - collisionGapX && y + hlength > testY && y + heightOffset < testY + Tile.size) {
							if (Player.buffType == 6 && Game.currentPlayer.mode == 10 && Game.currentPlayer.velX != 0 && LevelManager.map.get(i).get(j).bounce(0)) {
								
							} else {
							canMoveLeft = false;
							reversePositionX();
							}
						}
						
						if (velY >= 0) {//Moving Down
							if (y > testY - hlength - collisionGapY && y < testY && x + hwidth - 2 > testX && x < testX + Tile.size - 2) {
								canMoveY = false;
								if (!onGround) {
									
									onGround = true;
									velY = 0f;
									if (y + hlength > testY + 1) {
										reversePositionY();
									}
									
									if (mode == 7) {
										mode = 0;
										phase = 0;
									}
									if (mode == 9) {
										if (!LevelManager.map.get(i).get(j).bounce(1)) {
											mode = 0;
											phase = 0;
											//if (y > testY - length - collisionGapY + 1)
											//reversePositionY();
										}
									}
								}
							}
						} else if (velY < 0) {//Moving Up
							if (y + heightOffset > testY + 20 && y + heightOffset < testY + Tile.size + collisionGapY && x + hwidth > testX && x < testX + Tile.size) {
								if (!LevelManager.map.get(i).get(j).interact()) {
									canMoveY = false;
									velY = 0;
								}
								
								LevelManager.map.get(i).get(j).bounce(0);
								
							}
						}
						
						if (canMoveY) {
							onGround = false;
						}
						if ((!canMoveLeft || !canMoveRight || !canMoveY) && interacting == false) {
							interacting = true;
							if (LevelManager.map.get(i).get(j).interact()) {
								canMoveLeft = true;
								canMoveRight = true;
								canMoveY = true;
							}
						}
						
					}
				}
			}
			
			
			if ((canMoveRight && keyRight-keyLeft > 0) || (canMoveLeft && keyRight-keyLeft < 0)) {
				accelerateX(canMoveRight, canMoveLeft);
			} else {
				
				if (!canMoveRight || !canMoveLeft) {
					velX = 0;
				}
				if (keyRight-keyLeft > 0) {
					mode = 3;
				} else if (keyRight-keyLeft < 0) {
					mode = 4;
				} else {
					if (mode == 1) {
						mode = 5;
					} else if (mode == 2) {
						mode = 6;
					}
				}
			}
			moveX();
			moveY();
			//Ground Pound
			if (keyDown == 1 && mode != 9 && mode != 8 && !onGround && mode != 10) {
				mode = 8;
			} else if (keyDown == 1 && (onGround) && mode != 10) {
				mode = 10;
				if (buffType == 6) {
					velX *= 1.5;
				}
			} else if (keyDown != 1 && mode == 10) {
				mode = 0;
				phase = 0;
			}
			if (mode == 8) {
				velX = 0;
				velY = -1;
				Game.gameComponentList.add(new DelayTrigger(() -> mode = 9, 50));
			}
			if (mode == 9) {
				velY = 6;
				
			}
			
			//Attack Key Control
			if (attack) {
				if (!isAttacking) {
					isAttacking = true;
					attack();
				}
			} else {
				isAttacking = false;
			}
			
			//Costume Change
			if (mode != 1 || mode != 2) {
				switch (mode) {
					case 3: {
						phase = 0;
						break;
					}
					
					case 4: {
						phase = 0;
						break;
					}
					
					case 5: {
						phase = 0;
						break;
					}
					
					case 6: {
						phase = 0;
						break;
					}
					
					case 7: {
						phase = 3;
						break;
					}
					
					case 8: {
						phase = 6;
						break;
					}
					
					case 9: {
						phase = 7;
						break;
					}
					
					case 10: {
						if (buffType == 0) {
							phase = 0;
						} else {
							phase = 4;
						}
					}
				}
			}
			
			if (invulnerableFrame > 0) {
				invulnerableFrame--;
			}
			
			//Exiting Level
			if (x > ScaleManager.getWidth()) {
				LevelManager.exitLevel(LevelManager.mapInfo, "levelR", "eR");
			} else if (x < -width) {
				LevelManager.exitLevel(LevelManager.mapInfo, "levelL", "eL");
			}
			
			if (y > ScaleManager.getHeight()) {
				//LevelManager.exitLevel(LevelManager.mapInfo, "death", "death");
				die();
			} else if (y < -length) {
				LevelManager.exitLevel(LevelManager.mapInfo, "levelU", "eU");
			}
			
		}
		
		
	}

	private void moveX() {
		worldX += velX;
		if (-Player.resizedworldX <= 0 && -Player.resizedworldX > -LevelManager.mapWidth * Tile.size + ScaleManager.getWidth()) {
		} else {
			worldX+=velX;
			Player.worldLockX = true;
		}
		if (-Player.resizedworldY <= 0 && -Player.resizedworldY > -LevelManager.mapHeight * Tile.size + ScaleManager.getHeight()) {
		} else {
			Player.worldLockY = true;
		}
		
		if (scale != ScaleManager.getMin()) {
			Player.worldLockX = true;
			Player.worldLockY = true;
		}
		
		if (worldLockX) {
			x += velX * scale;
			
			if ((x > ScaleManager.getWidth()/2 - length/2 && keyRight-keyLeft > 0)) {
				worldLockX = false;
			}
			
			if ((x < ScaleManager.getWidth()/2 - length/2 && keyRight-keyLeft < 0)) {
				worldLockX = false;
			}
		}
		
		if (keyRight-keyLeft == 0) {
			if (onGround) {
				if (mode != 10) {
					velX *= 0.9;
				} else {
					if (buffType != 6) {
						velX *= 0.97;
					}
				}
			} else if(mode != 10 || buffType != 6) {
				velX = velX * 0.95;
			}
			if (Math.abs(velX) < 0.1) {
				velX = 0;
			}
			//velX += -(Math.abs(velX)/velX);
		} else {
			if (mode == 10 && buffType != 6) {
				velX *= 0.97;
			}
		}
	}
	
	private void accelerateX(boolean canMoveRight, boolean canMoveLeft) {
		if (mode != 10 && mode != 9 && mode != 8) {
			if(sprinting) {
				maxAccel = 1.5f;
				animationDelay = 12;
			} else {
				maxAccel = 1;
				animationDelay = 20;
			}
			
			if (count >= animationDelay) {
				
				if (keyRight-keyLeft > 0) {
					if (!canMoveRight) {
						return;
					}
					direction = -1;
					mode = 1;
				} else if (keyRight-keyLeft < 0) {
					if (!canMoveLeft) {
						return;
					}
					direction = 1;
					mode = 2;
				}
				count = 0;
				if (phase == 1) {
					phase = 0;
				} else {
					phase = 1;
				}
			}
			
			if ((keyRight-keyLeft)/velX < 0 && velX != 0) {
				phase = 5;
				velX += (keyRight-keyLeft)/20d;
			} else {
				velX += (keyRight-keyLeft)/2d;
			}
			if (velX > maxAccel) {
				velX = maxAccel;
			}
			if (velX < -maxAccel) {
				velX = -maxAccel;
			}
		} else {
			if (count >= animationDelay) {
				if (keyRight-keyLeft > 0) {
					direction = -1;
				} else if (keyRight-keyLeft < 0) {
					direction = 1;
				}
			}
		}
		
	}
	
	private void reversePositionX() {
		worldX -= (keyRight-keyLeft)/2;
		if (worldLockX) {
			x -= (double)(keyRight-keyLeft) * scale/2d;
			
			if ((x > ScaleManager.getWidth()/2 - length/2 && keyRight-keyLeft > 0)) {
				worldLockX = false;
			}
			
			if ((x < ScaleManager.getWidth()/2 - length/2 && keyRight-keyLeft < 0)) {
				worldLockX = false;
			}
		}
	}
	
	private void reversePositionY() {
		worldY -= 1;
		if (-Player.resizedworldX <= 0 && -Player.resizedworldX > -LevelManager.mapWidth * Tile.size + ScaleManager.getWidth()) {
		} else {
			Player.worldLockX = true;
		}
		if (-Player.resizedworldY <= 0 && -Player.resizedworldY > -LevelManager.mapHeight * Tile.size + ScaleManager.getHeight()) {
		} else {
			if (!worldLockY) {
				worldY -= 1;
			}
			Player.worldLockY = true;
		}
		
		if (scale != ScaleManager.getMin()) {
			scale = ScaleManager.getMin();
			Player.worldLockX = true;
			Player.worldLockY = true;
		}
		if (worldLockY) {
			y -= (double)1 * scale;
			
			if ((y > ScaleManager.getHeight()/2 - length/2)) {
				worldLockY = false;
			}
			
			if ((y < ScaleManager.getHeight()/2 - length/2)) {
				worldLockY = false;
			}
		}
	}
	
	public static void die() {
		if (!Game.currentPlayer.deathAnimationPlaying) {
			Game.paused = true;
			velY = -4f;
			DelayTrigger.runDelayedAction(() -> Game.currentPlayer.initDeath(ResourcePath.getImage("death")), 1);
			Game.currentPlayer.move(Game.currentPlayer.getX(), Game.currentPlayer.getY() -10);
			//DelayTrigger.runDelayedAction(() -> VisualEffects.playFlash(50, new Color(255,0,0)),1);
			DelayTrigger.runDelayedAction(() -> GameOver.enterTransition(), 100);
		}
	}
	
	public static void die2() {
		if (Game.currentPlayer.invulnerableFrame > 0) {
			return;
		}
		if (buffType > 0) {
			if (buffType > 1) {
				buffType = 1;
			} else {
				buffType = 0;
				Game.currentPlayer.rawLength = defaultLength;
				Game.currentPlayer.y += 1;
			}
			Game.currentPlayer.invulnerableFrame = 500;
			return;
		}
		if (!Game.currentPlayer.deathAnimationPlaying) {
			Game.paused = true;
			velY = -4f;
			
			
			DelayTrigger.runDelayedAction(() -> Game.currentPlayer.initDeath(ResourcePath.getImage("death")), 1);
			Game.currentPlayer.move(Game.currentPlayer.getX(), Game.currentPlayer.getY());
			//DelayTrigger.runDelayedAction(() -> VisualEffects.playFlash(50, new Color(255,0,0)),1);
			DelayTrigger.runDelayedAction(() -> GameOver.enterTransition(), 100);
		}
	}
	
	private void moveY() {
		double moveAmount = 0;
		if (!onGround) {
			velY -= gravity;
			if (velY > 3 && mode != 9) {
				velY = 3;
			}
			
			if (mode != 9 && mode != 8 && mode != 10) {
				mode = 7;
			}
		}
		
		worldY += velY;
		moveAmount = velY;
		if (-Player.resizedworldX <= 0 && -Player.resizedworldX > -LevelManager.mapWidth * Tile.size + ScaleManager.getWidth()) {
		} else {
			Player.worldLockX = true;
		}
		if (-Player.resizedworldY <= 0 && -Player.resizedworldY > -LevelManager.mapHeight * Tile.size + ScaleManager.getHeight()) {
		} else {
			if (!worldLockY) {
				worldY += moveAmount;
			}
			Player.worldLockY = true;
		}
		
		if (scale != ScaleManager.getMin()) {
			scale = ScaleManager.getMin();
			Player.worldLockX = true;
			Player.worldLockY = true;
		}
		
		if (worldLockY) {
			y += moveAmount * scale;
//			if ((y > ScaleManager.getHeight()/2d - length/2d && velY < 0)) {
//				worldLockY = false;
//			}
			
			if ((y < ScaleManager.getHeight()/2d - length/2d && velY < 0)) {
				worldLockY = false;
			}
		}
		
//		if (velY < 0) {
//			worldLockY = false;
//		}
	}
	
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.BLACK;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return (int) x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return (int) y;
	}

	@Override
	public void move(int posX, int posY) {
		// TODO Auto-generated method stub
		x = posX;
		y = posY;
	}
	
	public void initDeath(BufferedImage playerImg) {
		deathAnimationPlaying = true;
		deathAnimation = playerImg;
	}
	
	public void updateDeathAnimation(BufferedImage playerImg) {
		deathAnimation = playerImg;
	}

	public void attack() {
		if (buffType == 2) {
			fireball();
		} else if (buffType == 6) {
			spikeball();
		}
		//LevelManager.spriteList.add(new Shell("s",(int)(canvasX + 10 * direction),(int)(canvasY + rawy * scale - 10)));
	}
	public void fireball() {
		int dispY = (int) (y - ScaleManager.getHeight()/2 + length/2);
		int dispX = (int) (x - ScaleManager.getWidth()/2 + width/2);
		LevelManager.spriteList.add(new Fireball("f",(int)(worldX + x * scale + 10 * direction - dispX),(int)(worldY + y * scale - 10 - dispY),(int)direction));
	}
	
	public void spikeball() {
		int dispY = (int) (y - ScaleManager.getHeight()/2 + length/2);
		int dispX = (int) (x - ScaleManager.getWidth()/2 + width/2);
		LevelManager.spriteList.add(new SpikedBall("b",(int)(worldX + x * scale + 10 * direction - dispX),(int)(worldY + y * scale - 20 - dispY),(int)direction));
	}
	
}
