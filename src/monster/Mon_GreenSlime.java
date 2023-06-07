package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class Mon_GreenSlime extends Entity {

	GamePanel gp;

	public Mon_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = "monster";
		name = "GreenSlime";
		speed = 1;
		maxLife = 4;
		life = maxLife;

		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {
		up1 = setup("/monster/greenslime_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenslime_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenslime_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenslime_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenslime_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenslime_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenslime_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenslime_2", gp.tileSize, gp.tileSize);
	}

	public void setAction() {
		actionCounter++;

		if (actionCounter == 120) {
			Random random = new Random();
			int num = random.nextInt(100); // number from 0 to 99
			if (num < 25) {
				direction = "up";
			} else if (num > 24 && num < 50) {
				direction = "down";
			} else if (num > 49 && num < 75) {
				direction = "right";
			} else {
				direction = "left";
			}

			actionCounter = 0;
		}
	}

}
