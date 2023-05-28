package main;

/**
 * Handle events around the player like hitting
 * 
 * @author Moki_21_10
 *
 */
public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];

	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	/**
	 * Constructor
	 * 
	 * @param gp
	 */
	public EventHandler(GamePanel gp) {
		super();
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;

		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[col][row] = new EventRect(); // object created from my class
			eventRect[col][row].x = 23; // center of the tile
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2; // 2 pixels wide
			eventRect[col][row].height = 2;

			// for resets
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}

	public void checkEvent() {

		// Player can touch event again if more than 1 tile away from last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}

		if (canTouchEvent) {
			if (hit(27, 16, "right") == true) {
				damagePit(27, 16, gp.dialogueState);
			}
			if (hit(22, 16, "left") == true) {
				damagePit(22, 16, gp.dialogueState);
			}
			if (hit(23, 12, "up") == true) {
				healingPool(23, 12, gp.dialogueState);
			}
			if (hit(27, 27, "right") == true) {
				teleport(gp.dialogueState);
			}
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
	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;

		// player's event area
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		// get event rectangle solid area
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		// check if player's solidArea hits eventRect solidArea,
//		and if event is not	DONE
		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;

				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;

			}
		}

		// reset areas after checking collision
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	private void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You teleported";
		gp.player.worldX = gp.tileSize * 23;
		gp.player.worldY = gp.tileSize * 22;
	}

	private void damagePit(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fell into a pit";
		gp.player.life--; // lose half a heart
		eventRect[col][row].eventDone = true; // this destroys the damagePit
		canTouchEvent = false;
	}

	private void healingPool(int col, int row, int gameState) {
		if (gp.keyH.spacePressed) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You gain life";
			gp.player.life = gp.player.maxLife; // gain half a heart
		}
	}

}
