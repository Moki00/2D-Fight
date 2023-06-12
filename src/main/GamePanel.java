package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
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
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();

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
		assetSetter.setMonster();
//		playMusic(0); // music at player select

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

			// PLAYER
			player.update();

			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}

			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if (monster[i].alive && monster[i].dying==false) {
						monster[i].update(); // update if alive & not dying
					}
					if (monster[i].alive == false) {
						monster[i] = null; // null if not alive
					}
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

			// add player to Entity List
			entityList.add(player);

			// add NPCs to Entity List
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}

			// add Objects to Entity List
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}

			// add Monsters to Entity List
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}

			// Sort by World Y
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e1.worldY);
					return result;
				}
			});

			// Draw each Entity
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// Empty Entity List
			entityList.clear();

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
