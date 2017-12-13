import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends JPanel{

	private static Plateau plateau;
	private static SideBarre sideBarre;

	private static Protocole protocole;
	private static Joueur joueurHote;
	private static boolean createPartie;

	/**
	 * Constructeur
	 */
	public Game(){
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		// Paramètres Fenetre 
		this.setPreferredSize(new Dimension(860, 575));
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		plateau = new Plateau();
		sideBarre = new SideBarre();

		// Prêt
		sideBarre.getButtonPret().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				Sounds.eventSoundButton();

				joueurHote.setListeShips(plateau.getGrilleGUI().getListeShips());

				lockShips(true);

				//Si la case jouer solo n'est pas cochée
				if(!sideBarre.getInformationsPanel().getCreationPanel().getJouerSolo()){

					if(createPartie){
						// On passe la side barre en mode Recherche d'un joueur
						sideBarre.setEvent(Event.WAIT_PLAYER);
						System.out.println("@Debug : new Protocole(HOTE)");
						protocole = new Protocole(TokenProtocole.HOTE);
						System.out.println("@Debug : protocole.start()");
						protocole.start();
					}
					else{
						System.out.println("@Debug : new Protocole(CLIENT)");
						protocole = new Protocole(TokenProtocole.CLIENT);
						System.out.println("@Debug : protocole.start()");
						protocole.start();
					}
				}else{
					Game.getSideBarre().setEvent(Event.GAME);
					ActionsPanel.setEnabledButtonTirer(true);
					GrilleGUI.setListeShipsPositionsAdverse(Game.getJoueurHote().getListeShipsPositions());
				}

			}
		});

		// Tirer
		sideBarre.getButtonTirer().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){		
				Sounds.eventSoundShot();

				setImpact();

				int[] coordTir = new int[2];
				coordTir[0] = plateau.getGrilleGUI().getPosTargetX();
				coordTir[1] = plateau.getGrilleGUI().getPosTargetY();

				//Si la case jouer solo n'est pas cochée
				if(!sideBarre.getInformationsPanel().getCreationPanel().getJouerSolo()){
					ActionsPanel.setEnabledButtonTirer(false);
					protocole.sendTabCoord(coordTir);

				}else{
					Game.getJoueurHote().setImpact(coordTir[0],coordTir[1]);
					int restants = joueurHote.getNbShipRestant();

					if(restants <= 0){
						sideBarre.getInformationsPanel().getScorePanel().setVictory(true);
						sideBarre.setEvent(Event.SCORE);

						int nbTirs = joueurHote.getNbTirs();
						int nbRates = joueurHote.getNbRates();
						sideBarre.getActionsPanel().setScores(restants,nbTirs,nbRates);
					}
				}

				update();
				Sounds.eventSoundReload();
			}
		});

		// Abandon
		sideBarre.getButtonAbandon().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				Sounds.eventSoundButton();
				sideBarre.getActionsPanel().setAbandonConfirm(true);
			}
		});

		// Bouton NON abandon
		sideBarre.getActionsPanel().getButtonNon().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
				Sounds.eventSoundButton();
				sideBarre.getActionsPanel().setAbandonConfirm(false);
			}
		});
	}

	/**
	 * Met à jour 
	 */
	public void update(){
		removeAll();
		updateUI();

		plateau.update();
		updateGhostShipView();

		add(plateau);
		add(sideBarre);

		revalidate();
	}

	/**
	 * Lance un nouveau jeu, reset des éléments
	 */
	public void newGame(){
		resetGame();
		update();
	}

	public static void resetGame(){
		protocole = null;

		plateau.reset();
		sideBarre.reset();

		if(!createPartie)
			sideBarre.setEvent(Event.JOIN);
			
		lockShips(false);

		joueurHote = new Joueur();
	}

	/**
	 * Met à jour la vue des ghost navires de la SideBarre en fonction des navires du joueurHote
	 */
	public static	void updateGhostShipView(){
		LinkedList<ShipGUI> listeJoueurHote = joueurHote.getListeShips();

		if(listeJoueurHote != null){

			for(int i=0; i<listeJoueurHote.size(); i++){
				int[][] tabPoints = listeJoueurHote.get(i).getTabPoints();

				for(int j=0; j<tabPoints.length; j++){

					if(tabPoints[j][2] == 1)
						sideBarre.setImpactToFlottePanel(i,j);
				}
			} 
		}
	}

	/**
	 * Appel de la méthode plateau.grilleGUI.setImpact();
	 */
	public void setImpact(){
		int x = plateau.getGrilleGUI().getPosTargetX();
		int y = plateau.getGrilleGUI().getPosTargetY();

		boolean shipTouched = plateau.getGrilleGUI().setImpact();
		joueurHote.incrementTirs();

		if(!shipTouched)
			joueurHote.incrementRates();

	}

	/**
	 * Active ou désactive la modification des navires et la fonction target.
	 * @param bool Verouille ou déverouille les navires
	 */
	public static void lockShips(boolean bool){
		if(bool){
			plateau.getGrilleGUI().setEnabledTarget(true);
			plateau.getGrilleGUI().setEnabledPlaceShips(false);
		}
		else{
			plateau.getGrilleGUI().setEnabledTarget(false);
			plateau.getGrilleGUI().setEnabledPlaceShips(true);
		}
		plateau.getGrilleGUI().hideShips(bool);
	}

	/**
	 * Active ou désactive le mode création d'une partie
	 * @param bool Active ou désactive
	 */
	public static void setCreatePartie(boolean bool){
		createPartie = bool;
	}

	/**
	 * Retourne la SideBarre
	 * @return SideBarre
	 */
	public static SideBarre getSideBarre(){
		return sideBarre;
	}

	/**
	 * Retourne le protocole
	 * @return Protocole
	 */
	public static Protocole getProtocole(){
		return protocole;
	}

	/**
	 * Retourne le joueurhote
	 * @return Joueur
	 */
	public static Joueur getJoueurHote(){
		return joueurHote;
	}
}