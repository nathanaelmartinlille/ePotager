package Entite;

public class Jardinier {
	
	String nom;
	String prenom;
	double latitude;
	double longitude;
	boolean vendFruits;
	boolean vendLegumes;
	boolean estDispo;
	
	

	public Jardinier(String nom, String prenom, double latitude,
			double longitude, boolean vendFruits, boolean vendLegumes,
			boolean estDispo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.latitude = latitude;
		this.longitude = longitude;
		this.vendFruits = vendFruits;
		this.vendLegumes = vendLegumes;
		this.estDispo = estDispo;
	}

	public Jardinier(String nom, String prenom, double latitude, double longitude) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.latitude = latitude;
		this.longitude = longitude;
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
