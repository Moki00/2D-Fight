package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

	// Debug
	public boolean checkDrawTime = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// empty, for implementation
	}

	/**
	 * Pressing keys on keyboard
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// Title Screen
		if (gp.gameState == gp.titleState) {

			// Main Title Screen
			if (gp.ui.titleScreenState == 0) {

				// Up
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.ui.titleChoice--;
					if (gp.ui.titleChoice < 0) {
						gp.ui.titleChoice = 3;
					}
				}

				// Down
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.ui.titleChoice++;
					if (gp.ui.titleChoice > 3) {
						gp.ui.titleChoice = 0;
					}
				}

				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {

					// Load Game
					if (gp.ui.titleChoice == 0) {
						// add Load saved Game
						System.out.println("add: Load a saved game");
					}

					// New Game
					if (gp.ui.titleChoice == 1) {
						gp.ui.titleScreenState = 1; // create character
//						gp.playMusic(0); // mystery music: who will you be?
						gp.ui.titleChoice = 0; // reset the choice;
					}

					// Credits
					if (gp.ui.titleChoice == 2) {
						// go to credit screen
						System.out.println("credits");
					}

					// Quit
					if (gp.ui.titleChoice == 3) {
						System.exit(0);
					}

				} // end space or enter selection

			} // end Main Title Screen

			// Role selection Screen for "New Game"
			else if (gp.ui.titleScreenState == 1) {

				// Up
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.ui.titleChoice--;
					if (gp.ui.titleChoice < 0) {
						gp.ui.titleChoice = 3;
					}
				}

				// Down
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.ui.titleChoice++;
					if (gp.ui.titleChoice > 3) {
						gp.ui.titleChoice = 0;
					}
				}

				if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {

					// Monk
					if (gp.ui.titleChoice == 0) {
						System.out.println("add: Monk stats; start from Temple");
						gp.gameState = gp.playState;
						gp.playMusic(0); // change to Temple music
					}

					// Ranger
					if (gp.ui.titleChoice == 1) {
						System.out.println("add: Ranger stats; start from Forest");
						gp.gameState = gp.playState;
						gp.playMusic(0); // change to Forest music
					}

					// Mage
					if (gp.ui.titleChoice == 2) {
						System.out.println("add: Mage stats; start from Citadel");
						gp.gameState = gp.playState;
						gp.playMusic(0); // change to Citadel music
					}

					// Cancel, Return to title screen
					if (gp.ui.titleChoice == 3) {
						gp.ui.titleScreenState = 0;
						gp.ui.titleChoice = 0; // reset the choice;
					}

				} // end space or enter selection

			} // end Role selection Screen

		} // end Title Screen

		// Play State
		if (gp.gameState == gp.playState) {

			// Up
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			// Down
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			// Left
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			// Right
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			// Pause
			if (code == KeyEvent.VK_P || code == KeyEvent.VK_O) {
				gp.gameState = gp.pauseState;
			}
			// Action
			if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
				// System.out.println(spacePressed);
				spacePressed = true;
			}

			// Debug time check
			if (code == KeyEvent.VK_T || code == KeyEvent.VK_Y) {
				if (checkDrawTime == false) {
					checkDrawTime = true;
				} else if (checkDrawTime) {
					checkDrawTime = false;
				}
			}
		}

		// Pause State -> return to Play
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P || code == KeyEvent.VK_O) {
				gp.gameState = gp.playState;
			}
		}

		// In Dialogue
		else if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
				gp.gameState = gp.playState;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		// Up
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		// Down
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		// Left
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		// Right
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}

//		if (code == KeyEvent.VK_SPACE) {
//			spacePressed = false;
//		}

	}

}
