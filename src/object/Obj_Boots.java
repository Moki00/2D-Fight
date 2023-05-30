package object;

import entity.Entity;
import main.GamePanel;

public class Obj_Boots extends Entity {

	public Obj_Boots(GamePanel gp) {
		super(gp);
		name = "Boots";

		down1 = setup("/objects/boots");

	}

}