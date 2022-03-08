package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.dao.EmpruntDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntServiceImpl implements EmpruntService
{
	private static EmpruntServiceImpl instance;

	private EmpruntServiceImpl() {}

	public static EmpruntServiceImpl getInstance()
	{
		if (instance == null){
		instance = new EmpruntServiceImpl();
		}
		return instance;
	}

	public List<Emprunt> getList() throws ServiceException
	{ 
		List<Emprunt> result = new ArrayList<>();
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			result = edao.getList();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result;
	}

	public List<Emprunt> getListCurrent() throws ServiceException
	{ 
		List<Emprunt> result = new ArrayList<>();
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			result = edao.getListCurrent();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result; 
	}

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException
	{ 
		List<Emprunt> result = new ArrayList<>();
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			result = edao.getListCurrentByMembre(idMembre);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result; 
	}

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException
	{ 
		List<Emprunt> result = new ArrayList<>();
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			result = edao.getListCurrentByLivre(idLivre);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result; 
	}

	public Emprunt getById(int id) throws ServiceException
	{
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		Emprunt emprunt = new Emprunt();
		try
		{
			emprunt = edao.getById(id);
		}
		catch ( DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return emprunt;
	}

	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException
	{
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			edao.create(idMembre, idLivre, dateEmprunt);
		}
		catch ( DaoException e)
		{
			System.out.println(e.getMessage());
		};
	}

	public void returnBook(int id) throws ServiceException
	{
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		try
		{
			Emprunt emprunt = this.getById(id);
			emprunt.setDateRetour( LocalDate.now() );
			edao.update(emprunt);
		}
		catch (DaoException | ServiceException e)
		{
			System.out.println(e.getMessage());
		};
	}

	public int count() throws ServiceException
	{ 
		EmpruntDao edao = EmpruntDaoImpl.getInstance();
		int count = 0;
		try
		{
			count = edao.count();
		}
		catch ( DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return count;
	}

	public boolean isLivreDispo(int idLivre) throws ServiceException
	{
		boolean result = true;
		List<Emprunt> emprunts = new ArrayList<>();
		try
		{
			emprunts = this.getListCurrent(); 
		}
		catch (ServiceException e)
		{
			System.out.println(e.getMessage());
		}
		for(Emprunt e : emprunts)
		{
			if (e.getLivre().getId() == idLivre) result = false;
		} 
		return result; 
	}

	public boolean isEmpruntPossible(Membre membre) throws ServiceException
	{ 
		boolean result = true;
		List<Emprunt> emprunts = new ArrayList<>();
		try
		{
			emprunts = this.getListCurrentByMembre(membre.getId()); 
		}
		catch (ServiceException e)
		{
			System.out.println(e.getMessage());
		}
		Membre emembre = new Membre();
		Abonnement ab = membre.getAbonnement();
		int count = 0;
		for(Emprunt e : emprunts)
		{
			count = count + 1;
		};
		if (count >= 2 && ab == Abonnement.BASIC) result = false;
		if (count >= 5 && ab == Abonnement.PREMIUM) result = false;
		if (count >= 20 && ab == Abonnement.VIP) result = false;
		return result;  }
}

