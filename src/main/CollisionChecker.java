package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {

		// the 4 points of the entity
		int entityLeftWorldX = entity.worldX + entity.solidArea.x; // 0+12=12 from the left
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize; // 12/48=0 as in the [0] first column
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2; // only check 2 tiles at a time

		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; // 48-10/48=0
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
				System.out.println("Cannot go up here");
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
				System.out.println("Cannot go down here");
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
				System.out.println("Cannot go left here");
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
				System.out.println("Cannot go right here");
			}
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + entity.direction);
		}
	}

	/**
	 * if player hits an object, interact with that object
	 * 
	 * @param entity
	 * @param player
	 * @return index in the list of objects
	 */
	public int checkObject(Entity entity, boolean player) {

		int index = 999;

		for (int i = 0; i < gp.obj.length; i++) {

			if (gp.obj[i] != null) {

				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// get the object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.collisionOn = true;
							System.out.println("Cannot go up here");
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.collisionOn = true;
							System.out.println("Cannot go down here");
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.collisionOn = true;
							System.out.println("Cannot go left here");
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if (gp.obj[i].collision) {
							entity.collisionOn = true;
							System.out.println("Cannot go right here");
						}
						if (player) {
							index = i;
						}
					}
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + entity.direction);
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}
		}

		return index;
	}
}
