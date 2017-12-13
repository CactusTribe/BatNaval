import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu extends JPanel{

	private static final Image fond = setBackground();

	ImageIcon nouvelleNormal;
	ImageIcon nouvelleHover;
	ImageIcon nouvellePush;

	ImageIcon rejoindreNormal;
	ImageIcon rejoindreHover;
	ImageIcon rejoindrePush;

	ImageIcon optionsNormal;
	ImageIcon optionsHover;
	ImageIcon optionsPush;

	ImageIcon quitterNormal;
	ImageIcon quitterHover;
	ImageIcon quitterPush;

	JButton buttonNouvellePartie;
	JButton buttonRejoindrePartie;
	JButton buttonOptions;
	JButton buttonQuitter;

	/**
	 * Constructeur
	 */
	public Menu(){
		init();
	}

	/**
	 * Initialisation des attributs
	 */
	public void init(){
		this.setSize(800,625);
		this.setPreferredSize(new Dimension(800, 625));
		this.setLayout(null);

		nouvelleNormal = new ImageIcon(getClass().getResource("resources/boutons/nouvelleNormal.png"));
		nouvelleHover = new ImageIcon(getClass().getResource("resources/boutons/nouvelleHover.png"));
		nouvellePush = new ImageIcon(getClass().getResource("resources/boutons/nouvellePush.png"));

		rejoindreNormal = new ImageIcon(getClass().getResource("resources/boutons/rejoindreNormal.png"));
		rejoindreHover = new ImageIcon(getClass().getResource("resources/boutons/rejoindreHover.png"));
		rejoindrePush = new ImageIcon(getClass().getResource("resources/boutons/rejoindrePush.png"));

		optionsNormal = new ImageIcon(getClass().getResource("resources/boutons/optionsNormal.png"));
		optionsHover = new ImageIcon(getClass().getResource("resources/boutons/optionsHover.png"));
		optionsPush = new ImageIcon(getClass().getResource("resources/boutons/optionsPush.png"));

		quitterNormal = new ImageIcon(getClass().getResource("resources/boutons/quitterNormal.png"));
		quitterHover = new ImageIcon(getClass().getResource("resources/boutons/quitterHover.png"));
		quitterPush = new ImageIcon(getClass().getResource("resources/boutons/quitterPush.png"));

		buttonNouvellePartie = new JButton();
		buttonNouvellePartie.setOpaque(false);
		buttonNouvellePartie.setUI(new CustomizedButtonUI(nouvelleNormal, nouvelleHover, nouvellePush));
		buttonNouvellePartie.setBounds(250,200, 280, 70);
		buttonNouvellePartie.setContentAreaFilled(false);
		buttonNouvellePartie.setBorderPainted(false);

		buttonRejoindrePartie = new JButton();
		buttonRejoindrePartie.setOpaque(false);
		buttonRejoindrePartie.setUI(new CustomizedButtonUI(rejoindreNormal, rejoindreHover, rejoindrePush));
		buttonRejoindrePartie.setBounds(250,280, 280, 70);
		buttonRejoindrePartie.setContentAreaFilled(false);
		buttonRejoindrePartie.setBorderPainted(false);

		buttonOptions = new JButton();
		buttonOptions.setOpaque(false);
		buttonOptions.setUI(new CustomizedButtonUI(optionsNormal, optionsHover, optionsPush));
		buttonOptions.setBounds(250,360, 280, 70);
		buttonOptions.setContentAreaFilled(false);
		buttonOptions.setBorderPainted(false);	

		buttonQuitter = new JButton();
		buttonQuitter.setOpaque(false);
		buttonQuitter.setUI(new CustomizedButtonUI(quitterNormal, quitterHover, quitterPush));
		buttonQuitter.setBounds(250,440, 280, 70);
		buttonQuitter.setContentAreaFilled(false);
		buttonQuitter.setBorderPainted(false);

		this.add(buttonNouvellePartie);
		this.add(buttonRejoindrePartie);
		this.add(buttonOptions);
		this.add(buttonQuitter);
	}

	/**
	 * Retourne le JButton NouvellePartie
	 * @return le JButton nouvellePartie
	 */
	public JButton getButtonNouvellePartie(){
		return buttonNouvellePartie;
	}

	/**
	 * Retourne le JButton RejoindrePartie
	 * @return le JButton rejoindrePartie
	 */
	public JButton getButtonRejoindrePartie(){
		return buttonRejoindrePartie;
	}

	/**
	 * Retourne le JButton Options
	 * @return le JButton options
	 */
	public JButton getButtonOptions(){
		return buttonOptions;
	}

	/**
	 * Retourne le JButton Quitter
	 * @return le JButton quitter
	 */
	public JButton getButtonQuitter(){
		return buttonQuitter;
	}

	/**
	 * Affectation de l'icon correspondant à la case
	 * @return Image
	 */
	static protected Image setBackground(){
		try{	
			return ImageIO.read(Menu.class.getResource("resources/menu.png"));	
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Repaint l'image de la case
	 * @param g Graphics 
	 */
	public void paintComponent(Graphics g){
		g.drawImage(this.fond, 0,0, this.getWidth(), this.getHeight(), this);
	}

}