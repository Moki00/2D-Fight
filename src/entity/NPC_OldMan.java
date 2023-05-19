package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;
		packageClass = "";

		getOldManImage();

	}

	public void getOldManImage() {

		right1 = setup("/npc/oldman_r1");
		right2 = setup("/npc/oldman_r2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_d1");
		down2 = setup("/npc/oldman_d2");
	}

	public void setAction() {
		Random random = new Random();
		int i = random.nextInt(100) + 1;
	}

}
