import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Class Serveur
 *
 * Objet Serveur
 */
public class Serveur{
	
	protected ServerSocket socketserver;
	protected Socket socket;
	protected int port;

	protected boolean clientConnected = false;

	/**
	 * Constructeur
	 * @param port Port de connexion du serveur
	 * @param timeout Temps d'essai maximal pour la connexion d'un client
	 */
	public Serveur(int port, int timeout){
		this.port = port;
		this.clientConnected = false;

		try{
			socketserver = new ServerSocket(this.port);
			socketserver.setSoTimeout(timeout); 
			
		}catch (IOException e) {	
			System.out.println(" -> Lancement du serveur impossible.");
			this.clientConnected = false;
		}
	}

	/**
	 * Attente d'une connexion
	 */
	public void searchClient(){
		try{

			System.out.println("* Le serveur est à l'écoute du port "+socketserver.getLocalPort());
			socket = socketserver.accept();
			this.clientConnected = true;	
			System.out.println(" -> Un joueur s'est connecté");

		}catch (IOException e){
			System.out.println(" -> Erreur de connexion.");
		}
	}

  /**
	 * Methode qui recoit un object et le retourne
	 * @return l'objet ou null
	 */
	public Object getData(){
		try{

			if(socket != null){
				InputStream is = socket.getInputStream();  
				ObjectInputStream ois = new ObjectInputStream(is);  

				Object obj = (Object)ois.readObject();
				return obj;

			}else{
				return null;
			}

		} catch(IOException e){
			this.clientConnected = false;
			return null;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fonction qui envoie un object au client
	 * @param obj Objet à envoyer
	 */
	public void sendData(Object obj){
		try{

			OutputStream os = socket.getOutputStream();  
			ObjectOutputStream oos = new ObjectOutputStream(os);  
			oos.writeObject(obj);

		}catch(Exception e){
			System.out.println("Erreur lors de l'envoi des données. (Exception)");
		}
  }

	/**
	 * Fonction qui renvoi l'état du serveur
	 * @return true si le serveur est connecté, false sinon
	 */
	public boolean isConnectedToClient(){
		return this.clientConnected;
	}

	/**
	 * Fonction qui ferme le socket
	 */
	public void close(){
		try {
			this.clientConnected = false;

			if(socket != null)
				socket.close();
			if(socketserver != null)
				socketserver.close();	

			System.out.println(" -> Fermeture du socket serveur.");
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}