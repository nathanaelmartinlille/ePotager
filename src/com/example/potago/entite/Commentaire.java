package com.example.potago.entite;

/**
 * Cette entité permet de modeliser un commentaire inscrit en base de donnée.
 * 
 * @author nath
 */
public class Commentaire {

	private Utilisateur utilisateur;
	private Utilisateur auteur;
	private String contenu;
	private Double note;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Utilisateur getAuteur() {
		return auteur;
	}

	public void setAuteur(Utilisateur auteur) {
		this.auteur = auteur;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
