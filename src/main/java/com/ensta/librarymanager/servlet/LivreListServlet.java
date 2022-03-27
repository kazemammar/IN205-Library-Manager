package com.ensta.librarymanager.servlet;

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


@WebServlet("/livre_list")
public class LivreListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LivreService livreService = LivreServiceImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreService.getList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
        dispatcher.forward(request, response);
    }
    
}