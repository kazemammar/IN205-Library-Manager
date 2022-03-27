package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            request.setAttribute("livre", livreService.getById(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("livresCourants", empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            Livre newLivre = livreService.getById(Integer.parseInt(request.getParameter("id")));
            newLivre.setAuteur(request.getParameter("auteur"));
            newLivre.setTitre(request.getParameter("titre"));
            newLivre.setIsbn(request.getParameter("isbn"));
            livreService.update(newLivre);

            response.sendRedirect(request.getContextPath() + "/livre_details?id=" + newLivre.getId());
        } catch (NumberFormatException e1) {
            System.out.println(e1.getMessage()); 
            e1.printStackTrace();
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of LivreDetailsServlet");
        }
    }

    
}