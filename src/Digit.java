import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class Digit
 * @version 1.0
 *
 * Digit
 */
public class Digit extends JPanel{

	private Image digitImage = setImage(0);
	private int digit = 0;

  /**
   * Constructeur
   */
	public Digit(){
		this.setSize(26,32);
	}

  /**
   * Constructeur
   * @param digit Chiffre à afficher
   */
	public Digit(int digit){
		digitImage = setImage(digit);
		this.setSize(26,32);
	}

  /**
   * Constructeur
   * @param digit Chiffre à afficher
   * @param size Coté du carré
   */
	public Digit(int digit, int size){
		digitImage = setImage(digit);
		this.setSize(size,size);
	}

	/**
	 * Affectation de l'image
	 * @param digit Chiffre à afficher
	 * @return Image
	 */
	public Image setImage(int digit){
		this.digit = digit;
		
		try{	

			if(digit >= 0 && digit < 10){
				switch(digit){
					case 0:
						return ImageIO.read(Digit.class.getResource("resources/numbers/0.png"));
					case 1:
						return ImageIO.read(Digit.class.getResource("resources/numbers/1.png"));
					case 2:
						return ImageIO.read(Digit.class.getResource("resources/numbers/2.png"));
					case 3:
						return ImageIO.read(Digit.class.getResource("resources/numbers/3.png"));
					case 4:
						return ImageIO.read(Digit.class.getResource("resources/numbers/4.png"));
					case 5:
						return ImageIO.read(Digit.class.getResource("resources/numbers/5.png"));
					case 6:
						return ImageIO.read(Digit.class.getResource("resources/numbers/6.png"));
					case 7:
						return ImageIO.read(Digit.class.getResource("resources/numbers/7.png"));
					case 8:
						return ImageIO.read(Digit.class.getResource("resources/numbers/8.png"));
					case 9:
						return ImageIO.read(Digit.class.getResource("resources/numbers/9.png"));
				}
			}

		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Modifie le chiffre affiché
	 * @param digit Chiffre à afficher 
	 */
	public void setDigit(int digit){
		digitImage = setImage(digit);
		repaint();
	}

	/**
	 * Retourne le chiffre en String
	 * @return Le chiffre en String
	 */
	public String toString(){
		return Integer.toString(digit);
	}

	/**
	 * Repaint l'image
	 * @param g Graphics 
	 */
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(this.digitImage, 0,0, this.getWidth(), this.getHeight(), this);
	}
}