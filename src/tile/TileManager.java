package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.Util;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[46];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
//		loadMap("/maps/mapOfField.txt");
//		loadMap("/maps/mapWorldSimple.txt");
		loadMap("/maps/worldV3.txt");
	}

	/**
	 * run with setup method: setup(int index, String imageName, boolean collision)
	 */
	public void getTileImage() {

		// Unused
		setup(0, "grass00", false);
		setup(1, "grass00", true);
		setup(2, "grass00", true);
		setup(3, "grass00", false);
		setup(4, "grass00", true);
		setup(5, "grass00", false);
		setup(6, "grass00", false);
		setup(7, "grass00", false);
		setup(8, "grass00", false);
		setup(9, "grass00", false);

		// Grass
		setup(10, "grass00", false);
		setup(11, "grass01", false);
		// Water
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		// Road
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		// singles
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
		setup(42, "hut", true);
		setup(43, "table01", false);
		setup(44, "stairsDown", false);
		setup(45, "stairsUp", false);
//		setup(46, "", false);

	}

	/**
	 * handle all half-duplicated lines here. Instantiation, import image, scale and
	 * set collision
	 * 
	 * @param index
	 * @param imageName
	 * @param collision
	 */
	public void setup(int index, String imageName, boolean collision) {

		Util util = new Util();

		try {

			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = util.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param mapFile
	 */
	public void loadMap(String mapFile) {
		try {
			InputStream iS = getClass().getResourceAsStream(mapFile);
			BufferedReader bR = new BufferedReader(new InputStreamReader(iS));
			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = bR.readLine();

				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}

				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			bR.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draw 2D image
	 * 
	 * @param g2
	 */
	public void draw(Graphics2D g2) {

		int worldCol = 0, worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			// whole world
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;

			// screen around Player
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			// only show the tiles around the player
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				// no need to scale during game loop anymore
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}

			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}
}
