package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Util {

	/**
	 * Scales images (tiles) before game starts
	 * 
	 * @param original image
	 * @param width    of image
	 * @param height   of image
	 * @return scaled Image
	 */
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		// BufferedImage constructor: width, height, imageType as integers
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		// thisBufferedImage.createGraphics creates a Graphics2D used to draw
		Graphics2D g2 = scaledImage.createGraphics();
		// Draw the tile into the g2 scaledImage as a buffered image
		g2.drawImage(original, 0, 0, width, height, null);
		return scaledImage;
	}
}
