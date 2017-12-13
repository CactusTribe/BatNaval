import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class ActionsPanel
 * @version 1.0
 *
 * Contient les boutons d'actions de la SideBarre
 */
public class ActionsPanel extends JPanel{

	private ImageIcon tirerNormal;
	private ImageIcon tirerHover;
	private ImageIcon tirerPush;

	private ImageIcon pretNormal;
	private ImageIcon pretHover;
	private ImageIcon pretPush;

	private ImageIcon abandonNormal;
	private ImageIcon abandonHover;
	private ImageIcon abandonPush;

	private ImageIcon menuNormal;
	private ImageIcon menuHover;
	private ImageIcon menuPush;

	private ImageIcon ouiNormal;
	private ImageIcon ouiHover;
	private ImageIcon ouiPush;

	private ImageIcon nonNormal;
	private ImageIcon nonHover;
	private ImageIcon nonPush;

	private static JButton buttonPret = new JButton();
	private static JButton buttonTirer = new JButton();
	private JButton buttonAbandon;
	private JButton buttonMenu;
	private JButton buttonOui;
	private JButton buttonNon;

	private JLabel waitPlayer;
	private JLabel scores;
	private JLabel abandonConfirm;
	
	private ImageIcon img_waitPlayer; 
	private ImageIcon img_scores; 
	private ImageIcon img_abandonConfirm; 

	private boolean confirm;
	private TypeActionsBox typePanel;

	private BoxNumber scoreRestants;
	private BoxNumber scoreNbTirs;
	private BoxNumber scoreNbRates;

	/**
	 * Constructeur
	 */
	public ActionsPanel(){
		init();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.setLayout(null);
		this.setSize(300,200);
		this.setPreferredSize(new Dimension(300,200));
		this.setOpaque(false);
		this.confirm = false;

		img_waitPlayer = new ImageIcon(getClass().getResource("resources/waitplayer.png"));
		waitPlayer = new JLabel(img_waitPlayer);
		waitPlayer.setBounds(0,0, 300, 100);

		img_scores = new ImageIcon(getClass().getResource("resources/stats.png"));
		scores = new JLabel(img_scores);
		scores.setBounds(0,0, 300, 100);

		img_abandonConfirm = new ImageIcon(getClass().getResource("resources/abandonConfirm.png"));
		abandonConfirm = new JLabel(img_abandonConfirm);
		abandonConfirm.setBounds(0,95, 300, 100);

		pretNormal = new ImageIcon(getClass().getResource("resources/boutons/pretNormal.png"));
		pretHover = new ImageIcon(getClass().getResource("resources/boutons/pretHover.png"));
		pretPush = new ImageIcon(getClass().getResource("resources/boutons/pretPush.png"));

		tirerNormal = new ImageIcon(getClass().getResource("resources/boutons/tirerNormal.png"));
		tirerHover = new ImageIcon(getClass().getResource("resources/boutons/tirerHover.png"));
		tirerPush = new ImageIcon(getClass().getResource("resources/boutons/tirerPush.png"));

		abandonNormal = new ImageIcon(getClass().getResource("resources/boutons/abandonNormal.png"));
		abandonHover = new ImageIcon(getClass().getResource("resources/boutons/abandonHover.png"));
		abandonPush = new ImageIcon(getClass().getResource("resources/boutons/abandonPush.png"));

		menuNormal = new ImageIcon(getClass().getResource("resources/boutons/menuNormal.png"));
		menuHover = new ImageIcon(getClass().getResource("resources/boutons/menuHover.png"));
		menuPush = new ImageIcon(getClass().getResource("resources/boutons/menuPush.png"));

		ouiNormal = new ImageIcon(getClass().getResource("resources/boutons/ouiNormal.png"));
		ouiHover = new ImageIcon(getClass().getResource("resources/boutons/ouiHover.png"));
		ouiPush = new ImageIcon(getClass().getResource("resources/boutons/ouiPush.png"));

		nonNormal = new ImageIcon(getClass().getResource("resources/boutons/nonNormal.png"));
		nonHover = new ImageIcon(getClass().getResource("resources/boutons/nonHover.png"));
		nonPush = new ImageIcon(getClass().getResource("resources/boutons/nonPush.png"));

		buttonPret.setOpaque(false);
		buttonPret.setUI(new CustomizedButtonUI(pretNormal, pretHover, pretPush));
		buttonPret.setBounds(10,10, 280, 90);
		buttonPret.setContentAreaFilled(false);
		buttonPret.setBorderPainted(false);

		buttonTirer.setOpaque(false);
		buttonTirer.setUI(new CustomizedButtonUI(tirerNormal, tirerHover, tirerPush));
		buttonTirer.setBounds(10,10, 280, 90);
		buttonTirer.setContentAreaFilled(false);
		buttonTirer.setBorderPainted(false);

		buttonAbandon = new JButton();
		buttonAbandon.setOpaque(false);
		buttonAbandon.setUI(new CustomizedButtonUI(abandonNormal, abandonHover, abandonPush));
		buttonAbandon.setBounds(10,100, 280, 90);
		buttonAbandon.setContentAreaFilled(false);
		buttonAbandon.setBorderPainted(false);

		buttonMenu = new JButton();
		buttonMenu.setOpaque(false);
		buttonMenu.setUI(new CustomizedButtonUI(menuNormal, menuHover, menuPush));
		buttonMenu.setBounds(10,100, 280, 90);
		buttonMenu.setContentAreaFilled(false);
		buttonMenu.setBorderPainted(false);

		buttonOui = new JButton();
		buttonOui.setOpaque(false);
		buttonOui.setUI(new CustomizedButtonUI(ouiNormal, ouiHover, ouiPush));
		buttonOui.setBounds(50,150, 100, 34);
		buttonOui.setContentAreaFilled(false);
		buttonOui.setBorderPainted(false);

		buttonNon = new JButton();
		buttonNon.setOpaque(false);
		buttonNon.setUI(new CustomizedButtonUI(nonNormal, nonHover, nonPush));
		buttonNon.setBounds(150,150, 100, 34);
		buttonNon.setContentAreaFilled(false);
		buttonNon.setBorderPainted(false);

		scoreRestants = new BoxNumber();
		scoreNbTirs = new BoxNumber();
		scoreNbRates = new BoxNumber();

		scoreRestants.setLocation(215,0);
		scoreNbTirs.setLocation(215,35);
		scoreNbRates.setLocation(215,70);

		setEnabledButtonTirer(false);
	}

	/**
	 * Change le type de panel et donc les boutons d'actions associés 
	 * @param type TypeActionsBox
	 */
	public void setTypePanel(TypeActionsBox type){
		this.removeAll();
		this.updateUI();

		typePanel = type;

		switch(typePanel){
			case PRET_ABANDON:
				this.add(buttonPret);

				if(confirm)
					this.setEventConfirmAbandon();
				else
					this.add(buttonAbandon);
			break;
			case WAIT_ABANDON:
				this.add(waitPlayer);

				if(confirm)
					this.setEventConfirmAbandon();
				else
					this.add(buttonAbandon);
			break;
			case TIRER_ABANDON:
				this.add(buttonTirer);

				if(confirm)
					this.setEventConfirmAbandon();
				else
					this.add(buttonAbandon);
			break;
			case MENU:
				this.add(buttonMenu);
			break;
			case MENU_SCORES:
				this.add(scoreRestants);
				this.add(scoreNbTirs);
				this.add(scoreNbRates);
				this.add(scores);
				this.add(buttonMenu);
			break;
		}	
		this.revalidate();
	}

	/**
	 * Modifie le nombre de navire restants, nombre de tirs et nombre de ratés 
	 * @param restants int
	 * @param nbTirs int
	 * @param nbRates int
	 */
	public void setScores(int restants, int nbTirs, int nbRates){
		scoreRestants.setNumber(restants);
		scoreNbTirs.setNumber(nbTirs);
		scoreNbRates.setNumber(nbRates);
	}

	/**
	 * Event de confirmation d'abandon
	 */
	public void setEventConfirmAbandon(){
		this.add(abandonConfirm);
		this.add(buttonOui);
		this.add(buttonNon);
	}

	/**
	 * Permet de dire si on abandonne ou non
	 * @param bool Active ou désactive l'affichage de la confirmation d'abandon
	 */
	public void setAbandonConfirm(boolean bool){
		this.confirm = bool;
		setTypePanel(typePanel);
	}

	/**
	 * Permet de désactiver le bouton Prêt
	 * @param bool Active ou désactive le bouton pret
	 */
	public static void setEnabledButtonPret(boolean bool){
		buttonPret.setEnabled(bool);
	}

	/**
	 * Permet de désactiver le bouton Tirer
	 * @param bool Active ou désactive le bouton tirer
	 */
	public static void setEnabledButtonTirer(boolean bool){
		buttonTirer.setEnabled(bool);
	}

	/**
	 * Retourne le JButton buttonPret
	 * @return JButton
	 */
	public JButton getButtonPret(){
		return buttonPret;
	}

	/**
	 * Retourne le JButton buttonTirer
	 * @return JButton
	 */
	public JButton getButtonTirer(){
		return buttonTirer;
	}

	/**
	 * Retourne le JButton buttonAbandon
	 * @return JButton
	 */
	public JButton getButtonAbandon(){
		return buttonAbandon;
	}

	/**
	 * Retourne le JButton buttonMenu
	 * @return JButton
	 */
	public JButton getButtonMenu(){
		return buttonMenu;
	}

	/**
	 * Retourne le JButton buttonOui
	 * @return JButton
	 */
	public JButton getButtonOui(){
		return buttonOui;
	}

	/**
	 * Retourne le JButton buttonNon
	 * @return JButton
	 */
	public JButton getButtonNon(){
		return buttonNon;
	}
}