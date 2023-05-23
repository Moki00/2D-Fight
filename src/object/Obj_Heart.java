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
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			image = util.scaleImage(image, gp.tileSize, gp.tileSize);
			image2 = util.scaleImage(image2, gp.tileSize, gp.tileSize);
			image3 = util.scaleImage(image3, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}