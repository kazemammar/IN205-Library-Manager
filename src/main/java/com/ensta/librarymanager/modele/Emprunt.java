package com.ensta.librarymanager.modele;

import java.time.LocalDate;

public class Emprunt
{
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public int getId()
    {
        return id;
    }

    public void setId(int unId)
    {
        this.id = unId;
    }

    public Membre getMembre()
    {
        return membre;
    }

    public void setMembre(Membre unMembre)
    {
        this.membre = unMembre;
    }

    public Livre getLivre()
    {
        return livre;
    }

    public void setLivre(Livre unLivre)
    {
        this.livre = unLivre;
    }

    public LocalDate getDateEmprunt()
    {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate uneDateEmprunt)
    {
        this.dateEmprunt = uneDateEmprunt;
    }

    public LocalDate getDateRetour()
    {
        return dateRetour;
    }

    public void setDateRetour(LocalDate uneDateRetour)
    {
        this.dateRetour = uneDateRetour;
    }

    public Emprunt() {}

    public Emprunt(int unId, Membre unMembre , Livre unLivre, LocalDate uneDateEmprunt, LocalDate uneDateRetour)
    {
        this.id = unId;
        this.membre = unMembre;
        this.livre = unLivre;
        this.dateEmprunt = uneDateEmprunt;
        this.dateRetour = uneDateRetour;
    }

    public String toString()
    { return "\nEmprunt [id=" + id + "\n membre=" + membre.toString() + " livre=" + livre.toString() + " dateEmprunt=" + dateEmprunt + "\n dateRetour=" + dateRetour + "]\n";}

}
