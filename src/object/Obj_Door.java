package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Obj_Door extends SuperObject {
	GamePanel gp;

	public Obj_Door(GamePanel gp) {
		this.gp = gp;
		name = "Door";

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			util.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		collision = true;

	}

}