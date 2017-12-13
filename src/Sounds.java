import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import javax.sound.sampled.*;

/**
 * Class Sounds
 * @version 1.0
 * 
 * Contient le nécessaire pour la gestion des sons
 */
public class Sounds{

	private static float volumeMusic = 0.0f;
	private static float volumeSound = 0.0f;
	private static boolean mutedMusic = false;

	private static AudioPlayer music1 = initMusic();

	/**
	 * Initialise la musique
	 */
	private static AudioPlayer initMusic(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/music1.wav");
			AudioPlayer music1 = new AudioPlayer(file, true);

			return music1;
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	    return null;
	  }
	}

	/**
	 * Modifie le volume sonore de la musique
	 * @param volume volume +/- dB
	 */
	public static void setVolumeMusic(int volume){
		volumeMusic = (float)volume;
		music1.setVolume(volumeMusic);
	}

	/**
	 * Modifie le volume sonore des sons
	 * @param volume volume +/- dB
	 */
	public static void setVolumeSound(int volume){
		volumeSound = (float)volume;
	}

	/**
	 * Retourne le volume de la musique
	 * @return le volume en float
	 */
	public static float getVolumeMusic(){
		return volumeMusic;
	}

	/**
	 * Retourne le volume des sons
	 * @return le volume en float
	 */
	public static float getVolumeSound(){
		return volumeSound;
	}

	/**
	 * Modifie l'état d'activation de la musique
	 * @param bool Active ou désactive la musique
	 */
	public static void muteMusicIntro(boolean bool){
		mutedMusic = bool;

		if(bool)
			music1.stopSound();
		else
			music1.playSound();
	}

	/**
	 * Retourne l'état d'activation de la musique
	 * @return true si la musique est désactivée, false sinon
	 */
	public static boolean musicIsMuted(){
		return mutedMusic;
	}

	/**
	 * Lance la lecture de la musique
	 */
	public static void eventMusicIntro(){
		try{
			music1.setVolume(volumeMusic);
			music1.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son Tir
	 */
	public static void eventSoundShot(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/shotgun.wav");
			AudioPlayer tir = new AudioPlayer(file);

			float volume = volumeSound-8.0f;
			if(volume >= -80.0f)
				tir.setVolume(volume);
			else
				tir.setVolume(-80.0f);

			tir.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son de rechargement
	 */
	public static void eventSoundReload(){
		try{
	    InputStream file = Sounds.class.getResourceAsStream("resources/sounds/reload.wav");
			AudioPlayer reaload = new AudioPlayer(file);

			float volume = volumeSound-2.0f;
			if(volume >= -80.0f)
				reaload.setVolume(volume);
			else
				reaload.setVolume(-80.0f);

			reaload.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son de clic
	 */
	public static void eventSoundButton(){
	  try{
	    InputStream file = Sounds.class.getResourceAsStream("resources/sounds/button.wav");
			AudioPlayer clic = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				clic.setVolume(volume);
			else
				clic.setVolume(-80.0f);

			clic.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son de clic2
	 */
	public static void eventSoundButton2(){
	  try{
	    InputStream file = Sounds.class.getResourceAsStream("resources/sounds/buttonClick2.wav");
			AudioPlayer clic2 = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				clic2.setVolume(volume);
			else
				clic2.setVolume(-80.0f);

			clic2.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son Wood1
	 */
	public static void eventSoundWood1(){
	  try{
	    InputStream file = Sounds.class.getResourceAsStream("resources/sounds/wood1.wav");
			AudioPlayer wood1 = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				wood1.setVolume(volume);
			else
				wood1.setVolume(-80.0f);

			wood1.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son Wood2
	 */
	public static void eventSoundWood2(){
	  try{
	    InputStream file = Sounds.class.getResourceAsStream("resources/sounds/wood2.wav");
			AudioPlayer wood2 = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				wood2.setVolume(volume);
			else
				wood2.setVolume(-80.0f);

			wood2.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son explosion
	 */
	public static void eventSoundExplosion(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/explosion.wav");
			AudioPlayer explosion = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				explosion.setVolume(volume);
			else
				explosion.setVolume(-80.0f);

			explosion.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son scores
	 */
	public static void eventSoundScore(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/levelup.wav");
			AudioPlayer score = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				score.setVolume(volume);
			else
				score.setVolume(-80.0f);

			score.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du son tir dans l'eau
	 */
	public static void eventSoundShotWater(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/shotwater.wav");
			AudioPlayer shotWater = new AudioPlayer(file);

			float volume = volumeSound-5.0f;
			if(volume >= -80.0f)
				shotWater.setVolume(volume);
			else
				shotWater.setVolume(-80.0f);

			shotWater.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}

	/**
	 * Lance la lecture du bruit de porte
	 */
	public static void eventSoundShipDoor(){
		try{
			InputStream file = Sounds.class.getResourceAsStream("resources/sounds/shipDoor1.wav");
			AudioPlayer shipDoor = new AudioPlayer(file);

			float volume = volumeSound;
			if(volume >= -80.0f)
				shipDoor.setVolume(volume);
			else
				shipDoor.setVolume(-80.0f);

			shipDoor.playSound();
	  }
	  catch (Exception e)
	  {
	    e.printStackTrace();
	  }
	}
} 