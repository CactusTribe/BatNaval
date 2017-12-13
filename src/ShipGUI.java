import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class ShipGUI
 * @version 1.0
 *
 * Permet l'affichage du navire
 */
class ShipGUI extends JPanel implements MouseInputListener{

	private Image shipImage;

	private int lng;
	private int posX, posY, posCursorX, posCursorY;
	private int[][] tabPoints;

	private boolean errorPlace;
	private boolean enabledDeplacement;
	private Alignement alignement;

	/**
	 * Constructeur
	 * @param x Position x de l'origine
	 * @param y Position y de l'origine
	 * @param lng Longueur du navire
	 */
	public ShipGUI(int x, int y, int lng){
		this.lng = lng;
		this.posX = x*50;
		this.posY = y*50;
		this.alignement = Alignement.HORIZONTAL;
		this.tabPoints = new int[this.lng][3];

		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.errorPlace = false;
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setBounds(posX,posY,50*lng,50);
		this.setShipImage(lng);
		this.setOpaque(false);
		this.enabledDeplacement = true;

		this.calculPointsCarre();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Calcul tout les points du navire en fonction du point d'origine et de l'alignement
	 * sur une grille carre
	 */
	public void calculPointsCarre(){
		/* -1 en x,y ce qui permet une utilisation avec les coordonées affichées (1-10) lors
		de l'appel de la fonction puis utiliser (0-9) avec l'algorithme.
		*/
		int countPartShip = 0;

		switch(alignement){
			case HORIZONTAL: 
				countPartShip = 0; // Compteur des parties du navire (Toutes les cases qu'il occupe).

				for(int i=(posX/50); i<((posX/50)+lng); i++){ // Boucles de l'origine [X à X+longueur[.

					tabPoints[countPartShip][0] = i; // Affectation de tout les X pour les n cases du navire.
					tabPoints[countPartShip][1] = posY/50; // Affectation du Y constant en Horizontal.
					tabPoints[countPartShip][2] = 0; // Initialisation à false de la colonne "touché"
					countPartShip ++;
				}
			break;
			case VERTICAL:
				countPartShip = 0;

				for(int i=(posY/50); i<((posY/50)+lng); i++){

					tabPoints[countPartShip][0] = posX/50;
					tabPoints[countPartShip][1] = i;
					tabPoints[countPartShip][2] = 0;
					countPartShip ++;
				}
			break; 
		}	

		//Verification du placement
		verifPlacement();

		//printTabPoints(); DEBUG
	}

	/**
	 * Vérifie si le placement est disponible sur la grille
	 * Si non, le navire est mis en mode erreur
	 */
	public void verifPlacement(){
		if(!GrilleGUI.setShipOccupation(tabPoints, lng)){
			this.setErrorPlacement(true);
		}
		else{
			this.setErrorPlacement(false);
		}
		GrilleGUI.checkErrorShips();
	}

	/**
	 * Répercute l'impacte d'un tir x,y
	 * @param x Position x du tir
	 * @param y Position y du tir
	 */
	public boolean setImpact(int x, int y){

		for(int i=0; i<tabPoints.length; i++){
			if(tabPoints[i][0] == x && tabPoints[i][1] == y){
				tabPoints[i][2] = 1;
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet d'afficher le tableau des coordonnées pour débug
	 */		
	public void printTabPoints(){
		for(int i=0; i<lng; i++){
			for(int j=0; j<3; j++){
				System.out.print(tabPoints[i][j] + " | ");
			}
			System.out.println();
		}
	}

	/**
	 * Test si le navire est touché sur tout ses points
	 * @return true si le navire est détruit, false sinon
	 */		
	public boolean isDestroyed(){
		for(int i=0; i<lng; i++){
			if(tabPoints[i][2] == 0)
				return false;
		}
		return true;
	}

	/**
	 * Calcul le deplacement maxi, pour évité que le navire dépasse de la zone
	 */		
	public void calculDeplacementMaxi(){
		if(alignement == Alignement.HORIZONTAL){
			if((posX+(lng*50)) > 500)
				posX = (500-(lng*50));
			else if(posX < 0)
				posX = 0;

			if(posY > 450)
				posY = 450;
			else if(posY < 0)
				posY = 0;

			posX = (posX/50)*50;
			posY = (posY/50)*50;
		}
		else if(alignement == Alignement.VERTICAL){
			if((posX+50) > 500)
				posX = (500-50);
			else if(posX < 0)
				posX = 0;

			if(posY+(lng*50) > 500)
				posY = (500-(lng*50));
			else if(posY < 0)
				posY = 0;

			posX = (posX/50)*50;
			posY = (posY/50)*50;
		}
	}

	/**
	 * Permet de passer le navire en mode erreur (Il devient rouge)
	 * @param bool Active ou désactive le mode erreur
	 */
	public void setErrorPlacement(boolean bool){
		this.errorPlace = bool;
	}

	/**
	 * Permet d'activer ou non le déplacement du navire
	 * @param bool Active ou désactive le déplacement
	 */
	public void setEnabledDeplacement(boolean bool){
		this.enabledDeplacement = bool;
	}

	/**
	 * Retourne l'attribut ErrorPlace
	 * @return true si le navire est en erreur, false sinon
	 */	
	public boolean getErrorPlace(){
		return this.errorPlace;
	}

	/**
	 * Retourne le tableau des coordonnées
	 * @return le int[][] des coordonnées des points du navire
	 */	
	public int[][] getTabPoints(){
		return this.tabPoints;
	}

	/**
	 * Retourne la position x de l'origine
	 * @return la position x de l'origine
	 */	
	public int getPosX(){
		return this.posX;
	}

	/**
	 * Retourne la position y de l'origine
	 * @return la position y de l'origine
	 */	
	public int getPosY(){
		return this.posY;
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
	protected void setShipImage(int lng){
		try{	
			switch(lng){
				case 2:
					if(alignement == Alignement.HORIZONTAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/2C.png"));	
					else if(alignement == Alignement.VERTICAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/2C_V.png"));
				break;
				case 3:
					if(alignement == Alignement.HORIZONTAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/3C.png"));	
					else if(alignement == Alignement.VERTICAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/3C_V.png"));				
					break;
				case 4:
					if(alignement == Alignement.HORIZONTAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/4C.png"));	
					else if(alignement == Alignement.VERTICAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/4C_V.png"));				
					break;
				case 5:
					if(alignement == Alignement.HORIZONTAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/5C.png"));	
					else if(alignement == Alignement.VERTICAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/5C_V.png"));				
					break;
				case 6:
					if(alignement == Alignement.HORIZONTAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/6C.png"));	
					else if(alignement == Alignement.VERTICAL)
						shipImage = ImageIO.read(ShipGUI.class.getResource("resources/ships/6C_V.png"));				
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

		g2d.drawImage(this.shipImage, 0,0, this.getWidth(), this.getHeight(), this);


    if(errorPlace){
	    g2d.setRenderingHint(
	        RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setComposite(AlphaComposite.getInstance(
	        AlphaComposite.SRC_OVER, 0.3f));
	    g2d.setColor(Color.RED);
	    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
 		}
	}

	@Override
	public void mouseClicked(MouseEvent e){
		Sounds.eventSoundButton2();

		if(alignement == Alignement.HORIZONTAL && posY < 450){
			alignement = Alignement.VERTICAL;
			this.setBounds(posX,posY,50,50*lng);
		}
		else if(alignement == Alignement.VERTICAL && posX < 450){
			alignement = Alignement.HORIZONTAL;
			this.setBounds(posX, posY,50*lng,50);
		}

		calculPointsCarre();

		this.setShipImage(lng);
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e){
	}

	@Override
	public void mouseExited(MouseEvent e){
		calculDeplacementMaxi();

		this.setLocation(posX,posY);
		calculPointsCarre();
	}

	@Override
	public void mousePressed(MouseEvent e){
		if(enabledDeplacement){
			posCursorX = e.getX();
			posCursorY = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e){
	}

	@Override
	public void mouseDragged(MouseEvent e){
		if(enabledDeplacement){
			posX = (posX+e.getX())-posCursorX;
			posY = (posY+e.getY())-posCursorY;

			calculDeplacementMaxi();

			this.setLocation(posX,posY);
			calculPointsCarre();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e){
		
	}
}