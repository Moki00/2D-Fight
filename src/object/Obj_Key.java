package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * The key to open doors
 * 
 * @author Moki_21_10
 *
 */
public class Obj_Key extends SuperObject {

	GamePanel gp;

	public Obj_Key(GamePanel gp) {

		this.gp = gp;

		name = "Key";

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			util.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// unique solid area for each item
//		solidArea.x = 5;

	}

}
