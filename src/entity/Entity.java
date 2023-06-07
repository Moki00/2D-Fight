package entity;

import java.awt.AlphaComposite;
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

	// State
	public int worldX, worldY;
	public int spriteNum = 1;

	// Image of entity
	public BufferedImage image, image2, image3;
	public BufferedImage right1, left1, up1, down1, right2, left2, up2, down2;
	public BufferedImage attackRight1, attackLeft1, attackUp1, attackDown1, attackRight2, attackLeft2, attackUp2,
			attackDown2;
	public String direction = "down";

	// Collision
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default for all entities
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public boolean collision = false;

	// NPC Conversation
	String dialogues[] = new String[20];
	int dialogueIndex = 0;

	// Fighting
	boolean attacking = false;
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public boolean alive = true;
	public boolean dying = false;

	// Counters
	public int spriteCounter = 0;
	public int actionCounter = 0;
	public int invincibleCounter = 0;

	// Character Status
	public String name;
	public int maxLife;
	public int life;
	public boolean invincible = false;
	public int speed;
	public String type = "";

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
		gp.collisionChecker.checkEntity(this, gp.monster);
		gp.collisionChecker.checkEntity(this, gp.npc);

		boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

		if (this.type.equals("monster") && contactPlayer) {
			if (gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}

		// invincible: code outside movement
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 30) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

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

			// visual invincible with player at 30% opacity
			if (invincible) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			}

			// only drawing at location, no need for scaling here now
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			// puts everything else back to normal to only show the player opacity change
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		}
	}

	/**
	 * Setup image
	 * 
	 * @param imagePath
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage setup(String imagePath, int width, int height) {
		Util util = new Util();
		BufferedImage image = null;

		try {

			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png")); // must use png file with this
			image = util.scaleImage(image, width, height);
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
