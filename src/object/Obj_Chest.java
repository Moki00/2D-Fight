package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Chest extends Entity {

	public Obj_Chest(GamePanel gp) {
		super(gp);
		name = "Chest";

		down1 = setup("/objects/chest");

	}

}