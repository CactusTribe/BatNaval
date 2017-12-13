import java.util.LinkedList;

/**
 * Class Joueur
 * @version 1.0
 * 
 * - listeShips LinkedList : Contient tout les navires de type ShipGUI.
 */
public class Joueur{
	
	private LinkedList<ShipGUI> listeShips;
	private int nbTirs;
	private int nbRates;

	/**
	 * Constructeur
	 */
	public Joueur(){
		init();
	}

	/**
	 * Initialisation des attributs.
	 */
	private void init(){
	}

	/**
	 * Répercute l'impacte du tir sur la grille
	 * @param x Position x de l'impacte
	 * @param y Position y de l'impacte
	 */
	public void setImpact(int x, int y){
		for(int i=0; i<listeShips.size(); i++){
			boolean touched = listeShips.get(i).setImpact(x,y);

			if(touched){
				if(!Game.getSideBarre().getInformationsPanel().getCreationPanel().getJouerSolo())
					Sounds.eventSoundExplosion();
				Sounds.eventSoundWood1();
				break;
			}
		}
	}

	/**
	 * Incrémente nbTirs
	 */
	public void incrementTirs(){
		this.nbTirs++;
	}	

	/**
	 * Incrémente nbRates
	 */
	public void incrementRates(){
		this.nbRates++;
	}	

	/**
	 * Retourne nbTirs
	 * @return le nombre de tir effectués
	 */
	public int getNbTirs(){
		return this.nbTirs;
	}	

	/**
	 * Retourne nbRates
	 * @return le nombre de ratés 
	 */
	public int getNbRates(){
		return this.nbRates;
	}	

	/**
	 * Retourne le nombre de navires restants dans la flotte
	 * @return le nombre de navires restant
	 */
	public int getNbShipRestant(){
		int reste = 0;
		for(int i=0; i<listeShips.size(); i++){
			if(!listeShips.get(i).isDestroyed())
				reste++;
		}
		return reste;
	}

	/*******************************
	 *     GETTEURS / SETTERS      *
	 *******************************/

	/**
	 * Insère la liste des navires
	 * @param liste Liste des navires
	 */
	public void setListeShips(LinkedList<ShipGUI> liste){
		this.listeShips = liste;
	}

	/**
	 * Retourne la liste des navires
	 * @return la liste des navires LinkedList
	 */
	public LinkedList<ShipGUI> getListeShips(){
		return this.listeShips;
	}

	/**
	 * Retourne la liste des positions des navires
	 * @return la liste des positions LinkedList
	 */
	public LinkedList<int[][]> getListeShipsPositions(){
		LinkedList<int[][]> liste = new LinkedList<int[][]>();

		for(int i=0; i<listeShips.size(); i++){
			liste.add(listeShips.get(i).getTabPoints());
		}

		return liste;
	}
}