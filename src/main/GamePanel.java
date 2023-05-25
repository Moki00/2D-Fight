package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

/**
 * 
 * This is where we can view the game
 * 
 * @author Moki_2023
 *
 */
public class GamePanel extends JPanel implements Runnable {

	/**
	 * auto generated serial ID
	 */
	private static final long serialVersionUID = -4611689532662755630L;

	/**
	 * Screen Settings
	 */
	final int originalTileSize = 16;
	final int scale = 3; // scale for large monitor
	public final int tileSize = originalTileSize * scale; // 48x48 tiles
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	/**
	 * World Settings (whole map)
	 */
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	// FPS
	final int Fps = 60;

	// System
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);

	public Sound music = new Sound();
	public Sound soundEffect = new Sound();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public AssetSetter assetSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eventHandler = new EventHandler(this);
	Thread gameThread;

	/**
	 * Entities and Objects
	 */
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];

	/**
	 * Game States
	 */
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;

	/**
	 * Game Panel Constructor ("gp" in other classes)
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNpc();
		playMusic(0); // main song
		stopMusic(); ///// temp stopping music -remove later-

		// Start at Title Screen
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		double drawInterval = 1_000_000_000 / Fps; // update every 0.0167 seconds
		double delta = 0;
		double lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update(); // Update info as position
				repaint(); // Draw/paint the screen
				delta--;
				drawCount++;
			}

			if (timer >= 1_000_000_000) {
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	/**
	 * updates the game, unless paused
	 */
	public void update() {

		if (gameState == playState) {
			player.update();
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
		}

		if (gameState == pauseState) {
			// do not update the player
		}
	}

	/**
	 * Repaint method comes from here
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Debug start timer
		long drawStart = 0;
		if (keyH.checkDrawTime) {
			drawStart = System.nanoTime();
		}

		// Title Screen
		if (gameState == titleState) {
			ui.draw(g2);
			//
		}

		// non-Title Screen
		else {

			// Tile background
			tileM.draw(g2);

			// Object array
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}

			// NPC list
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].draw(g2);
				}
			}

			// player must be after tiles
			player.draw(g2);

			// UI labels last
			ui.draw(g2);

		} // close the else for non-title screen

		// Debug end timer
		if (keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Time passed: " + passed, 10, 400);
			System.out.println("Time passed: " + passed);
		}

		g2.dispose();

	}

	/**
	 * Music must loop
	 * 
	 * @param i
	 */
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	/**
	 * Sound effects do not loop
	 * 
	 * @param i
	 */
	public void playSoundEffect(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}

}
