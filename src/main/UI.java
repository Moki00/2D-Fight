package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

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
	Font arial_40, arial_80B;
//	BufferedImage keyIcon;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter;
	public boolean gameFinished = false;

	double playTime;
	DecimalFormat decFormat = new DecimalFormat("#0.0");

	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
//		Obj_Key key = new Obj_Key(gp);
//		keyIcon = key.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(arial_40);
		g2.setColor(Color.white);

		if (gp.gameState == gp.playState) {
			// play state
		} else if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		} else {
			System.err.print("error at UI draw gameState: " + gp.gameState);
		}
	}

	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}

	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - textLength / 2;
		return x;
	}

}
