import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class CaseCross
 * @version 1.0
 *
 * Case croix rouge
 */
public class CaseCross extends Case{

	private static final Image caseImage = setImage();

	/**
	 * Constructeur
	 */
	public CaseCross(){
	}

	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
			return ImageIO.read(CaseCross.class.getResource("resources/cross.png"));	
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
		super.paintComponent(g);
		g.drawImage(this.caseImage, 0,0, this.getWidth(), this.getHeight(), this);
	}
}