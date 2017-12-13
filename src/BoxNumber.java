import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class BoxNumber
 * @version 1.0
 *
 * Permet l'affichage de nombres sous forme d'images
 */
public class BoxNumber extends JPanel{

	private LinkedList<Digit> boxNumber;
	private int numberOfDigits;
	private int number;

	/**
	 * Constructeur
	 */
	public BoxNumber(){
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.setLayout(null);
		this.setSize(78,32);
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(78,32));
	}

	/**
	 * Permet d'afficher un nombre 
	 * @param x Nombre à afficher
	 */
	public void setNumber(int x){
		this.removeAll();
		this.updateUI();

		this.number = x;
		this.boxNumber = new LinkedList<Digit>();
		// Détermine le nombre de chiffres nécessaire pour afficher le nombre
		this.numberOfDigits = (int)(Math.floor(Math.log10(number))+1);

		String strNumber = Integer.toString(number);

		for(int i=0; i<strNumber.length(); i++){
			int digit = Integer.parseInt(Character.toString(strNumber.charAt(i)));
			this.boxNumber.add(new Digit(digit));
		}

		if(boxNumber != null){
			for(int i=0; i<boxNumber.size(); i++){
				this.boxNumber.get(i).setLocation(i*20,0);
				this.add(boxNumber.get(i));
			}
		}
	}
}






