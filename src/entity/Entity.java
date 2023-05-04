package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

/**
 * 
 * parent class for players and monsters
 * 
 * @author Moki_21_10
 *
 */
public class Entity {

	GamePanel gp;
	public int worldX, worldY;
	public int speed;

	// store our image files
	public BufferedImage right1, left1, up1, down1, right2, left2, up2, down2;
	public String direction;

	public int spriteNum = 1;
	public int spriteCounter = 0;

	// for collision
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

}
