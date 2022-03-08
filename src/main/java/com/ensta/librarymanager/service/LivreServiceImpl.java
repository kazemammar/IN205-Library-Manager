package com.ensta.librarymanager.service;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.LivreDaoImpl;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

import java.util.ArrayList;
import java.util.List;

public class LivreServiceImpl implements LivreService
{
	private static LivreServiceImpl instance;

	private LivreServiceImpl() {}

	public static LivreServiceImpl getInstance()
	{
		if (instance == null){
		instance = new LivreServiceImpl();
		}
		return instance;
	}

	public List<Livre> getList() throws ServiceException
	{
		List<Livre> result = new ArrayList<>();
		LivreDao ldao = LivreDaoImpl.getInstance();
		try
		{
			result = ldao.getList();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result;
	}

	public List<Livre> getListDispo() throws ServiceException
	{
		List<Livre> result = new ArrayList<>();
		try
		{
			EmpruntService eserv = EmpruntServiceImpl.getInstance();
			List<Livre> listeTotale = this.getList();
			for(Livre livre : listeTotale)
			{
				if(eserv.isLivreDispo(livre.getId()))
				{
					result.add(livre);
				}
			}
		}
		catch ( ServiceException e)
		{
			System.out.println(e.getMessage());
		}
		return result;
	}

	public Livre getById(int id) throws ServiceException
	{	
		Livre result = new Livre();
		LivreDao ldao = LivreDaoImpl.getInstance();
		try
		{
			result = ldao.getById(id);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
		return result;
	}

	public int create(String titre, String auteur, String isbn) throws ServiceException
	{
		int i = -1;
		if (titre == "" || titre == null) throw new ServiceException("Titre vide"); else
		{
			LivreDao ldao = LivreDaoImpl.getInstance();
			try
			{
				i = ldao.create(titre,auteur,isbn);
			}
			catch (DaoException e)
			{
				System.out.println(e.getMessage());
			};
		};
		return i;
	}

	public void update(Livre livre) throws ServiceException
	{
		String titre = livre.getTitre();
		if (titre == "" || titre == null) throw new ServiceException("Titre vide"); else
		{
			LivreDao ldao = LivreDaoImpl.getInstance();
			try
			{
				ldao.update(livre);
			}
			catch (DaoException e)
			{
				System.out.println(e.getMessage());
			};
		};
	}
	
	public void delete(int id) throws ServiceException
	{	
		LivreDao ldao = LivreDaoImpl.getInstance();
		try
		{
			ldao.delete(id);
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};
	}

	public int count() throws ServiceException
	{
		int count = 0;
		LivreDao ldao = LivreDaoImpl.getInstance();
		try
		{
			count=ldao.count();
		}
		catch (DaoException e)
		{
			System.out.println(e.getMessage());
		};	
		return count;
	}
}
