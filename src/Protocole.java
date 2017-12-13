import javax.swing.*;
import java.util.Random;
import java.util.*;

/**
 * Class Protocole
 * @version 1.0
 *
 * Gère toute les communications entre le serveur et le client
 * Initialise la connexion du serveur ou du client
 */
public class Protocole implements Runnable{

	protected Thread t;
	protected int delay;

	protected boolean suspended = false;
	protected boolean threadDone = false;

	private Serveur serveur;
	private Client client;

	private Runnable testDeconnexion;
	private Runnable testConnexion;
	private Runnable resetGame;
	private Runnable setScores;
	private Runnable updateView;

	private TokenProtocole typeConnection;
	private JTextField[] infosTextFields;

	/**
	 * Constructeur
	 * @param token TokenProtocole permetant de choisir le mode HOTE ou CLIENT
	 */
	public Protocole(TokenProtocole token){
		System.out.println("@Debug : Instanciation du Protocole");

		this.delay = 1000;
		this.typeConnection = token;

		testConnexion = new Runnable(){
			@Override
			public void run(){
				if(typeConnection == TokenProtocole.HOTE){
					// Si un joueur est connecté 
					if(serveur.isConnectedToClient()){
						Game.getSideBarre().setEvent(Event.GAME);
						Game.lockShips(true);
						System.out.println("@Debug : sendListeShipsPositions()");
						sendListeShipsPositions(Game.getJoueurHote().getListeShipsPositions());
						resume();
						randFIRST();
					}
					else{
						finish();						
						Game.getSideBarre().setEvent(Event.CREATE);
						SwingUtilities.invokeLater(resetGame);
					}
				}

				if(typeConnection == TokenProtocole.CLIENT){
					// Si la connexion au serveur est effective
					if(client.isConnectedToServeur()){
						Game.getSideBarre().setEvent(Event.GAME);
						Game.lockShips(true);
						System.out.println("@Debug : sendListeShipsPositions()");
						sendListeShipsPositions(Game.getJoueurHote().getListeShipsPositions());
						resume();
					}
					else{
						finish();
						Game.getSideBarre().setEvent(Event.JOIN);
						SwingUtilities.invokeLater(resetGame);
					}
				}
			}
		}; 

		
		testDeconnexion = new Runnable(){
			@Override
			public void run(){
				if(serveur != null){
					// Si le joueur n'est plus connecté
					if(!serveur.isConnectedToClient()){
						finish(); // On coupe le thread
						Game.getSideBarre().setEvent(Event.CREATE);
						SwingUtilities.invokeLater(resetGame);
					}
				}
				if(client != null){
					// Si la connexion au serveur n'est plus effective
					if(!client.isConnectedToServeur()){
						finish(); // On coupe le thread
						Game.getSideBarre().setEvent(Event.JOIN);
						SwingUtilities.invokeLater(resetGame);
					}
				}
			}
		}; 

		setScores = new Runnable(){
			@Override 
			public void run(){
				Game.getSideBarre().setEvent(Event.SCORE);
				int restants = Game.getJoueurHote().getNbShipRestant();
				int nbTirs = Game.getJoueurHote().getNbTirs();
				int nbRates = Game.getJoueurHote().getNbRates();
				Game.getSideBarre().getActionsPanel().setScores(restants,nbTirs,nbRates);
				finish();
			}
		};

		resetGame = new Runnable(){
			@Override 
			public void run(){
				Game.resetGame();
			}
		};

		updateView = new Runnable(){
			@Override 
			public void run(){
				Game.updateGhostShipView();
			}
		};
	}

	/**
	 * Initialisation
	 */
	private void init(){
		System.out.println("@Debug : init()");

		infosTextFields = Game.getSideBarre().getInformationsPanel().getCreationPanel().getTabJTextField();
		switch(typeConnection){
			case HOTE:
				connexionToClient();
			break;
			case CLIENT:
				connexionToServeur();
			break;
		}

		suspend();
		System.out.println("@Debug : testConnexion()");
		SwingUtilities.invokeLater(testConnexion);
	}

	/**
	 * Permet de choisir au hasard le premier joueur
	 */
	public void randFIRST() {
    Random rand = new Random();
    int randomNum1 = rand.nextInt((10 - 1) + 1) + 1;
    int randomNum2 = rand.nextInt((10 - 1) + 1) + 1;
    System.out.println(randomNum1 +" - "+ randomNum2);

    if(randomNum1 < randomNum2){
    	sendToken(TokenProtocole.FIRST);
    }
    else{
    	sendToken(TokenProtocole.SECOND);
    	ActionsPanel.setEnabledButtonTirer(true);
    }
	}

	/**
	 * Boucle de communication entre client et serveur
	 */
	public void run(){
		System.out.println("@Debug : run()");
		init();

		try{

			while(!threadDone){
				synchronized(this){
					while(suspended){
						wait();
					}
					System.out.println("@Debug : testDeconnexion()");
					SwingUtilities.invokeLater(testDeconnexion);

					if(typeConnection == TokenProtocole.HOTE){
						ecouteServeur();
					}
					if(typeConnection == TokenProtocole.CLIENT){
						ecouteClient();
					}
					Thread.sleep(delay);
				}
			}		

		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	/**
	 * Initialise le serveur et la connexion au client
	 */
	public void connexionToClient(){
		System.out.println("@Debug : new Serveur()");
		serveur = new Serveur(Integer.parseInt(infosTextFields[1].getText()), 60*1000);

		System.out.println("@Debug : searchClient()");
		serveur.searchClient(); 
	}

	/**
	 * Initialise le client et la connexion au serveur
	 */
	public void connexionToServeur(){	
		System.out.println("@Debug : new Client()");
		client = new Client(infosTextFields[0].getText(),Integer.parseInt(infosTextFields[1].getText()));
	}

	/**
	 * Ecoute les informations en provenance du client
	 */
	public void ecouteServeur(){
		Object obj = serveur.getData();
		readObject(obj);
	}

	/**
	 * Ecoute les informations en provenance du serveur
	 */
	public void ecouteClient(){
		Object obj = client.getData();
		readObject(obj);
	}

	/**
	 * Lecture de l'objet et détermine l'action en fonction du type
	 * @param obj Object à lire
	 */
	public void readObject(Object obj){
		if(obj != null){

			// Si c'est un int[] alors ce sont les coordonnées d'un tir
			if(obj instanceof int[]){

				int[] tab = (int[])obj;
				Game.getJoueurHote().setImpact(tab[0],tab[1]);
				SwingUtilities.invokeLater(updateView);
				ActionsPanel.setEnabledButtonTirer(true);
				Sounds.eventSoundWood2();
				System.out.println(" -> ["+tab[0]+";"+tab[1]+"]");

				int restants = Game.getJoueurHote().getNbShipRestant();
				if(restants <= 0){
					sendToken(TokenProtocole.VICTORY);
					Game.getSideBarre().getInformationsPanel().getScorePanel().setVictory(false);
					SwingUtilities.invokeLater(setScores);
				}
			}		

			// Si c'est une LinkedList<?> alors ce sont les coordonnées des navires adverses
			else if(obj instanceof LinkedList<?>){

				LinkedList<int[][]> liste = (LinkedList<int[][]>)obj;
				GrilleGUI.setListeShipsPositionsAdverse(liste);
				System.out.println(" -> Navires adverses ajoutés dans la Grille.");
			}

			// Si c'est un TokenProtocole alors ce sont des ordres spécifiques, on test quel type d'ordre
			else if(obj instanceof TokenProtocole){

				TokenProtocole token = (TokenProtocole)obj;

				if(obj == TokenProtocole.VICTORY){

					System.out.println(" -> VICTOIRE !");
					Game.getSideBarre().getInformationsPanel().getScorePanel().setVictory(true);
					SwingUtilities.invokeLater(setScores);
				}
				else if(obj == TokenProtocole.FIRST){

					System.out.println(" -> FIRST");
					ActionsPanel.setEnabledButtonTirer(true);
					Sounds.eventSoundWood2();
				}
				else if(obj == TokenProtocole.SECOND){

					System.out.println(" -> SECOND");
					Sounds.eventSoundWood2();
				}
			}

			// Si c'est un String alors on affiche le message
			else if(obj instanceof String){
				String msg = (String)obj;
				System.out.println(msg);
			}

		}
		else{
			System.out.println();
		}
	}

	/**
	 * Permet d'envoyer un String
	 * @param str Message à envoyer
	 */
	public void sendString(String str){
		if(serveur != null){
			serveur.sendData(str);
		}
		if(client != null){
			client.sendData(str);
		}
	}

	/**
	 * Permet d'envoyer un Token
	 * @param token TokenProtocole à envoyer
	 */
	public void sendToken(TokenProtocole token){
		if(serveur != null){
			serveur.sendData(token);
		}
		if(client != null){
			client.sendData(token);
		}	
	}

	/**
	 * Permet d'envoyer un int[]
	 * @param pos Tableau de coordonnées du tir à envoyer
	 */
	public void sendTabCoord(int[] pos){
		if(serveur != null){
			serveur.sendData(pos);
		}
		if(client != null){
			client.sendData(pos);
		}
	}

	/**
	 * Permet d'envoyer une LinkedList
	 * @param posShips Liste des tableaux de positions des navires
	 */
	public void sendListeShipsPositions(LinkedList<int[][]> posShips){
		if(serveur != null){
			serveur.sendData(posShips);
		}
		if(client != null){
			client.sendData(posShips);
		}
	}

	/**
	 * Permet de fermer les sockets du serveur ou du client
	 */
	public void closeConnexions(){
		if(serveur != null){
			serveur.close();
		}

		if(client != null){
			client.close();
		}
	}

	/**
	 * Démarre le thread
	 */
	public void start(){
		if(t == null){
			t = new Thread(this);
			t.start();
		}
	}

	/**
	 * Met en pause le thread
	 */	
	public void suspend(){
	  suspended = true;
	}

	/**
	 * Reprend le thread
	 */
	synchronized void resume(){
	  suspended = false;
	  notify();
	}

	/**
	 * Termine le thread
	 */
	public void finish(){
		threadDone = true;
		closeConnexions();
	}
}