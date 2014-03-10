package com.example.potago.entite;
/**
 * Cette entité permet de modeliser un message du tchat inscrit en base de donnée.
 * 
 * @author laura
 */
public class Message {

	private Utilisateur envoyeur;
	private Utilisateur receveur;
	private String contenu;
	
	public Message(Utilisateur envoyeur, Utilisateur receveur, String contenu) {
		super();
		this.envoyeur = envoyeur;
		this.receveur = receveur;
		this.contenu = contenu;
	}

	public Utilisateur getEnvoyeur() {
		return envoyeur;
	}

	public void setEnvoyeur(Utilisateur envoyeur) {
		this.envoyeur = envoyeur;
	}

	public Utilisateur getReceveur() {
		return receveur;
	}

	public void setReceveur(Utilisateur receveur) {
		this.receveur = receveur;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	
}
