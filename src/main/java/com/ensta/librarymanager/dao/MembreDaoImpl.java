package com.ensta.librarymanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class MembreDaoImpl implements MembreDao
{
    private static MembreDaoImpl instance;

    private MembreDaoImpl(){}

    public static MembreDaoImpl getInstance()
    {
        if (instance == null){
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_ALL = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;";
    private static final String SELECT_BY_ID = "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?;";
    private static final String CREATE = "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM membre WHERE id = ?;";
    private static final String COUNT = "SELECT COUNT(DISTINCT id) AS count FROM membre;";

    public List<Membre> getList() throws DaoException {
        List<Membre> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Membre membre = new Membre();
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setAdresse(rs.getString("adresse"));
                membre.setEmail(rs.getString("email"));
                membre.setTelephone(rs.getString("telephone"));
                String s;
                s = rs.getString("abonnement");
                Abonnement abonnement = Abonnement.BASIC;
                switch(s) {
                    case "PREMIUM":
                        abonnement = Abonnement.PREMIUM;
                        break;
                    case "VIP":
                        abonnement = Abonnement.VIP;
                        break;
                }
                membre.setAbonnement(abonnement);
                result.add(membre);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Membre getById(int id) throws DaoException {
        Membre membre = new Membre();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                membre.setId(rs.getInt("id"));
                membre.setNom(rs.getString("nom"));
                membre.setPrenom(rs.getString("prenom"));
                membre.setAdresse(rs.getString("adresse"));
                membre.setEmail(rs.getString("email"));
                membre.setTelephone(rs.getString("telephone"));
                String s;
                s = rs.getString("abonnement");
                Abonnement abonnement = Abonnement.BASIC;
                switch(s) {
                    case "PREMIUM":
                        abonnement = Abonnement.PREMIUM;
                        break;
                    case "VIP":
                        abonnement = Abonnement.VIP;
                        break;
                }
                membre.setAbonnement(abonnement);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du livre: id=" + id);
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
        return membre;
    }

    public int create(String nom, String prenom, String adresse, String email, String telephone ) throws DaoException {
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, "BASIC");
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du membre ");
        } finally {
            // Ici pour bien faire les choses on doit fermer les objets utilisés dans
            // des blocs séparés afin que les exceptions levées n'empèchent pas la fermeture des autres !
            // la logique est la même pour les autres méthodes. Pour rappel, le bloc finally sera toujours exécuté !
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
        return id;
    }

    public void update(Membre membre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().name());
            preparedStatement.setInt(7, membre.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du membre");
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

    public void delete(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du membre ");
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
