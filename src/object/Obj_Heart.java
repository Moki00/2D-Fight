package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * @author Moki_21_10
 *
 */
public class Obj_Heart extends SuperObject {
	GamePanel gp;

	public Obj_Heart(GamePanel gp) {
		this.gp = gp;
		name = "Heart";

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			util.scaleImage(image, gp.tileSize, gp.tileSize);
			util.scaleImage(image2, gp.tileSize, gp.tileSize);
			util.scaleImage(image3, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}