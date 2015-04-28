public class Project
{
    
    /* ======================================== */
    /*               STRUCTURE                  */
    /* ======================================== */
    
    
    static class Hebergement
    {
	int id=-1;
	int type = 0;
	short capacite =-1;
	String ville ="error";
	short tarif =-1 ;
    }


    /* ======================================== */
    /*               VALIDATION                 */
    /* ======================================== */


    static public boolean verifieConnexion(int connexion)
    {
	if(connexion == -1)
	    {
		Ecran.afficherln(" Erreur, connexion non initialisee.");
		return false;
	    }
	return true;
    }

    /* ======================================== */
    /*      FONCTION AFFICHAGE CONSOLE          */
    /* ======================================== */

    static public void afficherHebergement(int id, int enuma, int capacite, String ville, int tarif)
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
    {
	afficherHebergement(h.id, h.type, h.capacite, h.ville, h.tarif);
    }

    static public void afficherHerbergement(int res)
    {
	Ecran.afficherln(" Reservation ID : " + BD.attributInt(res, "heID"));			
	Ecran.afficher(" Type : ");
	switch(BD.attributInt(res, "heType"))
	    {
	    case 0 : Ecran.afficher("gite"); break;
	    case 1 : Ecran.afficher("appartement"); break;
	    case 2 : Ecran.afficher("chambre"); break;
	    default : Ecran.afficher("Inconnu (probablement un endroit melant l'espace et le temps brisant leur continum...)"); break;
	    }
	Ecran.afficherln(" Capacite : " + BD.attributInt(res, "heCapacite"));
	Ecran.afficherln(" Ville : " + BD.attributInt(res, "heVille"));
	Ecran.afficherln(" Tarif : " + BD.attributInt(res, "heTarif"));
		

    }

    /* ======================================== */
    /*               FONCTION MENU              */
    /* ======================================== */


    static public void saisirNouveau(int connexion)
    /* Permet l'enregistrement d'un nouvel hebergement */
    {
	if(verifieConnexion(connexion))
	    {
		Ecran.afficher("Saissisez l'id puis le type, puis la capacite, puis la ville puis le tarif, de l'hebergement");
		Hebergement nouveau= new Hebergement();
	       
		// ID
		Ecran.afficherln(" Veuillez saisir le numero d'enregistrement de l'hebergement");
		nouveau.id = Clavier.saisirInt();
	       
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
		Ecran.afficherln(" Tapez 'o' pour valider.");
		char reponse = 'X';
		reponse = Clavier.saisirChar();
	       
		if (reponse == 'o');
		{
		    // Ajout a la table
		    if( 
		       BD.executerUpdate(connexion,
					 "INSERT INTO HEBERGEMENT VALUES(" 
					 + Integer.toString(nouveau.id) + "," 
					 + Integer.toString(nouveau.type) + "," 
					 + Short.toString(nouveau.capacite) + "," 
					 + "'" + nouveau.ville + "'" 
					 + "," + nouveau.tarif 
					 + ")")
		       == -1
			)
			{
			    Ecran.afficherln(" Il y a eu un probleme lors de l'ajout. Veuillez ressayer. ");
			}
		    else
			{
			    Ecran.afficherln(" Ajout fait, merci de votre confiance !");
			}
		   
		}
	    }
    }
    
    static public int rechercher(int connexion)
    {
	// SAISIE DE LA RESERVATION
	Ecran.afficherln(" ====== RESERVATION ===== ");
	Ecran.afficher(" Saisir la date d'arrivee que vous souhaitez ");
	Ecran.afficher("\n Annee : ");	
	int annee = Clavier.saisirInt();

	Ecran.afficher("\n Mois : (chiffre) "); 
	int mois = Clavier.saisirInt();

	Ecran.afficher("\n Jour : "); 
	int jour = Clavier.saisirInt();

	Ecran.afficher("\n Heure d'arrivee "); 
	int heure = Clavier.saisirInt();

	Ecran.afficher("\n Quel est l'id de l'hebergement que vous souhaitez ? "); 
	int id = Clavier.saisirInt();

	Ecran.afficher("\n Duree du sejour : (jours) "); 
	int dureeJ = Clavier.saisirInt();
	
	// VERIFICATION DE LA DISPONIBILITE
	String requete = " SELECT HEBERGEMENT.* FROM  HEBERGEMENT AS H WHERE heID = " + Integer.toString(id) + " AND NOT EXISTS (SELECT * FROM RESERVATION WHERE reHebergement = H.heID AND reDateArrivee >= " + Long.toString(BD.date(jour, mois, annee, heure, 0, 0)) + " AND reDateDepart  <= " + Long.toString(BD.date(jour + dureeJ, mois, annee, heure, 0, 0)) + ")";
	int res = BD.executerSelect(connexion, requete);

	// AFFICHAGE DES RESULTATS CORRESPONDANTS 
	Ecran.afficherln(" ====== DISPONIBILITES ======= ") ;
	
	int nResults = 0;
	while(BD.suivant(res))
	    {
		nResults ++;
		afficherHerbergement(res);
	    }

	BD.fermerResultat(res);
	return nResults;

    }

    static public void reserver(int connexion)
    {
	/*
	  Demander la periode, cree une requete du style SELECT pour la valide et ADD pour la reservation 
	*/
	
	/// SECURITE
	if(!verifieConnexion(connexion))return;

	
	// AFFICHAGE EN CAS D'AUCUN RESULTAT CORRESPONDANT
	switch(rechercher(connexion))
	    {
	    case 0:
		{
		    Ecran.afficherln(" Desole, votre requete ne correspond a aucune de nos disponibilites; veuillez vous assurer des donnees que vous avez saisies et recommencer la manoeuvre.");
		}break;

		// AFFICHAGE EN CAS D'AU MOINS UN RESULTAT CORRESPONDANT	
	    case 1:
		{
		    /// Reserver
		}break;

	    default : 
		{
		    Ecran.afficherln(" Trop de possibilites, veuillez affiner votre recherche.");
		}break;
	    }
    }

    static public int connect()
    {
	return 2; // ENLEVE LE ! 
	//	return BD.ouvrirConnexion("172.20.128.64", "silvert_BD", "silvert", "silvert");
    }









    static public void main( String Args[] )
    {
	int connexion = connect();
	Ecran.afficherln("connexion : ",connexion);
	if(connexion != -1)
	    {
		int menu;
                
		do
		    {
			Ecran.afficherln("Bienvenue sur *inserer jeu de mot ici*.");
			Ecran.afficherln(" ===== Menu ===== ");
			Ecran.afficherln(" 1 - Ajouter son hebergement \n 2 - Rechercher un hebergement \n 3 - Réserver un hébergement \n 4 - Quitter ");
			menu = Clavier.saisirInt();  
			switch(menu)
			    {
			    case 1:
				saisirNouveau(connexion);
				break;
                  
			    case 2:
				rechercher(connexion);
				break;
                  
			    case 3:
				reserver(connexion);
				break;
			    case 4 : break;
			    default:
				Ecran.afficherln("Mille excuses, mais vous avez du faire une erreur. Recommencez s'il vous plait.");
				break;
			    }
		    }while (menu != 4);

		Ecran.afficherln("Good bye ! ");
		BD.fermerConnexion(connexion);
	

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














