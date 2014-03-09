package com.example.potago.entite;

import java.util.List;

public class Utilisateur {
	private Integer idUtilisateur;
	String nom;
	String prenom;
	String description;
	double latitude;
	double longitude;
	List<Commentaire> commentaires;
	Boolean estJardinier;
	String mail;
	boolean vendFruits;
	boolean vendLegumes;
	boolean estDispo;

	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}

	public Utilisateur(final String nom, final String prenom, final String description, final double latitude, final double longitude, final boolean vendFruits,
			final boolean vendLegumes, final boolean estDispo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.vendFruits = vendFruits;
		this.vendLegumes = vendLegumes;
		this.estDispo = estDispo;
	}

	public Utilisateur(final String nom, final String prenom, final String description, final String latitude, final String longitude, final int vendFruits, final int vendLegumes,
			final int estDispo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.description = description;
		this.latitude = Double.parseDouble(latitude);
		this.longitude = Double.parseDouble(longitude);
		this.vendFruits = vendFruits == 1 ? true : false;
		this.vendLegumes = vendLegumes == 1 ? true : false;
		this.estDispo = estDispo == 1 ? true : false;
	}

	public Utilisateur(final String nom, final String prenom, final String description, final double latitude, final double longitude) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(final String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	public boolean isVendFruits() {
		return vendFruits;
	}

	public void setVendFruits(final boolean vendFruits) {
		this.vendFruits = vendFruits;
	}

	public boolean isVendLegumes() {
		return vendLegumes;
	}

	public void setVendLegumes(final boolean vendLegumes) {
		this.vendLegumes = vendLegumes;
	}

	public boolean isEstDispo() {
		return estDispo;
	}

	public void setEstDispo(final boolean estDispo) {
		this.estDispo = estDispo;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(final List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Boolean getEstJardinier() {
		return estJardinier;
	}

	public void setEstJardinier(final Boolean estJardinier) {
		this.estJardinier = estJardinier;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(final String mail) {
		this.mail = mail;
	}

	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}
