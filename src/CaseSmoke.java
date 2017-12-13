import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class CaseSmoke
 * @version 1.0
 *
 * Case de fumée
 */
public class CaseSmoke extends Case{

	private static final Image caseImage = setImage();

	/**
	 * Constructeur
	 */
	public CaseSmoke(){
		super();
		setBackground(Color.BLACK);
	}

	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
			return ImageIO.read(CaseSmoke.class.getResource("resources/smoke.png"));	
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
		g.drawImage(this.caseImage, 0,0, this.getWidth(), this.getHeight(), this);
		super.paintComponent(g);
	}
}