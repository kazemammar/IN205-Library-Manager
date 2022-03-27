package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/membre_add")
public class MembreAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        MembreService membreService = MembreServiceImpl.getInstance();

        try {
            request.setAttribute("membres", membreService.getList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
        dispatcher.forward(request, response);
    }

    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            MembreService membreService = MembreServiceImpl.getInstance();
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();

            int id = membreService.create(request.getParameter("nom"), request.getParameter("prenom"), 
            request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"));
            
            response.sendRedirect(request.getContextPath() + "/membre_details?id=" + id);
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of MembreAddServlet");
        }
    }

    
}