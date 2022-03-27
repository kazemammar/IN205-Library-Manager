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

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();

        try {
            int idLivre = Integer.parseInt(request.getParameter("id"));
            Livre livre = livreService.getById(idLivre);
            request.setAttribute("livre", livre);
            request.setAttribute("idLivre", idLivre);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_delete.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
            int idLivre = Integer.parseInt(request.getParameter("id"));
            LivreService livreService = LivreServiceImpl.getInstance();
            livreService.delete(idLivre);
            response.sendRedirect(request.getContextPath() + "/livre_list");
        } catch (NumberFormatException e1) {
            System.out.println(e1.getMessage()); 
            e1.printStackTrace();
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of LivreDeleteServlet");
        }
    }

    
}