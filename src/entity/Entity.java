package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Util;

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
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default for all entities
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public String packageClass = "/player/";

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void draw(Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		BufferedImage image = null;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				} else {
					image = up1;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				} else {
					image = down1;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				} else {
					image = right1;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				} else {
					image = left1;
				}
				break;
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

		}
	}

	public BufferedImage setup(String imagePath) {
		Util util = new Util();
		BufferedImage image = null;

		try {

			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = util.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

}
