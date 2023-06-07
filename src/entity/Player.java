package entity;

import java.awt.AlphaComposite;
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

		// for resets
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// size of collision
		solidArea.width = gp.tileSize - solidArea.x * 2; // 48-32=16 wide
		solidArea.height = gp.tileSize - solidArea.y; // 48-24=24 high

		// Attack
		attackArea.width = 38;
		attackArea.height = 38;

		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 22;
		speed = 4;
		direction = "down";
		spriteNum = 1;

		// Player Status
		maxLife = 6;
		life = maxLife;

	}

	/**
	 * 16x16 player Images
	 */
	public void getPlayerImage() {

		right1 = setup("/player/heroR1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/heroR2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/heroLeft1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/heroLeft2", gp.tileSize, gp.tileSize);
		up1 = setup("/player/heroUp1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/heroUp2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/heroDown1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/heroDown2", gp.tileSize, gp.tileSize);

	}

	/**
	 * 32*16 for left and right fighting images; 16x32 for up and down fighting
	 * images
	 */
	public void getPlayerAttackImage() {

		attackRight1 = setup("/p_attack/attack_right_1", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setup("/p_attack/attack_right_2", gp.tileSize * 2, gp.tileSize);
		attackLeft1 = setup("/p_attack/attack_left_1", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setup("/p_attack/attack_left_2", gp.tileSize * 2, gp.tileSize);
		attackUp1 = setup("/p_attack/attack_up_1", gp.tileSize, gp.tileSize * 2);
		attackUp2 = setup("/p_attack/attack_up_2", gp.tileSize, gp.tileSize * 2);
		attackDown1 = setup("/p_attack/attack_down_1", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setup("/p_attack/attack_down_2", gp.tileSize, gp.tileSize * 2);

	}

	/**
	 * update player location
	 */
	public void update() {

		// invincible: code above pressing keys
		if (invincible) {
			invincibleCounter++;
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}

		if (attacking) {
			attacking();
		} else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true || keyH.spacePressed) {

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

			// Check Monster Collision
			int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
			touchMonster(monsterIndex);
//			interactMonster(monsterIndex);

			// Check Event
			gp.eventHandler.checkEvent();

			// if collision is false, player can move
			if (collisionOn == false && keyH.spacePressed == false) {
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
				}
			}

			gp.keyH.spacePressed = false; // moved

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
	 * hit space or enter button to talk to NPC, if far from NPC, it becomes an
	 * attack
	 * 
	 * @param i
	 */
	public void interactNpc(int i) {

		if (gp.keyH.spacePressed) {
			if (i != 999) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			} else {
				attacking = true;
			}
		}
	}

	/**
	 * attacking with 2 images in each direction
	 */
	public void attacking() {
		spriteCounter++;

		if (spriteCounter < 6) {
			spriteNum = 1;
		} else if (spriteCounter > 5 && spriteCounter < 26) {
			spriteNum = 2;

			// Save these areas
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// Adjust player's location for attackArea
			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}

			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			// Check monster collision with the updated locations
			int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);

			// restore position and solid area after collision
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;

		} else if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void damageMonster(int i) {
		if (i != 999) {
			if (gp.monster[i].invincible == false) {
				gp.monster[i].life--;
				gp.monster[i].invincible = true;

				if (gp.monster[i].life <= 0) {
					gp.monster[i] = null; // death by null
				}
			}
		} else {
			System.out.println("miss");
		}
	}

	/**
	 * touch a monster = ouch
	 * 
	 * @param i
	 */
	public void touchMonster(int i) {
		if (i != 999) {
			if (invincible == false) {
				life--;
				invincible = true;
			}
		}
	}

	/**
	 * drawing the player
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if (!attacking) {
				if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				} else {
					image = up1;
				}
			} else if (attacking) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {
					image = attackUp1;
				} else if (spriteNum == 2) {
					image = attackUp2;
				} else {
					image = up1;
				}
			}
			break;
		case "down":
			if (!attacking) {
				if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				} else {
					image = down1;
				}
			} else if (attacking) {
				if (spriteNum == 1) {
					image = attackDown1;
				} else if (spriteNum == 2) {
					image = attackDown2;
				} else {
					image = up1;
				}
			}
			break;
		case "right":
			if (!attacking) {
				if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				} else {
					image = right1;
				}
			} else if (attacking) {
				if (spriteNum == 1) {
					image = attackRight1;
				} else if (spriteNum == 2) {
					image = attackRight2;
				} else {
					image = up1;
				}
			}
			break;
		case "left":
			if (!attacking) {
				if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				} else {
					image = left1;
				}
			} else if (attacking) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {
					image = attackLeft1;
				} else if (spriteNum == 2) {
					image = attackLeft2;
				} else {
					image = up1;
				}
			}
			break;
		}

		// visual invincible with player at 30% opacity
		if (invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		// only drawing at location, no need for scaling here now
		g2.drawImage(image, tempScreenX, tempScreenY, null);

		// puts everything else back to normal to only show the player opacity change
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// troubleshoot collision rectangles
//		int x, int y, int width, int height
//		g2.setColor(Color.RED);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

		// troubleshoot invincible
//		g2.setFont(new Font("Ariel", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString(invincible + ": Invincible: " + invincibleCounter, 10, 400);

	}

}
