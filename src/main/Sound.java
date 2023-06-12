package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	Clip clip;
	URL soundUrl[] = new URL[30];

	public Sound() {
		soundUrl[0] = getClass().getResource("/sound/adventure.wav");
		soundUrl[1] = getClass().getResource("/sound/coin.wav");
		soundUrl[2] = getClass().getResource("/sound/powerUp.wav");
		soundUrl[3] = getClass().getResource("/sound/unlock.wav");
		soundUrl[4] = getClass().getResource("/sound/fanfare.wav");
		soundUrl[5] = getClass().getResource("/sound/hitMonster.wav");
		soundUrl[6] = getClass().getResource("/sound/receiveDamage.wav");
		soundUrl[7] = getClass().getResource("/sound/swingSword.wav");
//		soundUrl[8] = getClass().getResource("/sound/.wav");
//		soundUrl[9] = getClass().getResource("/sound/.wav");
//		soundUrl[10] = getClass().getResource("/sound/.wav");
//		soundUrl[11] = getClass().getResource("/sound/.wav");
//		soundUrl[12] = getClass().getResource("/sound/.wav");
//		soundUrl[13] = getClass().getResource("/sound/.wav");
//		soundUrl[14] = getClass().getResource("/sound/.wav");
//		soundUrl[15] = getClass().getResource("/sound/.wav");
//		soundUrl[16] = getClass().getResource("/sound/.wav");
//		soundUrl[17] = getClass().getResource("/sound/.wav");
//		soundUrl[]=getClass().getResource("/sound/.wav");
	}

	/**
	 * Set the sound file to play
	 * 
	 * @param i
	 */
	public void setFile(int i) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(soundUrl[i]);
			clip = AudioSystem.getClip();
			clip.open(audio);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		clip.stop();
	}

}
