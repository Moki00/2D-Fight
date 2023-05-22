package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Util;

/**
 * 
 * Parent class for all objects
 * 
 * @author Moki_21_10
 *
 */
public class SuperObject {

	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // whole item square
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	Util util = new Util();

	public void draw(Graphics2D g2, GamePanel gp) {

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

		}
	}

}
