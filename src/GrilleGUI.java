import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.*;
import javax.sound.sampled.*;

/**
 * Class GrilleGUI
 * @version 1.0
 *
 * Gère toute la grille
 */
public class GrilleGUI extends JPanel implements MouseInputListener{

	private static final Image fond = setImage();
	private static int[][] grilleOccupation = initGrilleOccupation();

	private Case[][] tabCases; // Contient les JPanels Cases
	private static LinkedList<ShipGUI> listeShips; // Contient les JPanels ShipGUI
	private static LinkedList<int[][]> listeShipsPositionsAdverse;
	private int posTargetX, posTargetY; // Position du curseur de visée
	private boolean enabledTarget;
	private boolean hideShips;

	/**
	 * Constructeur
	 */
	public GrilleGUI(){
		init();
		update();
	}

	/**
	 * Initialisation des attributs
	 */
	public void init(){
		this.posTargetX = -1;
		this.posTargetY = -1;
		this.tabCases = new Case[10][10];
		this.enabledTarget = true;
		this.hideShips = false;
		listeShips = new LinkedList<ShipGUI>();
		grilleOccupation = initGrilleOccupation();

		this.setLayout(null);
		this.setBounds(30,40,500,500);
		this.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.BLACK));

		this.initTabCases();
		this.initListeShips();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	/**
	 * Initialisation de la grille d'occupation
	 * @return la grilleOccupation initialisée 
	 */
	public static int[][] initGrilleOccupation(){
		int[][] grilleOccupation = new int[10][10];
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				grilleOccupation[i][j] = 0;
			}
		}
		return grilleOccupation;
	}

	/**
	 * Permet de passer les points d'un navire en mode occupé
	 * @param tabPoints Tableau int[][] contenant toutes les coordonnées du navire
	 * @param id Correspond à l'id du navire (sa taille)
	 * @return Retourne true si la position est valide, false sinon
	 */
	public static boolean setShipOccupation(int[][] tabPoints, int id){
		//Nettoyage des traces de drag
		GrilleGUI.clearShipOccupationByID(id); 
		//Verification
		for(int i=0; i<tabPoints.length; i++){
			if(GrilleGUI.pointIsOccuped(tabPoints[i][0], tabPoints[i][1], id))
				return false;
		}
		for(int i=0; i<tabPoints.length; i++){
			if(tabPoints[i][0] < 10 && tabPoints[i][1] < 10)
				grilleOccupation[tabPoints[i][0]][tabPoints[i][1]] = id;
		}
		//GrilleGUI.printGrilleOccupation();
		return true;
	}

	/**
	 * Permet de supprimer l'emplacement d'un navire via son ID
	 * @param id Correspond à l'id du navire (sa taille)
	 */
	public static void clearShipOccupationByID(int id){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				if(grilleOccupation[i][j] == id)
					grilleOccupation[i][j] = 0;
			}
		}
	} 

	/**
	 * Permet de savoir si un point de la grille est déjà occupé
	 * @param x Position x
	 * @param y Position y
	 * @param id Correspond à l'id du navire (sa taille)
	 * @return Retourne true si il est occupé, false sinon ou bien si le point est hors de la grille
	 */
	public static boolean pointIsOccuped(int x, int y,int id){
		if(x < 10 && y <10)
			return (grilleOccupation[x][y] != id && grilleOccupation[x][y] != 0);
		else
			return false;
	}

	/**
	 * Permet de vérifier si des navires sont en mode Erreur
	 * Le bouton Prêt est activé ou non en fonction du résultat
	 */
	public static void checkErrorShips(){
		boolean errorShip = false;
		for(int i=0; i<listeShips.size(); i++){
			if(listeShips.get(i).getErrorPlace())
				errorShip = true;
		}

		if(errorShip)
			ActionsPanel.setEnabledButtonPret(false);
		else
			ActionsPanel.setEnabledButtonPret(true);
	}

	/**
	 * Affiche la grille d'occupation dans le terminal pour débug
	 */
	public static void printGrilleOccupation(){
		System.out.println("==========================================================");
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				System.out.print(" "+ grilleOccupation[j][i] + " |");
			}
			System.out.println();
		}
		System.out.println("==========================================================");
	}

	/**
	 * Met à jour l'affichage de la grille
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		if(!hideShips)
			this.printShips();

		this.printGrille();
		this.repaint();
	}

	/**
	 * Initialise des cases de la grille
	 */
	public void initTabCases(){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				tabCases[j][i] = new CaseEmpty();
				tabCases[j][i].setBounds(50*j,50*i, 50,50);
				tabCases[j][i].setBorder(true);
			}
		}
	}

	/**
	 * Initialise la liste des navires
	 */
	public void initListeShips(){
		for(int i=0; i<5; i++){
			listeShips.add(new ShipGUI(0,i,i+2));
		}
	}

	/**
	 * Ajoute les cases au Layout pour les afficher
	 */
	public void printGrille(){
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				this.add(tabCases[j][i]);
			}
		}
	}

	/**
	 * Ajoute les navires au Layout pour les afficher
	 */
	public void printShips(){
		for(int i=0; i<listeShips.size(); i++){
			this.add(listeShips.get(i));
		}
	}

	/**
	 * Active ou désactive la visée sur la grille.
	 * @param bool Active ou désactive
	 */
	public void setEnabledTarget(boolean bool){
		this.enabledTarget = bool;
	}

	/**
	 * Active ou désactive le déplacement des navires.
	 * @param bool Active ou désactive
	 */
	public void setEnabledPlaceShips(boolean bool){
		for(int i=0; i<listeShips.size(); i++){
			listeShips.get(i).setEnabledDeplacement(bool);
		}
	}

	/**
	 * Un nouvel objet de type CaseSmoke est crée à la case x,y de la grille
	 * @return true si un navire est touché, false sinon
	 */
	public boolean setImpact(){
		int x = posTargetX;
		int y = posTargetY;

		boolean isTouched = false;

		for(int i=0; i<listeShipsPositionsAdverse.size(); i++){
			//On récupère le tableau contenant toutes les coordonées du navire
			int[][] tabCoord = listeShipsPositionsAdverse.get(i); 

			for(int j=0; j<tabCoord.length; j++){
				if(tabCoord[j][0] == x && tabCoord[j][1] == y){
					tabCases[x][y] = new CaseExplosion();
					tabCases[x][y].setBounds(50*x,50*y, 50,50);
					tabCases[x][y].setBorder(true);
					isTouched = true;

					Sounds.eventSoundExplosion();
					return true;
				}
			}
			if(isTouched)
				break;
		}

		if(!isTouched){
				if(posTargetX != -1 && posTargetY != -1){
				tabCases[x][y] = new CaseSmoke();
				tabCases[x][y].setBounds(50*x,50*y, 50,50);
				tabCases[x][y].setBorder(true);

				Sounds.eventSoundShotWater();
			}
		}
		update();
		return false;
	}

	/**
	 * Appel de la méthode setTargeted(true) de la case aux coordonnées posTargetX, posTargetY
	 */
	public void setTarget(){
		tabCases[posTargetX][posTargetY].setTargeted(true);
	}

	/**
	 * Appel de la méthode setTargeted(false) de la case aux coordonnées posTargetX, posTargetY
	 */
	public void unsetTarget(){
		tabCases[posTargetX][posTargetY].setTargeted(false);
	}

	/**
	 * Active ou désactive l'affichage des navires
	 * @param bool Active ou désactive
	 */
	public void hideShips(boolean bool){
		this.hideShips = bool;
		update();
	}

	/**
	 * Retourne la position X du curseur de visée
	 * @return la positon X de la target
	 */
	public int getPosTargetX(){
		return this.posTargetX;
	}

	/**
	 * Retourne la position Y du curseur de visée
	 * @return la position Y de la target
	 */
	public int getPosTargetY(){
		return this.posTargetY;
	}

	/**
	 * Insère la liste des navires
	 * @param liste Insère la liste des navires dans la grille
	 */
	public static void setListeShips(LinkedList<ShipGUI> liste){
		listeShips = liste;
	}

	/**
	 * Insère la liste des positions des navires adverse
	 * @param liste Insère la liste des positions des navires adverses dans la grille
	 */
	public static void setListeShipsPositionsAdverse(LinkedList<int[][]> liste){
		listeShipsPositionsAdverse = liste;
	}

	/**
	 * Retourne la liste des navires
	 * @return la liste des navires LinkedList
	 */
	public LinkedList<ShipGUI> getListeShips(){
		return this.listeShips;
	}
	
	/********************************
	 *                              *
	 *    GESTION GUI ET EVENTS     *
	 *                              *
	 ********************************/

	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
				return ImageIO.read(GrilleGUI.class.getResource("resources/sea2.png"));	
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
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(this.fond, 0,0, this.getWidth(), this.getHeight(), this);
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if(enabledTarget){
			if(posTargetX != -1 && posTargetY != -1)
				unsetTarget();

			posTargetX = e.getX()/50;
			posTargetY = e.getY()/50;

			setTarget();
			update();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e){
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e){
	}	

	@Override
	public void mouseReleased(MouseEvent e){
	}

	@Override
	public void mouseDragged(MouseEvent e){
	}

	@Override
	public void mouseMoved(MouseEvent e){
		this.repaint();
	}
}