package com.example.potago.entite;

import java.util.List;

public class Utilisateur {

	private Integer idUtilisateur;
	private String nom;
	private String prenom;
	private String description;
	private double latitude;
	private double longitude;
	private List<Commentaire> commentaires;
	private Boolean estJardinier;
	private String mail;
	private boolean vendFruits;
	private boolean vendLegumes;
	private boolean estDispo;

	public Utilisateur() {

	}

	public Utilisateur(String nom, String prenom, String description, double latitude, double longitude, boolean vendFruits, boolean vendLegumes,
			boolean estDispo) {
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

	public Utilisateur(String nom, String prenom, String description, String latitude, String longitude, boolean vendFruits, boolean vendLegumes,
			boolean estDispo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.description = description;
		this.latitude = Double.parseDouble(latitude);
		this.longitude = Double.parseDouble(longitude);
		this.vendFruits = vendFruits;
		this.vendLegumes = vendLegumes;
		this.estDispo = estDispo;
	}

	public Utilisateur(String nom, String prenom, String description, double latitude, double longitude) {
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isVendFruits() {
		return vendFruits;
	}

	public void setVendFruits(boolean vendFruits) {
		this.vendFruits = vendFruits;
	}

	public boolean isVendLegumes() {
		return vendLegumes;
	}

	public void setVendLegumes(boolean vendLegumes) {
		this.vendLegumes = vendLegumes;
	}

	public boolean isEstDispo() {
		return estDispo;
	}

	public void setEstDispo(boolean estDispo) {
		this.estDispo = estDispo;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Boolean getEstJardinier() {
		return estJardinier;
	}

	public void setEstJardinier(Boolean estJardinier) {
		this.estJardinier = estJardinier;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Integer idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}
