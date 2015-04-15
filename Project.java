public class Project
{

/*
class H�bergement
  id // cl�
  char type // Appartement, Gite, Chambre
  capcit� // Combien de personne
  Ville // Nom
  Tarif // int(4)
*/

    static public int connect()
    {
        return BD.ouvrirConnexion("172.20.128.64", "silvert_BD", "silvert", "silvert");
    }

    static public void main( String Args[] )
    {
	int connexion = connect();
	Ecran.afficherln("connexion : ",connexion);
	if(connexion != -1)
	    {
                /// ACCUEIL
                //Menu
                // 1 - Saisir un nouvel h�bergement
                // 2 - Cherche un h�bergement selon crit�res
                // 3 - R�servation pour une p�riode
                // ADMIN
                // 4 
                // 5 
                ///////////////////////
                
            /*
            swtich(menu)
            case 1 
            case 2
            case 3             
            */
            
            /*
            case 1 : saisirNouveau() 
              \def 
              Demander un structure h�bergement (avec tous ses champs) et envoy� �a � la BD
                Cr�e une requ�te du style ADD
            */
            
            /*
            case 2 : chercher() 
              \def 
                Demander les crit�res de s�lection, et afficher une requ�te SELECT
            */
            
            /*
            case 3 : reserver() 
              \def 
               Demander la p�riode, cr�e une requ�te du style SELECT pour la valid� et ADD pour la r�servation 
            */
            
            
            
		//BD.fermerResultat(res);
		BD.fermerConnexion(connexion);
	    }
	Ecran.afficherln("Good bye ! ");
        BD.fermerConnexion(connexion);
	

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














