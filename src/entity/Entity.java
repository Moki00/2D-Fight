package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Util;

/**
 * 
 * parent class for player, NPCs, and monsters
 * 
 * @author Moki_21_10
 *
 */
public class Entity {

	GamePanel gp;
	public int worldX, worldY;
	public int speed;

	// store image files of entity
	public BufferedImage right1, left1, up1, down1, right2, left2, up2, down2;
	public String direction;

	public int spriteNum = 1;
	public int spriteCounter = 0;

	// for collision
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default for all entities
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionCounter = 0;
	String dialogues[] = new String[20];
	int dialogueIndex = 0;

	// Character Status
	public int maxLife;
	public int life;

	/**
	 * Constructor
	 * 
	 * @param gp
	 */
	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}

	/**
	 * update each entity
	 */
	public void update() {
		setAction();

		// Check Tile Collision
		collisionOn = false;
		gp.collisionChecker.checkTile(this);
		gp.collisionChecker.checkObject(this, false);
		gp.collisionChecker.checkPlayer(this);

		// if collision is false, entity can move
		if (collisionOn == false) {

			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			default:
				System.out.println("never reach this: Entity.update");
				break;
			}
		}

		// entity has 2 movements per direction
		spriteCounter++;
		if (spriteCounter > 13) {
			if (spriteNum == 1) {
				spriteNum++;
			} else if (spriteNum == 2) {
				spriteNum--;
			}
			spriteCounter = 0;
		}

	}

	/**
	 * draw entity on screen
	 * 
	 * @param g2
	 */
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

	/**
	 * Setup image
	 * 
	 * @param imagePath
	 * @return
	 */
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

	/**
	 * talk to NPC
	 */
	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;

		// direct Old Man to player when talking
		switch (gp.player.direction) {

		case "down":
			direction = "up";
			break;
		case "up":
			direction = "down";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;

		}
	}

}
