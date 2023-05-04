package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Obj_Boots extends SuperObject {
	GamePanel gp;

	public Obj_Boots(GamePanel gp) {
		this.gp = gp;
		name = "Boots";

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			util.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}