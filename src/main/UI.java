package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;

/**
 * 
 * in game display
 * 
 * @author Moki_21_10
 *
 */
public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maru, puri;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int titleChoice = 0;
	public int titleScreenState = 0;

//	BufferedImage keyIcon;
//	double playTime;
//	DecimalFormat decFormat = new DecimalFormat("#0.0");

	/**
	 * Constructor
	 * 
	 * @param gp
	 */
	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream input = getClass().getResourceAsStream("/font/MaruMonica.ttf");
			maru = Font.createFont(Font.TRUETYPE_FONT, input);

			input = getClass().getResourceAsStream("/font/PurisaBold.ttf");
			puri = Font.createFont(Font.TRUETYPE_FONT, input);

		} catch (Exception e) {
			e.printStackTrace();
		}

//		Obj_Key key = new Obj_Key(gp);
//		keyIcon = key.image;
	}

	/**
	 * Show Message on screen
	 * 
	 * @param text
	 */
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	/**
	 * Draw the User Interface
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(maru);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// Title State
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		// Play State
		if (gp.gameState == gp.playState) {

		}

		// Pause State
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}

		// Dialogue State
		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	}

	/**
	 * Draw Title Screen
	 */
	private void drawTitleScreen() {

		if (titleScreenState == 0) {

			// Background
			g2.setColor(Color.black);
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

			// Declare and instantiate text and location
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 92));
			String text = "Moki's Adventure!";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 3;

			// Shadow
			g2.setColor(new Color(120, 120, 120));
			g2.drawString(text, x + 5, y + 5);

			// Title at top over Shadow
			g2.setColor(Color.white);
			g2.drawString(text, x, y);

			// Main Character Image in Middle
			x = gp.screenWidth / 2 - (gp.tileSize);
			y += gp.tileSize;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

			// Menu at bottom
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			text = "Load Game";
			x = getXforCenteredText(text);
			y += gp.tileSize * 4;
			g2.drawString(text, x, y);

			if (titleChoice == 0) {
				g2.drawString("→", x - gp.tileSize, y);
				// ⇒
			}

			text = "New Game";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (titleChoice == 1) {
				g2.drawString("→", x - gp.tileSize, y);
			}

			text = "Credits";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (titleChoice == 2) {
				g2.drawString("→", x - gp.tileSize, y);
			}

			text = "Quit";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);

			if (titleChoice == 3) {
				g2.drawString("→", x - gp.tileSize, y);
			}
		}

		// create character with "New Game"
		else if (titleScreenState == 1) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			String text = "Select your role:";
			int x, y;
			x = getXforCenteredText(text);
			y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			text = "Monk";
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if (titleChoice == 0) {
				g2.drawString("→", x - gp.tileSize, y);
			}

			text = "Ranger";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (titleChoice == 1) {
				g2.drawString("→", x - gp.tileSize, y);
			}

			text = "Mage";
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (titleChoice == 2) {
				g2.drawString("→", x - gp.tileSize, y);
			}

			text = "Cancel";
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if (titleChoice == 3) {
				g2.drawString("→", x - gp.tileSize, y);
			}
		}

	}

	private void drawDialogueScreen() {

		// Window
		int x, y, width, height;
		x = gp.tileSize * 2;
		y = gp.tileSize / 4;
		width = gp.screenWidth - (gp.tileSize * 4);
		height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);

		// Text
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawSubWindow(int x, int y, int width, int height) {

		int opacity = 220;
		Color black = new Color(0, 0, 0, opacity);
		g2.setColor(black);
		int arcWidth, arcHeight;
		arcWidth = 35;
		arcHeight = 35;
		g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

		Color white = new Color(255, 255, 255);
		g2.setColor(white);
		int thickness = 5;
		g2.setStroke(new BasicStroke(thickness));
		x += 5;
		y += 5;
		width -= 10;
		height -= 10;
		arcWidth -= 10;
		arcHeight -= 10;
		g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	/**
	 * Draw the Pause Screen
	 */
	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}

	/**
	 * Get x-location of text to center
	 * 
	 * @param text
	 * @return x as an integer
	 */
	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}

}
