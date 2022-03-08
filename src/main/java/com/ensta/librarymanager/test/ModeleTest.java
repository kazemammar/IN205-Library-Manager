package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;


import java.time.LocalDate;

public class ModeleTest
{
    public static void main( String[] args ) throws Exception
    {
        int id = 1;
        Membre membre = new Membre(id, "Kazem", "Ammar", "71 Rue Saint-Antoine 75004 Paris", "ammar@ensta-paris.fr", "07 66 66 66 66", Abonnement.VIP);
        Livre livre = new Livre(id, "The Fault in Our Stars ", "John Green", "0-9724-9162-7");
        LocalDate dateEmprunt = LocalDate.of(2022,3,8);
        LocalDate dateRetour = LocalDate.of(2022,4,1);
        Emprunt emprunt = new Emprunt(id, membre, livre, dateEmprunt, dateRetour);
        System.out.println(emprunt.toString());
    }
}
