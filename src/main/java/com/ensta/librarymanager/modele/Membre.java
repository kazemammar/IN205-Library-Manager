package com.ensta.librarymanager.modele;

public class Membre
{
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    public int getId()
    {
        return id;
    }

    public void setId(int unId)
    {
        this.id = unId;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String unNom)
    {
        this.nom = unNom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String unPrenom)
    {
        this.prenom = unPrenom;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String uneAdresse)
    {
        this.adresse = uneAdresse;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String unEmail)
    {
        this.email = unEmail;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String unTelephone)
    {
        this.telephone = unTelephone;
    }

    public Abonnement getAbonnement()
    {
        return abonnement;
    }

    public void setAbonnement(Abonnement unAbonnement)
    {
        this.abonnement = unAbonnement;
    }

    public Membre()
    {}

    public Membre(int unId, String unNom, String unPrenom, String uneAdresse, String unEmail, String unTelephone, Abonnement unAbonnement)
    {
        this.id = unId;
        this.nom = unNom;
        this.prenom = unPrenom;
        this.adresse = uneAdresse;
        this.email = unEmail;
        this.telephone = unTelephone;
        this.abonnement = unAbonnement;
    }

    public String toString()
    { return "Membre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", email=" + email + ", telephone=" + telephone + ", abonnement=" + abonnement + "]\n";}

}
