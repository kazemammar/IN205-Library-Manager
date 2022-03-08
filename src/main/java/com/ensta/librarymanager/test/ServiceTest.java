package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.service.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate ;

public class ServiceTest
{
    public static void main( String[] args ) throws ServiceException
    {
        // Tests pour MembreService
        System.out.println();
        System.out.println("MEMBRE SERVICE TEST");
        List<Membre> membresAvant = new ArrayList<>();
        List<Membre> membresDispo = new ArrayList<>();
        List<Membre> membresApres = new ArrayList<>();
        Membre membre4 =  new Membre();
        Membre membre1 =  new Membre(1, "Boss", "Hugo", "96, rue Lenotre, 78120 RAMBOUILLET", "HugoBoss@email.com", "0103042931", Abonnement.VIP); //Upgrade Abonnement
        int countMembresAvant = -1;
        int countMembresApres = -1;
        try
        {
            MembreService mserv = MembreServiceImpl.getInstance();
            membre4 = mserv.getById(4); // Recuperer le membre pour lequel id = 4
            membresAvant = mserv.getList(); // Lister tous les membres
            countMembresAvant = mserv.count(); // Compter le nombre de membres
            membresDispo = mserv.getListMembreEmpruntPossible(); // Lister tous les membres disponibles
            mserv.create("Legault", "Agramant", "17, rue Léon Dierx, 93190 LIVRY-GARGAN", "AgramantLegault@jourrapide.com", "0114658408"); // Creer un membre
            mserv.delete(6); // Supprimer le membre 6
            mserv.update(membre1); // Mettre a jour le membre 1
            membresApres = mserv.getList(); // Lister tous les membres
            countMembresApres = mserv.count(); // Compter le nombre de membres
        }
        catch (ServiceException e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(membresAvant.toString()); // Base de donnees avant
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countMembresAvant); // Nombre de livres avant
        System.out.println();
        System.out.println("SELECT_DISPO :");
        System.out.println(membresDispo.toString());
        System.out.println();
        System.out.println("SELECT_BY_ID :");
        System.out.println(membre4.toString());
        System.out.println();
        System.out.println("Après des changements sur la base de données:");
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(membresApres.toString()); // Base de donnees apres
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countMembresApres); // Nombre de livres apres
        System.out.println();

        // Tests pour LivreService
        System.out.println();
        System.out.println("LIVRE SERVICE TEST");
        List<Livre> livresAvant = new ArrayList<>();
        List<Livre> livresDispo = new ArrayList<>();
        List<Livre> livresApres = new ArrayList<>();
        Livre livre8 =  new Livre();
        Livre livre1 = new Livre(1, "The Perks of Being a Wallflower","Stephen Chbosky","0-3911-7023-6"); // On reecrit le titre avec des majuscules
        int countLivresAvant = -1;
        int countLivresApres = -1;
        try
        {
            LivreService lserv = LivreServiceImpl.getInstance();
            livre8 = lserv.getById(8); // Recuperer le livre pour lequel id = 8
            livresAvant = lserv.getList(); // Lister tous les livres
            countLivresAvant = lserv.count(); // Compter le nombre de livres
            livresDispo = lserv.getListDispo(); // Lister tous les livres disponibles
            lserv.create("The Diary of a Young Girl", "Anne Frank", "0-3310-4816-7"); // Creer un livre
            lserv.delete(4); // Supprimer le livre 4
            lserv.update(livre1); // Mettre a jour le livre 1
            livresApres = lserv.getList(); // Lister tous les livres
            countLivresApres = lserv.count(); // Compter le nombre de livres
        }
        catch (ServiceException e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(livresAvant.toString()); // Base de donnees avant
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countLivresAvant); // Nombre de livres avant
        System.out.println();
        System.out.println("SELECT_DISPO :");
        System.out.println(livresDispo.toString());
        System.out.println();
        System.out.println("SELECT_BY_ID :");
        System.out.println(livre8.toString());
        System.out.println();
        System.out.println("Après des changements sur la base de données:");
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(livresApres.toString()); // Base de donnees apres
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countLivresApres); // Nombre de livres apres
        System.out.println();

        // Tests pour EmpruntService
        System.out.println();
        System.out.println("EMPRUNT SERVICE TEST");
        List<Emprunt> empruntsAvant = new ArrayList<>();
        List<Emprunt> empruntsApres = new ArrayList<>();
        List<Emprunt> empruntsActuels = new ArrayList<>();
        List<Emprunt> empruntsMembre = new ArrayList<>();
        List<Emprunt> empruntsLivre = new ArrayList<>();
        Emprunt emprunt5 = new Emprunt();
        System.out.println();
        int countEmpruntsAvant = -1;
        int countEmpruntsApres = -1;
        try
        {
            EmpruntService eserv = EmpruntServiceImpl.getInstance();
            empruntsAvant = eserv.getList(); // Lister tous les emprunts
            countEmpruntsAvant = eserv.count();
            empruntsActuels = eserv.getListCurrent();
            empruntsMembre = eserv.getListCurrentByMembre(5);
            empruntsLivre = eserv.getListCurrentByLivre(2);
            emprunt5 = eserv.getById(5); // Recuperer l'emprunt 5
        }
        catch (ServiceException e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(empruntsAvant.toString()); // Base de donnees avant
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countEmpruntsAvant); // Nombre d'emprunts apres
        System.out.println();
        System.out.println("SELECT_CURRENT :");
        System.out.println(empruntsActuels.toString());
        System.out.println();
        System.out.println("SELECT_BY_MEMBRE :");
        System.out.println(empruntsMembre.toString());
        System.out.println();
        System.out.println("SELECT_BY_LIVRE :");
        System.out.println(empruntsLivre.toString());
        System.out.println();
        System.out.println("SELECT_BY_ID :");
        System.out.println(emprunt5.toString());
        System.out.println();
        try
        {
            EmpruntService eserv = EmpruntServiceImpl.getInstance();
            eserv.create(1,1,LocalDate.of(2019,03,11));
            eserv.returnBook(6);
            empruntsApres = eserv.getList(); // Lister tous les emprunts
            countEmpruntsApres = eserv.count();
        }
        catch (ServiceException e)
        {
            System.out.println(e);
        }
        System.out.println("Après des changements sur la base de données:");
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(empruntsApres.toString()); // Base de donnees avant
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countEmpruntsApres); // Nombre d'emprunts apres
        System.out.println();

    }
}
