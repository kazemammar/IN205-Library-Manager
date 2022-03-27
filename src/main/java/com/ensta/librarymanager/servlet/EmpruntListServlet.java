package com.ensta.librarymanager.servlet;

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


@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();

        System.out.println("emprunt_list   doGet");
        try {
            String show = request.getParameter("show");
            if (show!=null && show.equals("all")) {
                emprunts = empruntService.getList();
            }else { emprunts = empruntService.getListCurrent();}
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("emprunts", emprunts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
        dispatcher.forward(request, response);

    }
    
}