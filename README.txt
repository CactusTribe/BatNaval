#################################################################
#
# Projet : Bataille Navale 2 
# Auteurs : Laiter Léonard, Lefranc Joaquim, Nabil Zerzeri
#
# Version : 0.1
#
#################################################################

----- Classes Main -----

	@ Point de départ du programme 

		* Avec interface : BatNav/BatNav.jar

----- Changes Log -----

	-> 10/04/15 - v1.1 :
	----------------------
		- Jeu fonctionnel en local, bug du .jar corrigé
		- Optimisation du Client.java
		- Jeu possible en solo contre ses propre navire (puzzle game)
		- Ajout de sons : victoire/défaite, enabled Bouton Tirer  

	-> 08/04/15 - v0.9.6 :
	----------------------
		- Déroulement du jeu fonctionnel, transmission du joueur et des différentes actions
		- Implémentation du protocole.
		- Implémentation des fonctions getData(), sendData(), readObject()
		- Organisation des cycles d’init, de connexion et déconnexion
		- Gestion de l’affichage de debug dans le terminal
		- Implémentation de randFIRST() qui détermine aléatoirement le premier joueur

	-> 30/03/15 - v0.9.1 :
	----------------------
		- Implémentation des erreurs de placement.
		- Intégration d'une musique de fond et de nouveaux sons.
		- Rajout d'un panel options permetant de gérer le volume audio. 
		- Première intégration de la gestion Serveur/Client, 
                  possibilité de connecter deux ordinateurs en réseau local.


----- Mémo des développeurs -----

-> PC Joaquim <-
_________________________________________

Organisation du répertoire :
_________________________________________

	+ [Projet] Bataille Navale
	 + [GUI] Dev
		~/.git
		~/.gitignore
	   + bin 
		> *.class
		+ resources
	         + ships
		   > *.{jpg, png}
	   + src
	      > *.java

	 + [NoGUI] Dev
		~/.git
		~/.gitignore
	   + bin
		> *.class
	   + src 
		> *.java
		> README.txt
	 + Bataille_Navale2 (Dépôt SVN)
		~/.svn
	   	> *.{java, tex, txt, jpg}
	 + Documentation

_________________________________________

Commandes terminal :
_________________________________________

	@ Java
	
		* Compilation -> $ javac src/*.java -d bin/

		* Changement de dir -> $ cd bin

		* Execution -> $ java MainClass

		* Archivage -> $ jar cvmf META-INF/MANIFEST.MF BatNav_x.x.x.jar -C bin .

	@ SVN

		* Copie dans le dépôt svn -> $ cp src/*.java ../Bataille_Navale2/

		* Upload du dossier -> $ svn commit -m "Message"

		* Ajout de fichiers svn -> $ svn add monfichier.*

	@ GIT

		* Status -> $ git status

		* Ajout de fichiers -> $ git add .

		* Ajout de fichiers non ignoré -> $ git add -A 

		* Liste des commits -> $ git log

		* Revenir en arrière -> $ git checkout SHA

		* Faire un commit -> $ git commit -am "Message"


-> PC Léo <-
_________________________________________

Organisation du répertoire :
_________________________________________

	+ [Documents] Bataille Navale
	 + [NoGUI] Dev
	 	~/.git
	 	~/.gitignore
	 	+bin
	 	 > *.class
	 	+src
	 	 > *.java
	 	 > README.txt

	 + [BatNavSVN] Bataille_Navale2
	 	~/.svn
	 	> *.{java,txt,tex,jpg}

	 + [Apprentissage] (Nouveaux principes à tester commes les énumérations)
	 	> *.{java,class}


-> PC Nabil <-
_________________________________________

Organisation du répertoire :
_________________________________________