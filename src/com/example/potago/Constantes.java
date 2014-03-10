package com.example.potago;

/**
 * Cette classe permet de gerer les differentes constantes de l'application.
 * 
 * @author nath
 * 
 */
public class Constantes {

	public static final String NOM_PREFERENCE = "PREFERENCE";

	public static final String LOGIN = "LOGIN";

	public static final String PASSWORD = "PASSWORD";

	/**
	 * Cet URL recupere sous forme de JSON les commentaires pour un profil donné
	 */
	public static final String RECUPERATION_COMMENTAIRE = "http://lauraleclercq.com/ePotager/recupererCommentaire.php";

	/**
	 * Cet URL permet de recuperer les informations à partir d'un ID PROFIL donné
	 */
	public static final String RECUPERATION_INFO_PROFIL = "http://lauraleclercq.com/ePotager/recupererUtilisateur.php";

	public static final String RECUPERATION_GEOLOC = "http://lauraleclercq.com/ePotager/test.php";

	public static final String INSERTION_COMPTE_UTILISATEUR = "http://lauraleclercq.com/ePotager/insererUtilisateur.php";

	public static final String CHECKLOGIN = "http://lauraleclercq.com/ePotager/verifierUtilisateur.php";

	public static final String REPERTOIRE_STOCKAGE_IMAGE = "http://lauraleclercq.com/ePotager/images/";

	public static final String INSERTION_COMMENTAIRE = "http://lauraleclercq.com/ePotager/insererCommentaire.php";

	public static final String MODIFICATION_PROFIL = "http://lauraleclercq.com/ePotager/modifierProfil.php";


	public static final String UPLOAD_SERVEUR = "http://lauraleclercq.com/ePotager/imageUpload.php";

	public static final String RECUPERATION_IMAGES = "http://lauraleclercq.com/ePotager/imageDownload";

	public static final String RECUPERATION_TCHAT = "http://lauraleclercq.com/ePotager/recupererConversations.php";
}

