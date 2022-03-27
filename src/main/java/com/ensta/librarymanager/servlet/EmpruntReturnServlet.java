package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.EmpruntServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();

        try {
            emprunts = empruntService.getListCurrent();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idEmprunt = Integer.parseInt(request.getParameter("id"));
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            empruntService.returnBook(idEmprunt);
            
            response.sendRedirect(request.getContextPath() + "/emprunt_list");
        } catch (NumberFormatException e1) {
            System.out.println(e1.getMessage()); 
            e1.printStackTrace();
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
            e2.printStackTrace();
        } catch (ServiceException e3) {
            System.out.println(e3.getMessage());
            throw new ServletException ("Error in doPost() of EmpruntReturnServlet.");
        }
    }

}