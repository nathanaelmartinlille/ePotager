package com.example.potago.entite;

public class Jardinier extends Utilisateur {

	boolean vendFruits;
	boolean vendLegumes;
	boolean estDispo;

	public Jardinier(String nom, String prenom, String description, double latitude, double longitude, boolean vendFruits, boolean vendLegumes, boolean estDispo) {
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

	public Jardinier(String nom, String prenom, String description, String latitude, String longitude, boolean vendFruits, boolean vendLegumes, boolean estDispo) {
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

	public Jardinier(String nom, String prenom, String description, double latitude, double longitude) {
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

}
