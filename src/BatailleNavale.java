import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

/**
 * Class BatailleNavale
 * @version 1.0
 *
 * Class principale, lance le jeux et contient les ActionListener des boutons du menu
 */
public class BatailleNavale extends JFrame{

	private Menu menu;
	private Game game;

	private Image imageCursor;
	private Cursor cursor;

	/**
	 * Constructeur
	 */
	public BatailleNavale(){
		init();
		initCursor();
		Sounds.eventMusicIntro();
	}

	/**
	 * Initialise le curseur
	 */
	private void initCursor(){
		//Get the default toolkit  
		Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		try{
			//Load an image for the cursor  
			imageCursor = ImageIO.read(BatailleNavale.class.getResource("resources/cursor.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		//Create the hotspot for the cursor  
		Point hotSpotCur = new Point(0,0);  
		cursor = toolkit.createCustomCursor(imageCursor, hotSpotCur, "Cursor");
		this.setCursor(cursor);
	}

	/**
	 * Initialisation
	 */
	private void init(){
		// Paramètres Fenetre 
		this.setTitle("Bataille Navale v1.1");
		this.setSize(800,575);
		this.setMinimumSize(new Dimension(800, 575));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		menu = new Menu();
		game = new Game();

		this.add(menu);
		this.pack();


		/****************************
		 *    ACTIONS LISTENERS     *
		 ****************************/

		/* ------ MENU ------ */

		// NouvellePartie
		menu.getButtonNouvellePartie().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	
								
				Sounds.eventSoundShipDoor();
				game.setCreatePartie(true);
				game.newGame();
				addJPanelGame();
			}
		});

		// RejoindrePartie
		menu.getButtonRejoindrePartie().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	

				Sounds.eventSoundShipDoor();
				game.setCreatePartie(false);
				game.newGame();
				addJPanelGame();
			}
		});

		// Options
		menu.getButtonOptions().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	

				Sounds.eventSoundShipDoor();
				addJPanelOptions();
			}
		});

		// Quitter
		menu.getButtonQuitter().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){		

				Sounds.eventSoundShipDoor();
				System.exit(0);
			}
		});

		// Bouton OUI abandon
		game.getSideBarre().getActionsPanel().getButtonOui().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	

				Sounds.eventSoundShipDoor();
				if(Game.getProtocole() != null)
					Game.getProtocole().closeConnexions();
				Game.resetGame();
				addJPanelMenu();
			}
		});

		// Menu
		game.getSideBarre().getButtonMenu().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	

				Sounds.eventSoundShipDoor();			
				Game.resetGame();
				addJPanelMenu();
			}
		});
	}

	/**
	 * Affiche le JPanel du menu
	 */
	public void addJPanelMenu(){
		this.getContentPane().removeAll();
		this.add(menu);
		this.revalidate();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Affiche le JPanel Options
	 */
	public void addJPanelOptions(){
		this.getContentPane().removeAll();

		this.setSize(300,575);
		this.setMinimumSize(new Dimension(300, 575));

		SideBarre sideBarre = new SideBarre();
		sideBarre.setEvent(Event.OPTIONS);

		// Menu
		sideBarre.getButtonMenu().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){	

				Sounds.eventSoundShipDoor();
				addJPanelMenu();
			}
		});

		this.add(sideBarre);
		this.revalidate();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/**
	 * Affiche le JPanel du jeu
	 */
	public void addJPanelGame(){
		this.getContentPane().removeAll();
		this.add(game);
		this.revalidate();
		this.pack();
		//this.setLocationRelativeTo(null);
	}

	/**
	 * Fonction main 
	 * @param args String[]
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new BatailleNavale().setVisible(true);
			}
		});
	}
}