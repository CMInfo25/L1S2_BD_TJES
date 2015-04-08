public class Project
{

    static public int connect()
    {
        return BD.ouvrirConnexion("172.20.128.64", "silvert_bd", "silvert", "silvert");
    }

    static public void main( String Args[] )
    {
	int connection = connect();;
	Ecran.afficherln("Connection : ",connection);
	if(connection != -1)
	    {
		// (coucou-hello-18ans)-(cava-hola-19ans)
		int res = BD.executerSelect(connection,"SELECT * FROM message");
		while(BD.suivant(res))
		      {
			  String message = BD.attributString(res,"msgTexte");
			  String pseudo = BD.attributString(res,"msgPseudo");
			  long date = BD.attributLong(res,"msgDate");
			  Ecran.afficherln(BD.heures(date),":",BD.minutes(date),":",BD.secondes(date)," - ",pseudo," : ",message);
		      }
		BD.fermerResultat(res);
		BD.fermerConnexion(connection);
	    }
	Ecran.afficherln("Good bye ! ");
	

    }

}

	/* TUTORIAL 
	int maConnection = BD.ouvrirConnexion(String adresse, String nom_bd, String login, String password);

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














