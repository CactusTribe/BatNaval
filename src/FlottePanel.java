import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class FlottePanel
 * @version 1.0
 *
 * Contient la représentation des navires dans la SideBarre
 */
public class FlottePanel extends JPanel{

	private JLabel myFlotte;
	private JLabel line1;
	private JLabel line2;
	private ImageIcon img_myFlotte; 
	private ImageIcon img_line1; 
	private ImageIcon img_line2; 
	private LinkedList<GhostShipGUI> listeGhostShips; // Contient les JPanels GhostShipGUI

	/**
	 * Constructeur
	 */
	public FlottePanel(){
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
		listeGhostShips = new LinkedList<GhostShipGUI>();

		img_myFlotte = new ImageIcon(getClass().getResource("resources/myFlotte.png"));
		myFlotte = new JLabel(img_myFlotte);
		myFlotte.setBounds(0,0, 300, 60);

		img_line1 = new ImageIcon(getClass().getResource("resources/line1.png"));
		line1 = new JLabel(img_line1);
		line1.setBounds(0,50, 300, 40);

		img_line2 = new ImageIcon(getClass().getResource("resources/line2.png"));
		line2 = new JLabel(img_line2);
		line2.setBounds(0,335, 300, 40);

		this.add(myFlotte);
		this.add(line1);

		initListeGhostShips();
		placementGhostShips();

		this.add(line2);
	}

	/**
	 * Met à jour l'aspect graphique
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		this.add(myFlotte);
		this.add(line1);

		initListeGhostShips();
		placementGhostShips();

		this.add(line2);
	}

	/**
	 * Reset la liste des navires fantômes
	 */
	public void reset(){
		listeGhostShips = new LinkedList<GhostShipGUI>();
		update();
	}

	/**
	 * Initialise la liste des navires fantômes
	 */
	public void initListeGhostShips(){
		for(int i=0; i<5; i++){
			listeGhostShips.add(new GhostShipGUI(i+2));
		}
	}

	/**
	 * Placement des navires fantômes
	 */
	public void placementGhostShips(){
		int posY = 100;
		for(int i=0; i<5; i++){
			listeGhostShips.get((4-i)).setLocation(15,posY);
			this.add(listeGhostShips.get(i));
			posY += 45;
		}
	}

	/**
	 * Place une croix sur la case "indexCase" du navire "indexShip" de la liste 
	 * @param indexShip Position du navire dans la liste
	 * @param indexCase Position de la case du navire
	 */
	public void setCrossToShip(int indexShip, int indexCase){
		listeGhostShips.get(indexShip).setCross(indexCase);
	}
}