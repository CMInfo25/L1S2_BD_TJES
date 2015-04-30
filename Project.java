public class Project
{
    
    /* ======================================== */
    /*               STRUCTURE                  */
    /* ======================================== */
    
    
    static class Hebergement
    // Sert à manipuler des enregistrement de le table HEBERGEMENT
    {
	int id= -1;
	int type = 0;
	short capacite = -1;
	String ville = "error";
	short tarif = -1 ;
    }

    static class RechercheVersReserve
    // Sert à transferer les donnees necessaire depuis une recherche vers une reservation
    {
	int res = -1;
	long arrivee = -1;
	long depart = -1;
    }

    static class UtilisateurConnecte
    // Sert à identifier un utilisateur dans le code
    {
	int connexion = -1;
	String login = "error";
	boolean administrateur = false;
    }


    /* ======================================== */
    /*               VALIDATION                 */
    /* ======================================== */


    static public boolean verifieConnexion(int connexion)
    // Verifie la connexion et renvoie un message d'erreur avant FALSE.
    {
	if(connexion == -1)
	    {
		Ecran.afficherln(" Erreur, connexion non initialisee.");
		return false;
	    }
	return true;
    }


    /* ======================================== */
    /*               UTILITAIRE                 */
    /* ======================================== */
    static public int nouvelIDReservation(int connexion)
    // Retourne l'id directement suivant le plus grand de la table pour un nouvel enregistrement.
    {
	// VERIFICATION DE LA CONNEXION
	if(!verifieConnexion(connexion))return -1;
	
	// RECHERCHE DU PLUS GRAND ID
	int res = BD.executerSelect(connexion , " SELECT reID"
				    + " FROM RESERVATION "
				    + " WHERE reID >= ALL("
				    + " SELECT reID "
				    + " FROM RESERVATION " 
				    + ")"
				    );

	/* ENLEVE LE */
	Ecran.afficherln (" SELECT reID"
				    + " FROM RESERVATION "
				    + " WHERE reID >= ALL("
				    + " SELECT reID "
				    + " FROM RESERVATION " 
				    + ")"
			  );
	/* */

	// ON RETIENT LE DERNIER ID EXISTANT
	int dernierID = BD.attributInt(res, "reID");

	// LIBERATION
	BD.fermerResultat(res);
	
	// RESULTAT
	return dernierID + 1;
	
    }

    static public int nouvelIDHebergement(int connexion)
    // Retourne l'id directement suivant le plus grand de la table pour un nouvel enregistrement.
    {
	// VERIFICATION DE LA CONNEXION
	if(!verifieConnexion(connexion))return -1;
	
	// RECHERCHE DU PLUS GRAND ID
	int res = BD.executerSelect(connexion, " SELECT heID"
				    + " FROM HEBERGEMENT "
				    + " WHERE heID >= ALL("
				    + " SELECT heID "
				    + " FROM HEBERGEMENT " 
				    + ")"
				    );


	/* ENLEVE LE */
	Ecran.afficherln (" SELECT heID"
			  + " FROM HEBERGEMENT "
			  + " WHERE heID >= ALL("
			  + " SELECT heID "
			  + " FROM HEBERGEMENT " 
			  + ")"
			  );
	/* */



	// ON RETIENT LE DERNIER ID EXISTANT
	int dernierID = BD.attributInt(res, "heID");

	// LIBERATION
	BD.fermerResultat(res);
	
	// RESULTAT
	return dernierID + 1;
	
    }



    /* ======================================== */
    /*      FONCTION AFFICHAGE CONSOLE          */
    /* ======================================== */

    static public void afficherHebergement(int id, int enuma, int capacite, String ville, int tarif)
    // Afficher un hebergement depuis ses donnees
    {
	Ecran.afficherln(" Reservation ID : ", id);
	Ecran.afficher(" Type : ");
	switch(enuma)
	    {
	    case 0 : Ecran.afficher("gite"); break;
	    case 1 : Ecran.afficher("appartement"); break;
	    case 2 : Ecran.afficher("chambre"); break;
	    default : Ecran.afficher("Inconnu (probablement un endroit melant l'espace et le temps brisant leur continum...)"); break;
	    }
	Ecran.afficherln(" \n Capacite : ", capacite);
	Ecran.afficherln(" Ville de : " , ville);
	Ecran.afficherln(" Tarif : " , tarif ) ;
    }

    static public void afficherHebergement(Hebergement h)
    // Afficher un hebergement depuis la classe faite en amont.
    {
	afficherHebergement(h.id, h.type, h.capacite, h.ville, h.tarif);
    }

    static public void afficherHebergement(int res)
    // Affiche un hebergement depuis un resultat
    {
	afficherHebergement(
			     BD.attributInt(res, "heID"),
			     BD.attributInt(res, "heType"),
			     BD.attributInt(res, "heCapacite"),
			     BD.attributString(res, "heVille"),
			     BD.attributInt(res, "heTarif")
			     );
    }

    /* ======================================== */
    /*               FONCTION MENU              */
    /* ======================================== */


    static public void saisirNouveau(int connexion)
    // Permet l'enregistrement d'un nouvel hebergement 
    {
	// SECURITE
	if(!verifieConnexion(connexion))return;

	// PREPARATION DU NOUVEL ID
	Hebergement nouveau= new Hebergement();
	       
	// ID (automatique)
	nouveau.id = nouvelIDHebergement(connexion);
	       
	// TYPE
	Ecran.afficherln(" Veuillez saisir le type (0 - 2) de l'hebergement :");
	Ecran.afficher(" 0 - Gite \n 1 - Appartement \n 2 - Maison \n");
	nouveau.type = Clavier.saisirInt();
	       
	// CAPACITY
	Ecran.afficherln(" Veuillez saisir la capacite de l'hebergement");
	nouveau.capacite = Clavier.saisirShort();
	       
	// CITY
	Ecran.afficherln(" Veuillez saisir la ville de l'hebergement");
	nouveau.ville = Clavier.saisirString();
	       
	// COST
	Ecran.afficherln(" Veuillez saisir le tarifde l'hebergement");
	nouveau.tarif = Clavier.saisirShort();
	       
	// VALIDATION
	Ecran.afficherln("Resumons : ");
	afficherHebergement(nouveau);

	// CONFIRMATION
	Ecran.afficherln(" Tapez 'o' pour valider.");
	char reponse = 'X';
	reponse = Clavier.saisirChar();
	       
	if (reponse == 'o');
	// On souhaite ajouter à la table
	{


	/* ENLEVE LE */
	    Ecran.afficherln ("INSERT INTO HEBERGEMENT VALUES(" 
			      + "'" + Integer.toString(nouveau.id) + "'" + "," 
			      + "'" + Integer.toString(nouveau.type) + "'" + "," 
			      + "'" + Short.toString(nouveau.capacite) + "'" + "," 
			      + "'" + nouveau.ville + "'" 
			      + "," + nouveau.tarif 
			      + ")"
			      );
	/* */




	    if( 
	       BD.executerUpdate(connexion,
				 "INSERT INTO HEBERGEMENT VALUES(" 
				 + "'" + Integer.toString(nouveau.id) + "'" + "," 
				 + "'" + Integer.toString(nouveau.type) + "'" + "," 
				 + "'" + Short.toString(nouveau.capacite) + "'" + "," 
				 + "'" + nouveau.ville + "'" 
				 + "," + nouveau.tarif 
				 + ")")
	       == -1
		)
		// PROBLEME
		{
		    Ecran.afficherln(" Il y a eu un probleme lors de l'ajout. Veuillez ressayer. ");
		}
	    else
		// OK
		{
		    Ecran.afficherln(" Ajout fait, merci de votre confiance !");
		}
		   
	}
}
    
    static public RechercheVersReserve rechercher(int connexion)
    // Execute une recherche dans la table selon les criteres de l'utilisateur et renvoie le resultat de la recherche.
    {
	RechercheVersReserve results = new RechercheVersReserve();

	// SAISIE DE LA RESERVATION
	Ecran.afficherln(" ====== RESERVATION ===== ");
	Ecran.afficher(" Saisir la date d'arrivee que vous souhaitez ");

	// ANNEE
	Ecran.afficher("\n Annee : ");	
	int annee = Clavier.saisirInt();

	// MOIS
	Ecran.afficher("\n Mois : (chiffre) "); 
	int mois = Clavier.saisirInt();

	// JOUR
	Ecran.afficher("\n Jour : "); 
	int jour = Clavier.saisirInt();

	// HEURE
	Ecran.afficher("\n Heure d'arrivee "); 
	int heure = Clavier.saisirInt();

	// DUREE
	Ecran.afficher("\n Duree du sejour : (jours) "); 
	int dureeJ = Clavier.saisirInt();

	// TRANSFORMATION
	results.arrivee = BD.date(jour, mois, annee, heure, 0, 0);
	results.depart = BD.date(jour + dureeJ, mois, annee, heure, 0, 0);
	
	// VERIFICATION DE LA DISPONIBILITE
	String requete = " SELECT HEBERGEMENT.* "
	    + " FROM  HEBERGEMENT AS H "
	    + " WHERE NOT EXISTS ( "
	    + " SELECT * "
	    + " FROM RESERVATION "
	    + " WHERE reHebergement = H.heID "
	    + " AND reDateArrivee >= " + Long.toString(results.arrivee) 
	    + " AND reDateDepart  <= " + Long.toString(results.depart) 
	    + ")";

	/* ENLEVE LE */
	    Ecran.afficherln (requete);
	/* */



	results.res = BD.executerSelect(connexion, requete);

	// AFFICHAGE DES RESULTATS CORRESPONDANTS 
	Ecran.afficherln(" ====== DISPONIBILITES ======= ") ;
	
	int nResults = 0;
	while(BD.suivant(results.res))
	    {
		nResults ++;
		Ecran.afficherln(" ===== HEBERGEMENT N " + Integer.toString(nResults) + " =====");
		afficherHebergement(results.res);
	    }

	// RENVOIE
	BD.reinitialiser(results.res);
	return results;

    }

    static public void reserver(int connexion)
    // Recherche des hebergements (pour avoir l'ID de celui qu'on veut) et lance le processus de reservation.
    {
	/// SECURITE
	if(!verifieConnexion(connexion))return;

	// RECHERCHE DES HEBERGEMENTS CORRESPONDANTS AUX CRITERES
	RechercheVersReserve recherche;
	recherche = rechercher(connexion);

	// S'IL EXISTE DES RESULTATS
	if( recherche.res == -1)
	    {
		Ecran.afficherln(" Desole, votre requete ne correspond a aucune de nos disponibilites; veuillez vous assurer des donnees que vous avez saisies et recommencer la manoeuvre.");
	    }
	else
	    {
		// PROCESSUS DE RESERVATION LANCE, ON DEMANDE L'ID SOUHAITE
		Ecran.afficherln(" Veuillez saisir l'ID de l'hebergement que vous souhaitez.");
		int id = Clavier.saisirInt();

		// VERIFICATION DE RESERVATION POSSIBLE
		int dejaPris = BD.executerSelect(connexion,
						 "SELECT * " 
						 + " FROM RESERVATION as R " 
						 + " WHERE reID = " 
						 + Integer.toString(id) 
						 + " AND reDateArrivee >= " + Long.toString(recherche.arrivee) 
						 + " AND reDateArrivee <= " +  Long.toString(recherche.depart) + ")" 
						 );

	/* ENLEVE LE */
		Ecran.afficherln ("SELECT * " 
				  + " FROM RESERVATION as R " 
				  + " WHERE reID = " 
				  + Integer.toString(id) 
				  + " AND reDateArrivee >= " + Long.toString(recherche.arrivee) 
				  + " AND reDateArrivee <= " +  Long.toString(recherche.depart) + ")" );
	/* */


		if( dejaPris != -1 ) 
		    // RESERVATION IMPOSSIBLE CAR DEJA PRISE
		    {
			Ecran.afficherln (" C'est embarassant, il a du y avoir une erreur, cette reservation est deja prise. Nos excuses.");
		    }
		else
		    // RESERVATION POSSIBLE, ON ESSAYE DE L'ENREGISTRER
		    {


			/* ENLEVE LE */
			Ecran.afficherln ("INSERT INTO RESERVATION VALUES(" 
					  + "'" + Integer.toString(nouvelIDReservation(connexion)) + "'" 
					  + "," + "DIEU" + "," 
					  + Integer.toString(id) 
					  + "," + Long.toString(recherche.arrivee) + "," 
					  + Long.toString(recherche.depart) + ")"
					  );
		        /* */


			if(BD.executerUpdate(connexion,
					     "INSERT INTO RESERVATION VALUES(" 
					     + "'" + Integer.toString(nouvelIDReservation(connexion)) + "'" 
					     + "," + "DIEU" + "," 
					     + Integer.toString(id) 
					     + "," + Long.toString(recherche.arrivee) + "," 
					     + Long.toString(recherche.depart) + ")"
					     ) == -1 )
			    // ECHEC DE L'AJOUT
			    {
				Ecran.afficherln(" Il y a eu un probleme lors de l'ajout, veuillez reiterer. Mille excuses.");
			    }
			else
			    // SUCESS !
			    {
				Ecran.afficherln(" Votre reservation a bien ete enregistree ! Merci pour votre confiance !");
			    }
		    }

		// LIBERATION
		BD.fermerResultat(dejaPris);
		   
	    }

	// LIBERATION
	BD.fermerResultat(recherche.res);
    }

    static public int connect()
    {
	return 2; // ENLEVE LE ! 
	//	return BD.ouvrirConnexion("172.20.128.64", "silvert_BD", "silvert", "silvert");
    }

    
    /* ======================================== */
    /*               MAIN                       */
    /* ======================================== */
    static public boolean accueil(UtilisateurConnecte user)
    {
	Ecran.afficherln(" ===== ACCUEIL  ===== ");
	Ecran.afficherln(" 1 - Se connecter \n 2 - S'inscrire");
	
	user.connexion = connect();
	int choix = -1;
	do
	    {
		choix = Clavier.saisirInt();
		switch(choix)
		    {
		    case 1 : 
			if(!connexionUtilisateur(user))
			    {return false;}; 
			break;

		    case 2 : 
			if(!inscriptionUtilisateur(user))
			    {return false;}; 
			break;

		    default : 
			Ecran.afficherln("Nous n'avons pas compris votre choix. Recommencez."); 
			break;
		    }
		
	    }while(choix != 1 && choix != 2);    
	return true;
    }

    static public boolean connexionUtilisateur(UtilisateurConnecte user)
    {
	// SECURITE
	if(!verifieConnexion(user.connexion))return false;

	Ecran.afficherln(" ===== CONNEXION ===== ");
	
	// LOGIN
	Ecran.afficherln(" Veulliez saisir votre login ");
	user.login = Clavier.saisirString();
	

	// PASSWORD 
	Ecran.afficherln(" Veulliez saisir votre mot de passe ");
	String motdepasse = Clavier.saisirString();

	// Verification
        int res = BD.executerSelect(user.connexion, 
				 "SELECT * "
				 + " FROM UTILISATEUR "
				 + " WHERE utLogin = " + "'" + user.login + "'" 
				 + " AND utPassword = " +  "'" + motdepasse +  "'" 
				    );

	/* ENLEVE LE */
	Ecran.afficherln ("SELECT * "
			  + " FROM UTILISATEUR "
			  + " WHERE utLogin = " + "'" + user.login + "'" 
			  + " AND utPassword = " +  "'" + motdepasse +  "'" 
			  );
	/* */

	if(res != -1) 
	    {
		Ecran.afficherln(" Bienvenue à vous " + BD.attributString(res, "utPrenom") + " " + BD.attributString(res, "utNom"));
		user.administrateur = ( BD.attributInt(res, "utRole") == 0 );
		BD.fermerResultat(res);
		return true;
	    }

	Ecran.afficherln("Connexion echouee."); return false;
    }

    static public boolean inscriptionUtilisateur(UtilisateurConnecte user)
    {	
	// SECURITE
	if(!verifieConnexion(user.connexion))return false;

	Ecran.afficherln(" ===== INSCRIPTION ===== ");
	
	// LOGIN
	Ecran.afficherln(" Veulliez saisir votre login ");
	user.login = Clavier.saisirString();
	
	// Verification
        int res = BD.executerSelect(user.connexion, 
				    "SELECT * "
				    + " FROM UTILISATEUR "
				    + " WHERE utLogin = " +  "'" + user.login + "'" 
				    );

	/* ENLEVE LE */
	Ecran.afficherln ("SELECT * "
			  + " FROM UTILISATEUR "
			  + " WHERE utLogin = " +  "'" + user.login + "'" 
			  );
	/* */

	if(res != -1) 
	    // EXISTE DEJA
	    {
		Ecran.afficherln(" Cet utilisateur existe déjà, veuillez reiterer.");
		return false;
	    }
	else
	    // INSCRIPTION
	    {

		// PASSWORD 
		Ecran.afficherln(" Veulliez saisir votre mot de passe ");
		String motdepasse = Clavier.saisirString();

		// NOM
		Ecran.afficherln(" Veulliez saisir votre nom ");
		String nom = Clavier.saisirString();

		// PRENOM
		Ecran.afficherln(" Veulliez saisir votre prenom ");
		String prenom = Clavier.saisirString();

		// ADMINISTRATEUR
		Ecran.afficherln(" Pour etre considere comme administrateur, veuillez saisir le mot de pass ROOT, autrement appuyez sur entree.");
		boolean admin = (Clavier.saisirString() == "admin");
		String Sadmin = (admin == true) ? "0" : "1";
		/* ENLEVE LE */
		Ecran.afficherln (" INSERT INTO UTILISATEUR VALUES ("
				     +  "'" + user.login +  "'" + ","
				     +  "'" + motdepasse +  "'" + ","
				  + Sadmin + "," 
				     +  "'" + nom +  "'" + ","
				  +  "'" + prenom + "'"  
				  + ")"
				  );
		/* */


		if(BD.executerUpdate(user.connexion, 
				     " INSERT INTO UTILISATEUR VALUES ("
				     +  "'" + user.login +  "'" + ","
				     +  "'" + motdepasse +  "'" + ","
				     + "1" + "," 
				     +  "'" + nom +  "'" + ","
				     +  "'" + prenom + "'"  
				     + ")"
				     ) 
		   == -1)
		    {
			Ecran.afficherln(" Impossible de vous inscrire. Veuillez ressayer plus tard.");
			return false;
		    }
		else
		    {
			Ecran.afficherln("Bievenue " + prenom);
			return true;
		    }
	    }
    }

    static public void main( String Args[] )
    {
	// CONNEXION PAR UTILISATEUR
	Ecran.afficherln("Bienvenue sur *inserer jeu de mot ici*. \n");
	UtilisateurConnecte user = new UtilisateurConnecte();
	if(accueil(user) == false) return;
        
	// DEBUT
	if(verifieConnexion(user.connexion))
	    {
		int menu = -1;
		do
		    {
			Ecran.afficherln(" ===== Menu ===== ");
			Ecran.afficherln(" 1 - Ajouter son hebergement \n 2 - Rechercher un hebergement \n 3 - Réserver un hébergement (lance une recherche) \n 4 - Quitter ");
			menu = Clavier.saisirInt();  
			switch(menu)
			    {
			    case 1: // Ajout
				saisirNouveau(user.connexion);
				break;
                  
			    case 2: // Recherche
				BD.fermerResultat(rechercher(user.connexion).res);
				break;
                  
			    case 3: // Reservation
				reserver(user.connexion);
				break;

			    case 4 : /* On va sortir de la boucle */ break;
			    default:
				Ecran.afficherln("Mille excuses, mais vous avez du faire une erreur. Recommencez s'il vous plait.");
				break;
			    }
		    }while (menu != 4);

		Ecran.afficherln(" * inserer jeu de mot ici* ");
		BD.fermerConnexion(user.connexion);
	

	    }

    }
}

/* TUTORIAL 
   int maconnexion = BD.ouvrirConnexion(String adresse, String nom_bd, String login, String password);

   void BD.fermerConnexion(int connexion);

   int BD.executerSelect(int connexion, String sql); // Retourne le numéro de la réponse serveur
   int BD.executerUpdate(int connexion, String sql) // Retourne idem
   boolean BD.suivant(int res) // return true si ya bien un suivant
   boolean BD.reinitialiser(int res) // place le cruseur à -1
   void BD.fermerResultat(int res) // free()
   String BD.attributString(int res, String att)// Renvoie la valeur de att de l'enregistrement numero res sous forme caractère
   String BD.attributInt(int res, String att)// Renvoie la valeur de att de l'enregistrement numero res sous forme d'entier
   long BD.attributLong(int res, String att) // idem en long

   /// DATE
   long BD.maintenant() // ~ time(NULL)
   long BD.date(int jour, int mois, int annee, int heures, int minutes, int secondes) // obvious
        
   int BD.jour(long d)
   int BD.mois(long d)
   int BD.annee(long d)
   int BD.heures(long d)
   int BD.minutes(long d)
   int BD.secondes(long d)

   void BD.pause(int m) // pause la BD

   EXEMPLE :
   int co = BD.ouvrirConnexion("172.20.XXX.YYY","maBase","monLogin","monMotDePasse");
   int res = BD.executerSelect(co, "SELECT * FROM maTable");
   while (BD.suivant(res)) {
   Ecran.afficher("Valeur de attribut1 (entier) = ", BD.attributInt(res,"attribut1"));
   Ecran.afficher(" et valeur de attribut2 (chaine) = ", BD.attributString(res,"attribut2"));
   Ecran.sautDeLigne();
   }
   BD.fermerResultat(res);
   BD.fermerConnexion(co);

   //============== BD
	
   msgId : identifiant du message (entier - numéro automatique) - clé primaire de la table
   msgTexte : texte du message (chaîne - 255 caractères maxi)
   msgPseudo : pseudo de l'auteur (chaîne - 20 caractères maxi)
   msgDate : horodatage du message (entier long) 
*/














