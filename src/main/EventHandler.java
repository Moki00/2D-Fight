package main;

import java.awt.Rectangle;

/**
 * Handle events around the player like hitting
 * 
 * @author Moki_21_10
 *
 */
public class EventHandler {

	GamePanel gp;
	Rectangle eventRect;
	int eventRectDefaultX, eventRectDefaultY;

	/**
	 * Constructor
	 * 
	 * @param gp
	 */
	public EventHandler(GamePanel gp) {
		super();
		this.gp = gp;

		eventRect = new Rectangle();
		eventRect.x = 23; // center of the tile
		eventRect.y = 23;
		eventRect.width = 2; // 2 pixels wide
		eventRect.height = 2;

		// for resets
		eventRectDefaultX = eventRect.x;
		eventRectDefaultY = eventRect.y;

	}

	public void checkEvent() {
		if (hit(27, 16, "right") == true) {
			damagePit(gp.dialogueState);
		}
		if (hit(23, 12, "up") == true) {
			healingPool(gp.dialogueState);
		}
	}

	private void damagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fell into a pit";
		gp.player.life--; // lose half a heart
	}

	private void healingPool(int gameState) {
		if (gp.keyH.spacePressed) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You gain life";
			gp.player.life++; // gain half a heart
		}
	}

	/**
	 * Did it hit?
	 * 
	 * @param eventCol
	 * @param eventRow
	 * @param reqDirection
	 * @return if hit or not
	 */
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;

		// player's event area
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		// get event rectangle solid area
		eventRect.x = eventCol * gp.tileSize + eventRect.x;
		eventRect.y = eventRow * gp.tileSize + eventRect.y;

		// check if player's solidArea hits eventRect solidArea
		if (gp.player.solidArea.intersects(eventRect)) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}

		// reset areas after checking collision
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect.x = eventRectDefaultX;
		eventRect.y = eventRectDefaultY;

		return hit;
	}

}
