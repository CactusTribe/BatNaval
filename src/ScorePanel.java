import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class ScorePanel
 * @version 1.0
 *
 * Contient les scores
 */
public class ScorePanel extends JPanel{

	private JLabel labelScores;
	private JLabel labelWinBIG;
	private JLabel labelLoseBIG;
	private JLabel line1;
	private JLabel line2;
	private ImageIcon img_Scores; 
	private ImageIcon img_winBIG;
	private ImageIcon img_loseBIG;  
	private ImageIcon img_line1; 
	private ImageIcon img_line2; 

	private boolean victory;

	/**
	 * Constructeur
	 */
	public ScorePanel(){
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
		this.victory = false;

		img_Scores = new ImageIcon(getClass().getResource("resources/labelScores.png"));
		labelScores = new JLabel(img_Scores);
		labelScores.setBounds(0,0, 300, 60);

		img_line1 = new ImageIcon(getClass().getResource("resources/line1.png"));
		line1 = new JLabel(img_line1);
		line1.setBounds(0,50, 300, 40);

		img_line2 = new ImageIcon(getClass().getResource("resources/line2.png"));
		line2 = new JLabel(img_line2);
		line2.setBounds(0,335, 300, 40);

		img_winBIG = new ImageIcon(getClass().getResource("resources/victoryBig.png"));
		labelWinBIG = new JLabel(img_winBIG);
		labelWinBIG.setBounds(0,90, 300, 250);

		img_loseBIG = new ImageIcon(getClass().getResource("resources/deathBig.png"));
		labelLoseBIG = new JLabel(img_loseBIG);
		labelLoseBIG.setBounds(0,90, 300, 250);

		update();
	}

	/**
	 * Met à jour l'aspect graphique
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		this.add(labelScores);
		this.add(line1);

		if(victory)
			this.add(labelWinBIG);
		else
			this.add(labelLoseBIG);
		this.add(line2);
	}

	/**
	 * Permet de passer en mode victoire ou non le panel
	 * @param bool Victoire ou défaite
	 */
	public void setVictory(boolean bool){
		this.victory = bool;
		update();
	}
}