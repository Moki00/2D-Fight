package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	/**
	 * Constructor
	 * 
	 * @param gp
	 */
	public NPC_OldMan(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;

		getOldManImage();
		setDialogue();

	}

	public void getOldManImage() {

		right1 = setup("/npc/oldman_r1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_r2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_d1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_d2", gp.tileSize, gp.tileSize);
	}

	/**
	 * Things the Old Man says
	 */
	public void setDialogue() {
		dialogues[0] = "Good-day to you.";
		dialogues[1] = "How are you? \nIt's a lovely day.";
		dialogues[2] = "I'm looking for my cat.";
		dialogues[3] = "What are you looking for?";
	}

	/**
	 * different behavior with random actions
	 */
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

	public void speak() {
		super.speak();
	}

}
