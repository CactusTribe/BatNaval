import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class Plateau
 * @version 1.0
 *
 * Contient l'objet de type GrilleGUI
 */
public class Plateau extends JPanel{

	private static final Image fond = setImage();
	private GrilleGUI grilleGUI;
	
	/**
	 * Constructeur
	 */
	public Plateau(){
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.setPreferredSize(new Dimension(560, 575));
		this.setLayout(null);

		grilleGUI = new GrilleGUI();
		
		this.add(grilleGUI);
	}

	/**
	 * Met à jour l'aspect graphique
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		this.grilleGUI.update();

		this.add(grilleGUI);
	}

	public void reset(){
		grilleGUI = new GrilleGUI();
		update();
	}

	/**
	 * Retourne la grille
	 * @return la GrilleGUI
	 */
	public GrilleGUI getGrilleGUI(){
		return this.grilleGUI;
	}

	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
			return ImageIO.read(Plateau.class.getResource("resources/wood-frame.png"));	
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Repaint l'image
	 * @param g Graphics 
	 */
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(this.fond, 0,0, this.getWidth(), this.getHeight(), this);
	}
}