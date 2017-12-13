import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Class InformationsPanel
 * @version 1.0
 *
 * Contient les informations de la partie
 */
public class InformationsPanel extends JPanel{

	private CreationPanel creationPanel;
	private FlottePanel flottePanel;
	private OptionsPanel optionsPanel;
	private ScorePanel scorePanel;
	
	/**
	 * Constructeur
	 */
	public InformationsPanel(){
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

		this.creationPanel = new CreationPanel();
		this.flottePanel = new FlottePanel();
		this.optionsPanel = new OptionsPanel();
		this.scorePanel = new ScorePanel();
		
	}

	/**
	 * Change le type de panel (Création, Flotte , Scores)
	 * @param type Type de panel
	 */
	public void setTypePanel(TypeInfoPanel type){
		this.removeAll();
		this.updateUI();

		switch(type){
			case CREATION_PANEL:
				this.add(creationPanel);
			break;
			case FLOTTE_PANEL:
				this.add(flottePanel);
			break;
			case SCORE_PANEL:
				this.add(scorePanel);
			break;
			case OPTIONS_PANEL:
				this.add(optionsPanel);
			break;
		}
		this.revalidate();
	}

	/**
	 * Retourne CreationPanel creationPanel
	 * @return CreationPanel
	 */
	public CreationPanel getCreationPanel(){
		return creationPanel;
	}

	/**
	 * Retourne FlottePanel flottePanel
	 * @return FlottePanel
	 */
	public FlottePanel getFlottePanel(){
		return flottePanel;
	}

	/**
	 * Retourne OptionsPanel optionsPanel
	 * @return OptionsPanel
	 */
	public OptionsPanel getOptionsPanel(){
		return optionsPanel;
	}

	/**
	 * Retourne ScorePanel scorePanel
	 * @return ScorePanel
	 */
	public ScorePanel getScorePanel(){
		return scorePanel;
	}
}