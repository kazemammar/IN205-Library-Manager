package com.ensta.librarymanager.modele;

public class Livre
{
    private int id;
    private String titre;
    private String auteur;
    private String isbn;

    public int getId()
    {
        return id;
    }

    public void setId(int unId)
    {
        this.id = unId;
    }

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String unTitre)
    {
        this.titre = unTitre;
    }

    public String getAuteur()
    {
        return auteur;
    }

    public void setAuteur(String unAuteur)
    {
        this.auteur = unAuteur;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String unIsbn)
    {
        this.isbn = unIsbn;
    }

    public Livre()
    {}

    public Livre(int unId, String unTitre, String unAuteur, String unIsbn)
    {
        this.id = unId;
        this.titre = unTitre;
        this.auteur = unAuteur;
        this.isbn = unIsbn;
    }

    public String toString()
    { return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", isbn=" + isbn + "]\n";}

}
