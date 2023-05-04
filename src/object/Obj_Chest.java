package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Obj_Chest extends SuperObject {
	GamePanel gp;

	public Obj_Chest(GamePanel gp) {
		this.gp = gp;
		name = "Chest";

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			util.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}