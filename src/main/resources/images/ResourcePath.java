package main.resources.images;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.SpriteSheetController;

public class ResourcePath {
	public static final String character = "Character";
	public static ArrayList<String> idList = new ArrayList<String>();
	public static ArrayList<BufferedImage> imgList = new ArrayList<BufferedImage>();
	public static BufferedImage getImage(String id) {
		for (int i = 0; i < idList.size(); i++) {
			if (idList.get(i).equals(id)) {
				return imgList.get(i);
			}
		}
		
		idList.add(id);
		BufferedImage newImg = inputImage(id);
		imgList.add(newImg);
		return newImg;
	}
	
	public static BufferedImage inputImage(String id) {
		switch (id) {
		case "b" : {
			return SpriteSheetController.loadImage("Spiked_Ball");
		}
		
		case "t" : {
			return SpriteSheetController.loadImage("flower_gray");
		}
		
		case "f" : {
			return SpriteSheetController.loadImage("fireball");
		}
		
		case "r" : {
			return SpriteSheetController.loadImage("flower_red");
		}
		
		case "s" : {
			return SpriteSheetController.loadImage("koopa");
		}
		
		case "k" : {
			return SpriteSheetController.loadImage("koopa");
		}
		
		case "g" : {
			return SpriteSheetController.loadImage("Goomba");
		}
		case "m" : {
			return SpriteSheetController.loadImage("mushroom");
		}
		
		case "W": {
			return SpriteSheetController.loadImage("normalbrick");
		}
		
		case "E": {
			return SpriteSheetController.loadImage("empty");
		}
		
		case "Q": {
			return SpriteSheetController.loadImage("questionblock");
		}
		
		case "N": {
			return SpriteSheetController.loadImage("normalbrick");
		}
		
		case "G": {
			return SpriteSheetController.loadImage("ground");
		}
		
//		case "S": {
//			return SpriteSheetController.loadImage("slime");
//		}
		
		case "B": {
			return SpriteSheetController.loadImage("brick");
		}
		
//		case "H": {
//			return SpriteSheetController.loadImage("corridorhor");
//		}
//		
//		case "L": {
//			return SpriteSheetController.loadImage("corridorendL");
//		}
//		
//		case "D": {
//			return SpriteSheetController.loadImage("door");
//		}
//		
//		case "O": {
//			return SpriteSheetController.loadImage("door_open");
//		}
		
		case "T": {
			return SpriteSheetController.loadImage("questionblock");
		}
		
//		case "apple": {
//			return SpriteSheetController.loadImage("apple");
//		}
//		
//		case "Y": {
//			return SpriteSheetController.loadImage("tree");
//		}
//		
//		case "V": {
//			return SpriteSheetController.loadImage("dead_tree");
//		}
		
		}
		return null;
	}
}
