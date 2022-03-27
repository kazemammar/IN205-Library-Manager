package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.LivreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();

        try {
            livres = livreService.getListDispo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
        dispatcher.forward(request, response);
    }

    
    /** 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LivreService livreService = LivreServiceImpl.getInstance();
            int id = livreService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));

            response.sendRedirect(request.getContextPath() + "/livre_details?id=" + id);
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of LivreAddServlet.");
        }
    }
    
}