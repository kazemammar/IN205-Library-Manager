package com.ensta.librarymanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object ;
import java.time.LocalDate ;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImpl implements EmpruntDao
{
    private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl(){}

    public static EmpruntDaoImpl getInstance()
    {
        if (instance == null){
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL = "SELECT e.id AS id, idMembre, nom, prenom, email, telephone, abonnement, idLivre, titre auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
    private static final String SELECT_CURRENT = "SELECT e.id AS id, idMembre, nom, prenom, email, telephone, abonnement, idLivre, titre auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
    private static final String SELECT_BY_MEMBRE = "SELECT e.id AS id, idMembre, nom, prenom, email, telephone, abonnement, idLivre, titre auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL and e.idLivre = ?;";
    private static final String SELECT_BY_LIVRE = "SELECT e.id AS id, idMembre, nom, prenom, email, telephone, abonnement, idLivre, titre auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL and e.idLivre = ?;";
    private static final String SELECT_BY_ID = "SELECT e.id AS id, idMembre, nom, prenom, email, telephone, abonnement, idLivre, titre auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
    private static final String CREATE = "INSERT INTO emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE emprunt SET idMembre=?, idLivre=?, dateEmprunt = ?, dateRetour = ? WHERE id=?;";
    private static final String COUNT = "SELECT COUNT(DISTINCT id) FROM emprunt;";

    public List<Emprunt> getList() throws DaoException
    {
        List<Emprunt> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();
            MembreDao mdao = MembreDaoImpl.getInstance();
            LivreDao ldao = LivreDaoImpl.getInstance();
            java.util.Date retour = new java.util.Date();
            while (rs.next())
            {
                Emprunt emprunt = new Emprunt();

                Membre membre = mdao.getById(rs.getInt("idMembre"));
                Livre livre = ldao.getById(rs.getInt("idLivre"));

                emprunt.setId(rs.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                retour = rs.getDate("dateRetour");
                if(rs.wasNull())
                {
                    emprunt.setDateRetour(null);
                }
                else
                {
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                };
                result.add(emprunt);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List<Emprunt> getListCurrent() throws DaoException
    {
        List<Emprunt> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CURRENT)) {
            ResultSet rs = preparedStatement.executeQuery();
            MembreDao mdao = MembreDaoImpl.getInstance();
            LivreDao ldao = LivreDaoImpl.getInstance();
            java.util.Date retour = new java.util.Date();
            while (rs.next())
            {
                Emprunt emprunt = new Emprunt();

                Membre membre = mdao.getById(rs.getInt("idMembre"));
                Livre livre = ldao.getById(rs.getInt("idLivre"));

                emprunt.setId(rs.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                retour = rs.getDate("dateRetour");
                if(rs.wasNull())
                {
                    emprunt.setDateRetour(null);
                    result.add(emprunt);
                }
                else
                {
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                };
            };
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException
    {
        List<Emprunt> result = new ArrayList<>();
        List<Emprunt> current = new ArrayList<>();
        try {
            current = this.getListCurrent();
            int idm = 0;
            for(Emprunt emprunt : current)
            {
                idm = emprunt.getMembre().getId();
                if (idm==idMembre) result.add(emprunt);
            };
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException
    {
        List<Emprunt> result = new ArrayList<>();
        List<Emprunt> current = new ArrayList<>();
        try {
            current = this.getListCurrent();
            int idl = 0;
            for(Emprunt emprunt : current)
            {
                idl = emprunt.getLivre().getId();
                if (idl==idLivre) result.add(emprunt);
            };
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public Emprunt getById(int id) throws DaoException
    {
        Emprunt emprunt = new Emprunt();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            MembreDao mdao = MembreDaoImpl.getInstance();
            LivreDao ldao = LivreDaoImpl.getInstance();
            java.util.Date retour = new java.util.Date();
            if (rs.next()) {
                Membre membre = mdao.getById(rs.getInt("idMembre"));
                Livre livre = ldao.getById(rs.getInt("idLivre"));

                emprunt.setId(rs.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(rs.getDate("dateEmprunt").toLocalDate());
                retour = rs.getDate("dateRetour");
                if(rs.wasNull())
                {
                    emprunt.setDateRetour(null);
                }
                else
                {
                    emprunt.setDateRetour(rs.getDate("dateRetour").toLocalDate());
                };
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de l'emprunt: id=" + id);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emprunt;}

    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException
    {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement pS = null;
        try
        {
            connection = ConnectionManager.getConnection();
            pS = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            pS.setInt(1, idMembre);
            pS.setInt(2, idLivre);
            pS.setDate(3, java.sql.Date.valueOf(dateEmprunt));
            pS.setDate(4, null);
            pS.executeUpdate();
            rs = pS.getGeneratedKeys();
        }
        catch (SQLException e)
        {
            throw new DaoException("Problème lors de la création de l'emprunt ");
        }
        finally
        {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (pS != null) {
                    pS.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Emprunt emprunt) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(1, emprunt.getMembre().getId());
            preparedStatement.setInt(2, emprunt.getLivre().getId());
            preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour de l'emprunt");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int count() throws DaoException
    {
        int count = 0;
        ResultSet rs = null;
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(COUNT))
        {
            rs = preparedStatement.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return count;
    }
}
