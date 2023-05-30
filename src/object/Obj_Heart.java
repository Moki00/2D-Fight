package object;

import entity.Entity;
import main.GamePanel;

/**
 * @author Moki_21_10
 *
 */
public class Obj_Heart extends Entity {

	public Obj_Heart(GamePanel gp) {
		super(gp);
		name = "Heart";

		image = setup("/objects/heart_full");
		image2 = setup("/objects/heart_half");
		image3 = setup("/objects/heart_blank");

	}

}