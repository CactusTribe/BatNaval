import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Class Client
 * @version 1.0
 *
 * Permet l'instanciation d'un objet Client
 */
public class Client{	

	protected Socket socket;
	protected int port;

	protected boolean serveurConnected = false;

	/**
	 * Constructeur
	 * @param addr L'adresse du serveur
	 * @param port Port du serveur
	 */
	public Client(String addr, int port) {
		this.port = port;
		this.serveurConnected = false;

		try{
			InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(addr), port);

			System.out.println("@Debug : new Socket() at " + InetAddress.getByName(addr).getHostAddress() + ":" + port);
			socket = new Socket();
			socket.connect(isa,1000);

			System.out.println("* Le Client est connecté au serveur "+ socket.getRemoteSocketAddress().toString());
			serveurConnected = true;	

		}catch (IOException e) {	
			System.out.println(" -> Connexion échouée. (IOException)");
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

				Object obj = ois.readObject();
				return obj;

			}else{
				return null;
			}

		} catch(IOException e){
			this.serveurConnected = false;
			return null;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fonction qui envoie un object au serveur
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
	 * Fonction qui renvoi l'état du client
	 * @return true si le client est connecté, false sinon
	 */
	public boolean isConnectedToServeur(){
		return this.serveurConnected;
	}

	/**
	 * Fonction qui ferme le socket
	 */
	public void close(){
		try {
			this.serveurConnected = false;

			if(socket != null)
				socket.close();	

			System.out.println(" -> Fermeture du socket client.");
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}