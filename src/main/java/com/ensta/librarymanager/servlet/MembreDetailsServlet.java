package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;
import com.ensta.librarymanager.modele.Abonnement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            request.setAttribute("membre", membreService.getById(Integer.parseInt(request.getParameter("id"))));
            request.setAttribute("empruntsCourants", empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            Membre newMembre = membreService.getById(Integer.parseInt(request.getParameter("id")));
            newMembre.setPrenom(request.getParameter("prenom"));
            newMembre.setNom(request.getParameter("nom"));
            newMembre.setEmail(request.getParameter("email"));
            newMembre.setTelephone(request.getParameter("telephone"));
            newMembre.setAbonnement(Abonnement.valueOf(request.getParameter("abonnement")));
            membreService.update(newMembre);

            response.sendRedirect(request.getContextPath() + "/membre_details?id=" + newMembre.getId());
        } catch (NumberFormatException e1) {
            System.out.println(e1.getMessage()); 
            e1.printStackTrace();
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of MembreDetailsServlet");
        }
    }

    
}