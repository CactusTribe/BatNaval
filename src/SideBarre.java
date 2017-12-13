import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class SideBarre
 * @version 1.0
 */
public class SideBarre extends JPanel{

	private static final Image fond = setImage();

	private InformationsPanel infoPanel;
	private ActionsPanel actionsPanel;
	
	/**
	 * Constructeur
	 */
	public SideBarre(){
		init();
		update();
	}

	/**
	 * Initialisation
	 */
	private void init(){
		this.setPreferredSize(new Dimension(300, 575));
		this.setLayout(null);
		this.infoPanel = new InformationsPanel();
		this.actionsPanel = new ActionsPanel();

		infoPanel.setLocation(0,0);
		actionsPanel.setLocation(0,375);
		
		this.setEvent(Event.CREATE);
		this.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, Color.BLACK));
	}

	/**
	 * Met à jour l'aspect graphique
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		this.add(infoPanel);
		this.add(actionsPanel);
		this.revalidate();
	}

	/**
	 * Reset la SideBarre
	 */
	public void reset(){
		infoPanel.setTypePanel(TypeInfoPanel.CREATION_PANEL);
		infoPanel.getCreationPanel().setCreatePartie(true);
		infoPanel.getFlottePanel().reset();
		setEvent(Event.CREATE);
		actionsPanel.setAbandonConfirm(false);
		update();
	}

	/**
	 * Séléction de l'event
	 * @param e Type d'event
	 */
	public void setEvent(Event e){
		switch(e){
			case CREATE:
				infoPanel.setTypePanel(TypeInfoPanel.CREATION_PANEL);
				actionsPanel.setTypePanel(TypeActionsBox.PRET_ABANDON);
				actionsPanel.setAbandonConfirm(false);
				update();
			break;
			case JOIN:
				infoPanel.getCreationPanel().setCreatePartie(false);
				infoPanel.setTypePanel(TypeInfoPanel.CREATION_PANEL);
				actionsPanel.setTypePanel(TypeActionsBox.PRET_ABANDON);
				actionsPanel.setAbandonConfirm(false);
				update();	
			break;
			case WAIT_PLAYER:
				infoPanel.setTypePanel(TypeInfoPanel.FLOTTE_PANEL);
				actionsPanel.setTypePanel(TypeActionsBox.WAIT_ABANDON);
				actionsPanel.setAbandonConfirm(false);
				update();		
			break;
			case GAME:
				infoPanel.setTypePanel(TypeInfoPanel.FLOTTE_PANEL);
				actionsPanel.setEnabledButtonTirer(false);
				actionsPanel.setTypePanel(TypeActionsBox.TIRER_ABANDON);
				actionsPanel.setAbandonConfirm(false);
				update();
			break;
			case OPTIONS:
				infoPanel.setTypePanel(TypeInfoPanel.OPTIONS_PANEL);
				actionsPanel.setTypePanel(TypeActionsBox.MENU);
				update();
			break;
			case SCORE:
				infoPanel.setTypePanel(TypeInfoPanel.SCORE_PANEL);
				actionsPanel.setTypePanel(TypeActionsBox.MENU_SCORES);
				Sounds.eventSoundScore();
				update();
			break;
		}
	}

	/**
	 * Ajoute une croix rouge sur le navire correspondant dans le panel Flotte
	 * @param indexShip Position du navire dans la liste
	 * @param indexCase Position de la case du navire
	 */
	public void setImpactToFlottePanel(int indexShip, int indexCase){
		this.infoPanel.getFlottePanel().setCrossToShip(indexShip, indexCase);
	}

	/**
	 * Retourne le JButton buttonPret
	 * @return le JButton pret
	 */
	public JButton getButtonPret(){
		return actionsPanel.getButtonPret();
	}

	/**
	 * Retourne le JButton buttonTirer
	 * @return le JButton tirer
	 */
	public JButton getButtonTirer(){
		return actionsPanel.getButtonTirer();
	}

	/**
	 * Retourne le JButton buttonAbandon
	 * @return le JButton abandon
	 */
	public JButton getButtonAbandon(){
		return actionsPanel.getButtonAbandon();
	}

	/**
	 * Retourne le JButton buttonMenu
	 * @return le JButton menu
	 */
	public JButton getButtonMenu(){
		return actionsPanel.getButtonMenu();
	}

	/**
	 * Retourne le InformationsPanel infoPanel
	 * @return InformationsPanel
	 */
	public InformationsPanel getInformationsPanel(){
		return infoPanel;
	}

	/**
	 * Retourne le ActionsPanel actionsPanel
	 * @return ActionsPanel
	 */
	public ActionsPanel getActionsPanel(){
		return actionsPanel;
	}

	/**
	 * Affectation de l'image
	 * @return Image
	 */
	static protected Image setImage(){
		try{	
			return ImageIO.read(SideBarre.class.getResource("resources/drapesBlack.png"));	
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
		g.drawImage(this.fond, 0,0, this.getWidth(), this.getHeight(), this);
	}
}