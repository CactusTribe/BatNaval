import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class GhostShipGUI
 * @version 1.0
 *
 * Permet l'affichage du navire fantome dans la sideBarre
 */
class GhostShipGUI extends JPanel{

	private Image shipImage;
	private int lng;
	private int tailleCase;
	private Case[] tabCasesGhostShip;

	/**
	 * Constructeur
	 * @param lng Longueur du navire
	 */
	public GhostShipGUI(int lng){
		this.lng = lng;
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.tailleCase = 45;
		this.tabCasesGhostShip = new Case[this.lng];
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setSize(tailleCase*lng, tailleCase);
		this.setPreferredSize(new Dimension(tailleCase*lng, tailleCase));
		this.setShipImage(lng);
		this.setOpaque(false);

		initTabCasesGhostShip();
		
	}

	/**
	 * Initialise les JPanels Cases permetant l'évolution graphique du navire fantôme
	 */
	private void initTabCasesGhostShip(){
		for(int i=0; i<tabCasesGhostShip.length; i++){
			tabCasesGhostShip[i] = new CaseEmpty();
			tabCasesGhostShip[i].setPreferredSize(new Dimension(tailleCase,tailleCase));
			this.add(tabCasesGhostShip[i]);
		}
	}

	/**
	 * Met à jour l'aspect graphique
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		for(int i=0; i<tabCasesGhostShip.length; i++){
			this.add(tabCasesGhostShip[i]);
		}
	}

	/**
	 * Affiche une croix rouge à la case i du navire
	 * @param indexCase Position de la case du navire
	 */
	public void setCross(int indexCase){
		if(indexCase < lng)
			tabCasesGhostShip[indexCase] = new CaseCross();
		tabCasesGhostShip[indexCase].setPreferredSize(new Dimension(tailleCase,tailleCase));
		update();
	}

	/**
	 * Affectation de l'image
	 * @param lng Longueur du navire
	 */
	protected void setShipImage(int lng){
		try{	
			switch(lng){
				case 2:
					shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/ghost2C.png"));	
				break;
				case 3:
					shipImage =  ImageIO.read(ShipGUI.class.getResource("resources/ships/ghost3C.png"));	
				break;
				case 4:
					shipImage =  ImageIO.read(ShipGUI.class.getResource("resources/ships/ghost4C.png"));	
				break;
				case 5:
					shipImage =  ImageIO.read(ShipGUI.class.getResource("resources/ships/ghost5C.png"));	
				break;
				case 6:
					shipImage =  ImageIO.read(ShipGUI.class.getResource("resources/ships/ghost6C.png"));	
				break;
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Repaint l'image
	 * @param g Graphics 
	 */
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(
	    RenderingHints.KEY_ANTIALIASING,
	    RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(this.shipImage, 0,0, this.getWidth(), this.getHeight(), null);

	}
}
