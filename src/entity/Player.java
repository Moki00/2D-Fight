package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;

	/**
	 * Control the player with this constructor
	 * 
	 * @param gamePanel
	 * @param keyHandler
	 */
	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp); // calling superClass

		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// solid collision area
		solidArea = new Rectangle();

		// upper left corner of collision
		solidArea.x = gp.tileSize / 3; // 48/3=16 from the left (mid 1/3rd to collide, 1/3 free on both sides)
		solidArea.y = gp.tileSize / 2; // 48/2=24 from the top (bottom half will collide)

		// objects
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// size of collision
		solidArea.width = gp.tileSize - solidArea.x * 2; // 48-32=16 wide
		solidArea.height = gp.tileSize - solidArea.y; // 48-24=24 high

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 22;
		speed = 4;
		direction = "down";
		spriteNum = 1;
	}

	public void getPlayerImage() {

		right1 = setup("/player/heroR1");
		right2 = setup("/player/heroR2");
		left1 = setup("/player/heroLeft1");
		left2 = setup("/player/heroLeft2");
		up1 = setup("/player/heroUp1");
		up2 = setup("/player/heroUp2");
		down1 = setup("/player/heroDown1");
		down2 = setup("/player/heroDown2");

	}

	/**
	 * update player location
	 */
	public void update() {

		if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// Check Tile Collision
			collisionOn = false;
			gp.collisionChecker.checkTile(this);

			// Check Object Collision
			int objIndex = gp.collisionChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// Check NPC Collision
			int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
			interactNpc(npcIndex);

			// if collision is false, player can move
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
					System.out.println("never reach this: Player.update");
					break;
				}

			}

			// player has 2 movements only
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
	}

	/**
	 * player picks up an object
	 * 
	 * @param i
	 */
	public void pickUpObject(int i) {
		if (i != 999) {

		}
	}

	/**
	 * @param i
	 */
	private void interactNpc(int i) {
		if (i != 999) {
			if (gp.keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		gp.keyH.spacePressed = false;

	}

	/**
	 * drawing the player
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize); // location, then size

		BufferedImage image = null;

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

		// only drawing at location, no need for scaling here now
		g2.drawImage(image, screenX, screenY, null);

		// troubleshoot collision rectangles
//		int x, int y, int width, int height
//		g2.setColor(Color.RED);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

	}

}
