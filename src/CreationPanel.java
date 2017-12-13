import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;
/**
 * Class CreationPanel
 * @version 1.0
 *
 * Contient les champs de création de partie
 */
public class CreationPanel extends JPanel{

	private JLabel labelConfig;
	private JLabel labelRejoindre;
	private JLabel line1;
	private JLabel line2;
	private JLabel labelServeur;
	private JLabel labelPort;
	private JLabel labelConnexionLocale;
	private JLabel labelJouerSolo;

	private ImageIcon img_Config; 
	private ImageIcon img_Rejoindre; 
	private ImageIcon img_line1; 
	private ImageIcon img_line2; 
	private ImageIcon img_Serveur;
	private ImageIcon img_Port;
	private ImageIcon img_ConnexionLocale;
	private ImageIcon img_JouerSolo;

	private JTextField fieldHote;
	private JTextField fieldPort;
	private JCheckBox boxConnexionLocale;
	private JCheckBox boxJouerSolo;

	private String currentIP;
	private boolean connexionLocale;
	private boolean createPartie;

	/**
	 * Constructeur
	 */
	public CreationPanel(){
		this.connexionLocale = true;
		this.getCurrentIP(connexionLocale);
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
		this.createPartie = true;

		fieldHote = new JTextField(this.currentIP);
		fieldHote.setBackground(Color.BLACK);
		fieldHote.setForeground(Color.WHITE);
		fieldHote.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		fieldHote.setBounds(40,30,220,20);
		fieldHote.setCaretColor(Color.WHITE);

		fieldPort = new JTextField("5580");
		fieldPort.setBackground(Color.BLACK);
		fieldPort.setForeground(Color.WHITE);
		fieldPort.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		fieldPort.setBounds(40,30,220,20);
		fieldPort.setCaretColor(Color.WHITE);

		img_Config = new ImageIcon(getClass().getResource("resources/labelConfig.png"));
		labelConfig = new JLabel(img_Config);
		labelConfig.setBounds(0,0, 300, 60);

		img_Rejoindre = new ImageIcon(getClass().getResource("resources/labelRejoindre.png"));
		labelRejoindre = new JLabel(img_Rejoindre);
		labelRejoindre.setBounds(0,0, 300, 60);

		img_line1 = new ImageIcon(getClass().getResource("resources/line1.png"));
		line1 = new JLabel(img_line1);
		line1.setBounds(0,50, 300, 40);

		img_Serveur = new ImageIcon(getClass().getResource("resources/labelServeur.png"));
		labelServeur = new JLabel(img_Serveur);
		labelServeur.setBounds(0,90, 300, 60);

		img_Port = new ImageIcon(getClass().getResource("resources/labelPort.png"));
		labelPort = new JLabel(img_Port);
		labelPort.setBounds(0,150, 300, 60);

		img_ConnexionLocale = new ImageIcon(getClass().getResource("resources/labelConnexionLocale.png"));
		labelConnexionLocale = new JLabel(img_ConnexionLocale);
		labelConnexionLocale.setBounds(0,210, 300, 60);

		boxConnexionLocale = new JCheckBox();
		boxConnexionLocale.setOpaque(false);
		boxConnexionLocale.setBounds(235,227,30,30);

		img_JouerSolo = new ImageIcon(getClass().getResource("resources/labelJouerSolo.png"));
		labelJouerSolo = new JLabel(img_JouerSolo);
		labelJouerSolo.setBounds(0,270, 300, 60);

		boxJouerSolo = new JCheckBox();
		boxJouerSolo.setOpaque(false);
		boxJouerSolo.setBounds(235,287,30,30);
		boxJouerSolo.setSelected(false);

		img_line2 = new ImageIcon(getClass().getResource("resources/line2.png"));
		line2 = new JLabel(img_line2);
		line2.setBounds(0,335, 300, 40);

		boxConnexionLocale.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e){

				if(boxConnexionLocale.isSelected()){
					connexionLocale = true;
					getCurrentIP(connexionLocale);
					fieldHote.setText(currentIP);
					fieldPort.setText("5580");
				}
				else{
					connexionLocale = false;
					getCurrentIP(connexionLocale);
					fieldHote.setText(currentIP);		
				}
			}
		});

		update();
	}

	/**
	 * Met à jour le panel
	 */
	public void update(){
		this.removeAll();
		this.updateUI();

		if(createPartie){
			this.add(labelConfig);
			this.add(boxConnexionLocale);
			this.add(boxJouerSolo);
			this.add(labelConnexionLocale);
			this.add(labelJouerSolo);

			if(connexionLocale){
				boxConnexionLocale.setSelected(true);
			}else{
				boxConnexionLocale.setSelected(false);
			}

		}
		else{
			this.add(labelRejoindre);
		}
		this.add(line1);

		labelServeur.add(fieldHote);
		labelPort.add(fieldPort);

		this.add(labelServeur);
		this.add(labelPort);

		this.add(line2);
		boxJouerSolo.setSelected(false);
	}

	/**
	 * Met à jour l'ip courante 
	 * @param local Choix d'ip local ou externe
	 */
	public void getCurrentIP(boolean local){
		if(local){
			// IP RESEAU LOCAL
			try {

				InetAddress serveurAdresse = InetAddress.getLocalHost();
				this.currentIP = serveurAdresse.getHostAddress();

			}catch (UnknownHostException e){
				e.printStackTrace();
			}
		}
		else{
			// IP RESEAU EXTERNE
			try{

				URL whatismyip = new URL("http://checkip.amazonaws.com");
				BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				this.currentIP = in.readLine();
				in.close();

			}catch (Exception e){
				System.out.println(e);
			}
		}
	}

	/**
	 * Permet de passer le panel en mode création de partie ou bien rejoindre une partie
	 * @param b true pour passer le panel en mode création de partie
	 */
	public void setCreatePartie(boolean b){
		this.createPartie = b;
		update();
	}

	/**
	 * Retourne l'état du panel
	 * @return true si il est en mode création
	 */
	public boolean getCreatePartie(){
		return this.createPartie;
	}

	/**
	 * Retourne l'état de la checkbox solo
	 * @return true si elle est checked
	 */
	public boolean getJouerSolo(){
		return boxJouerSolo.isSelected();
	}

	/**
	 * Retourne un tableau de JTextField contenant l'adresse et le port de connexion
	 * @return le tableau de JTextField
	 */
	public JTextField[] getTabJTextField(){
		JTextField[] tab = new JTextField[2];
			tab[0] = fieldHote;
			tab[1] = fieldPort;
		return tab;
	}
}