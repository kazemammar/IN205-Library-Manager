package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.dao.MembreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

import java.util.ArrayList;
import java.util.List;

public class MembreServiceImpl implements MembreService
{
	private static MembreServiceImpl instance;

	private MembreServiceImpl() {}

	public static MembreServiceImpl getInstance()
	{
		if (instance == null){
		instance = new MembreServiceImpl();
		}
		return instance;
	}

	public List<Membre> getList() throws ServiceException
	{
		List<Membre> result = new ArrayList<>();
		MembreDao mdao = MembreDaoImpl.getInstance();
		try
		{
			result = mdao.getList();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result;
	}

	public List<Membre> getListMembreEmpruntPossible() throws ServiceException
	{
		List<Membre> result = new ArrayList<>();
		try
		{
			EmpruntService eserv = EmpruntServiceImpl.getInstance();
			List<Membre> listeTotale = this.getList();
			for(Membre membre : listeTotale)
			{
				if(eserv.isEmpruntPossible(membre))
				{
					result.add(membre);
				}
			}
		}
		catch ( ServiceException e)
		{
			System.out.println(e.getMessage());
		}
		return result;
	}

	public Membre getById(int id) throws ServiceException
	{	
		Membre result = new Membre();
		MembreDao mdao = MembreDaoImpl.getInstance();
		try
		{
			result = mdao.getById(id);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result;
	}

	public int create(String nom, String prenom, String telephone, String adresse, String email ) throws ServiceException
	{
		int i = -1;
		if (nom == "" || nom == null || prenom == "" || prenom == null) 
		{ throw new ServiceException("Nom et/ou Prenom vide"); }
		else
		{
			MembreDao mdao = MembreDaoImpl.getInstance();
			try
			{
				i = mdao.create(nom, prenom, telephone, adresse, email );
			}
			catch (DaoException e)
			{
				System.out.println(e.getMessage());
			};
		};
		return i;
	}

	public void update(Membre membre) throws ServiceException
	{
		String nom = membre.getNom();
		String prenom = membre.getPrenom();
		if (nom == "" || nom == null || prenom == "" || prenom == null) 
		{ throw new ServiceException("Titre vide"); }
		else
		{
			MembreDao mdao = MembreDaoImpl.getInstance();
			try
			{
				mdao.update(membre);
			}
			catch (DaoException e)
			{
				System.out.println(e.getMessage());
			};
		};
	}
	
	public void delete(int id) throws ServiceException
	{	
		MembreDao mdao = MembreDaoImpl.getInstance();
		try
		{
			mdao.delete(id);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
	}

	public int count() throws ServiceException
	{
		int count = 0;
		MembreDao mdao = MembreDaoImpl.getInstance();
		try
		{
			count=mdao.count();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};	
		return count;
	}
}

