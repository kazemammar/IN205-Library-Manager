package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.exception.*;
import com.ensta.librarymanager.persistence.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate ;

public class DaoTest
{
    public static void main( String[] args ) throws DaoException
    {
        // Tests pour LivreDao
        System.out.println();
        System.out.println("LIVRE DAO TEST");
        List<Livre> livresAvant = new ArrayList<>();
        List<Livre> livresApres = new ArrayList<>();
        Livre livre8 =  new Livre();
        Livre livre1 = new Livre(1, "The Da Vinci Code","Dan Brown","0-1598-7305-3");
        int countLivresAvant = -1;
        int countLivresApres = -1;
        try (Connection connection = ConnectionManager.getConnection())
        {
            LivreDao dao = LivreDaoImpl.getInstance();
            livre8 = dao.getById(8); // Recuperer le livre pour lequel id = 8
            livresAvant = dao.getList(); // Lister tous les livres
            countLivresAvant = dao.count(); // Compter le nombre de livres
            dao.create("The Book Thief", "Markus Zusak", "0-2185-4889-3"); // Creer un livre
            dao.delete(4); // Supprimer le livre 4
            dao.update(livre1); // Mettre a jour le livre 1
            livresApres = dao.getList(); // Lister tous les livres
            countLivresApres = dao.count(); // Compter le nombre de livres
        }
        catch (DaoException | SQLException e)
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

        // Tests pour MembreDao
        System.out.println();
        System.out.println("MEMBRE DAO TEST");
        List<Membre> membresAvant = new ArrayList<>();
        List<Membre> membresApres = new ArrayList<>();
        Membre membre4 =  new Membre();
        Membre membre1 = new Membre(1, "Brewer", "Thomas", "40.87157, -74.017245", "ThomasMBrewer@dayrep.com", "212-576-1191", Abonnement.VIP); //Upgrade Abonnement
        int countMembresAvant = -1;
        int countMembresApres = -1;
        try (Connection connection = ConnectionManager.getConnection())
        {
            MembreDao dao = MembreDaoImpl.getInstance();
            membre4 = dao.getById(4);
            membresAvant = dao.getList();
            countMembresAvant = dao.count();
            dao.create("Duclos", "Fanchon", "20, rue Marie de Médicis, 34500 BÉZIERS", "FanchonDuclos@dayrep.com", "04 46 20 99 96"); // Creer un membre
            dao.delete(6); // Supprimer le membre 6
            dao.update(membre1); // Mettre à jour le membre 1
        }
        catch (DaoException | SQLException e)
        {
            System.out.println(e);
        }
        try (Connection connection = ConnectionManager.getConnection())
        {
            MembreDao dao = MembreDaoImpl.getInstance();
            membresApres = dao.getList(); // Lister tous les membres
            countMembresApres = dao.count(); // Compter le nombre de membres
        }
        catch (DaoException | SQLException e)
        {
            System.out.println(e);
        }
        try (Connection connection = ConnectionManager.getConnection())
        {
            MembreDao dao = MembreDaoImpl.getInstance();
            membresApres = dao.getList(); // Lister tous les membres
            countMembresApres = dao.count(); // Compter le nombre de membres
        }
        catch (DaoException | SQLException e)
        {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(membresAvant.toString()); // Base de donnees avant
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countMembresAvant); // Nombre de membres avant
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

        // Tests pour EmpruntDao
        System.out.println();
        System.out.println("EMPRUNT DAO TEST");
        List<Emprunt> empruntsAvant = new ArrayList<>();
        List<Emprunt> empruntsApres = new ArrayList<>();
        List<Emprunt> empruntsActuels = new ArrayList<>();
        List<Emprunt> empruntsMembre = new ArrayList<>();
        List<Emprunt> empruntsLivre = new ArrayList<>();
        Emprunt emprunt5 = new Emprunt();
        Emprunt emprunt6 = new Emprunt(6,membre4,livre8,LocalDate.of(2019,05,01),LocalDate.of(2019,05,15));
        int countEmpruntsAvant = -1;
        int countEmpruntsApres = -1;
        try (Connection connection = ConnectionManager.getConnection())
        {
            EmpruntDao dao = EmpruntDaoImpl.getInstance();
            empruntsAvant = dao.getList(); // Lister tous les emprunts
            empruntsActuels = dao.getListCurrent(); // ... courants
            empruntsMembre = dao.getListCurrentByMembre(5); // ... pour le membre 5
            empruntsLivre = dao.getListCurrentByLivre(2); // ... pour le livre 2
            emprunt5 = dao.getById(5); // Recuperer l'emprunt 5
            countEmpruntsAvant = dao.count();
        }
        catch (DaoException | SQLException e)
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
        System.out.println("SELECT_BY_ID :");
        System.out.println(emprunt5.toString());
        System.out.println();
        System.out.println("SELECT_BY_MEMBRE :");
        System.out.println(empruntsMembre.toString());
        System.out.println();
        System.out.println("SELECT_BY_LIVRE :");
        System.out.println(empruntsLivre.toString());
        System.out.println();
        try (Connection connection = ConnectionManager.getConnection())
        {
            EmpruntDao dao = EmpruntDaoImpl.getInstance();
            dao.create(1,1,LocalDate.of(2019,03,11));
            dao.update(emprunt6);
            empruntsApres = dao.getList(); // Lister tous les emprunts
            countEmpruntsApres = dao.count();
        }
        catch (DaoException | SQLException e)
        {
            System.out.println(e);
        }
        System.out.println("Après des changements sur la base de données:");
        System.out.println();
        System.out.println();
        System.out.println("SELECT_ALL :");
        System.out.println(empruntsApres.toString()); // Base de donnees apres
        System.out.println();
        System.out.println("COUNT :");
        System.out.println(countEmpruntsApres); // Nombre d'emprunts apres
        System.out.println();
    }
}
