import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;
/**
 * Class OptionsPanel
 * @version 1.0
 *
 * Contient les champs des options
 */
public class OptionsPanel extends JPanel{

	private JLabel labelOptions;
	private JLabel line1;
	private JLabel line2;
	private JLabel labelVolumeMusic;
	private JLabel labelVolumeSound;
	private JLabel labelActiveMusic;

	private ImageIcon img_Options;  
	private ImageIcon img_line1; 
	private ImageIcon img_line2; 
	private ImageIcon img_VolumeMusic;
	private ImageIcon img_VolumeSound;
	private ImageIcon img_ActiveMusic;

	private JSlider slideMusic;
	private JSlider slideSound;
	private JCheckBox boxActivateMusic;

	/**
	 * Constructeur
	 */
	public OptionsPanel(){
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.setLayout(null);
		this.setSize(300,375);
		this.setPreferredSize(new Dimension(300,375));
		this.setOpaque(false);

		img_Options = new ImageIcon(getClass().getResource("resources/labelOptions.png"));
		labelOptions = new JLabel(img_Options);
		labelOptions.setBounds(0,0, 300, 60);

		img_line1 = new ImageIcon(getClass().getResource("resources/line1.png"));
		line1 = new JLabel(img_line1);
		line1.setBounds(0,50, 300, 40);

		img_VolumeMusic = new ImageIcon(getClass().getResource("resources/labelVolumeMusic.png"));
		labelVolumeMusic = new JLabel(img_VolumeMusic);
		labelVolumeMusic.setBounds(0,90, 300, 60);

		img_VolumeSound = new ImageIcon(getClass().getResource("resources/labelVolumeSound.png"));
		labelVolumeSound = new JLabel(img_VolumeSound);
		labelVolumeSound.setBounds(0,170, 300, 60);

		img_ActiveMusic = new ImageIcon(getClass().getResource("resources/labelActivateMusic.png"));
		labelActiveMusic = new JLabel(img_ActiveMusic);
		labelActiveMusic.setBounds(10,250, 300, 60);

		img_line2 = new ImageIcon(getClass().getResource("resources/line2.png"));
		line2 = new JLabel(img_line2);
		line2.setBounds(0,335, 300, 40);

		int volumeMusic = 80+(((int)Sounds.getVolumeMusic())*8);
		int volumeSound = 80+(((int)Sounds.getVolumeSound())*8);

		slideMusic = new JSlider(0,80, volumeMusic);
		slideMusic.setOpaque(false);
		slideMusic.setBounds(42,117,210,30);

		slideSound = new JSlider(0,80, volumeSound);
		slideSound.setOpaque(false);
		slideSound.setBounds(42,197,210,30);

		boxActivateMusic = new JCheckBox();
		boxActivateMusic.setOpaque(false);
		boxActivateMusic.setBounds(221,267,30,30);

		// Si la musique n'est pas mutted on active la checkbox, sinon non
		if(!Sounds.musicIsMuted())
			boxActivateMusic.setSelected(true);
		else
			boxActivateMusic.setSelected(false);

		// Slide volume musique
		slideMusic.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){
				Sounds.setVolumeMusic((slideMusic.getValue()-80)/8);
			}
		});

		// Slide volume des sons
		slideSound.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){
				Sounds.setVolumeSound((slideSound.getValue()-80)/8);
			}
		});

		// CheckBox activation de la musique
		boxActivateMusic.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){
				if(boxActivateMusic.isSelected()){
					Sounds.muteMusicIntro(false);
				}
				else{
					Sounds.muteMusicIntro(true);
				}
			}
		});

		update();
	}

	/**
	 * Met à jour
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		this.add(labelOptions);
		this.add(line1);

		this.add(slideMusic);
		this.add(slideSound);
		this.add(boxActivateMusic);
		this.add(labelVolumeMusic);
		this.add(labelVolumeSound);
		this.add(labelActiveMusic);

		this.add(line2);
	}
}