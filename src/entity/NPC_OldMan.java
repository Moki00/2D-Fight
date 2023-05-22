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

		right1 = setup("/npc/oldman_r1");
		right2 = setup("/npc/oldman_r2");
		left1 = setup("/npc/oldman_left_1");
		left2 = setup("/npc/oldman_left_2");
		up1 = setup("/npc/oldman_up_1");
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_d1");
		down2 = setup("/npc/oldman_d2");
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
			System.out.println(num + ": old man goes: " + direction);

			actionCounter = 0;
		}
	}

	public void speak() {
		super.speak();
	}

}
