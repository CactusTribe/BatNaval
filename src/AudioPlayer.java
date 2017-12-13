import java.io.*;
import javax.sound.sampled.*;

/**
 * Class AudioPlayer
 * @version 1.0
 *
 * Construit un objet permettant de jouer un fichier son
 */
public class AudioPlayer{
	private AudioInputStream audioInputStream = null;
	private InputStream file;
	private FloatControl gainControl;
	private Clip clip;
	private boolean loop = false;

	/**
	 * Constructeur
	 * @param file Fichier .wav récupéré par InputStream
	 */
	public AudioPlayer(InputStream file){
		this.file = file;

		try{
			InputStream bufferedIn = new BufferedInputStream(file);
			audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Constructeur
	 * @param file Fichier .wav récupéré par InputStream
	 * @param loop Active ou désactive la fonction boucle
	 */
	public AudioPlayer(InputStream file, boolean loop){
		this.file = file;
		this.loop = loop;

		try{
			InputStream bufferedIn = new BufferedInputStream(file);
			audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Lance la lecture du son
	 */
	public void playSound(){
		if(this.loop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		clip.start();
	}

	/**
	 * Arrête la lecture du son
	 */
	public void stopSound(){
		clip.stop();
	}

	/**
	 * Modifie le volume du son
	 * @param volume Volume de type Float (décibel) +/- dB
	 */
	public void setVolume(Float volume){
		gainControl.setValue(volume);
	}
}


