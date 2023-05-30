package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Door extends Entity {

	public Obj_Door(GamePanel gp) {
		super(gp);
		name = "Door";

		down1 = setup("/objects/door");

		collision = true;

		// unique solid area for an item
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

	}

}