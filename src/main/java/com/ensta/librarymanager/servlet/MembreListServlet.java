package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.MembreService;
import com.ensta.librarymanager.service.MembreServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/membre_list")
public class MembreListServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        MembreService membreService = MembreServiceImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreService.getList();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("membres", membres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
        dispatcher.forward(request, response);
    }
}