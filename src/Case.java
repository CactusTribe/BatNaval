import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Abstract Class Case
 * @version 1.0
 *
 * Contient tout les élements communs aux cases
 */
abstract class Case extends JPanel{

	private static Image target = setImage();
	private boolean targeted;
	private boolean targetedEnabled;

	/**
	 * Constructeur
	 */
	public Case(){
		init();
	}

	private void init(){
		this.setPreferredSize(new Dimension(50,50));
		targeted = false;
	}

	/**
	 * Affiche un contour sur la case
	 * @param bool Active ou désactive le contour noir
	 */
	public void setBorder(boolean bool){
		if(bool)
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		else
			setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	}

	/**
	 * Place le curseur de visé sur la case
	 * @param bool Affiche ou retire le curseur 
	 */
	public void setTargeted(boolean bool){
		targeted = bool;
		this.repaint();
	}
	
	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
			return ImageIO.read(Case.class.getResource("resources/target1.png"));	
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
		if(targeted){
			g.drawImage(this.target, 0,0, this.getWidth(), this.getHeight(), this);
		}
	}

}