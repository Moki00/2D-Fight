package object;

import entity.Entity;
import main.GamePanel;

/**
 * The key to open doors
 * 
 * @author Moki_21_10
 *
 */
public class Obj_Key extends Entity {

	public Obj_Key(GamePanel gp) {

		super(gp); // Entity

		name = "Key";
		down1 = setup("/objects/key");

		// unique solid area for an item
//		solidArea.x = 5;

	}

}
