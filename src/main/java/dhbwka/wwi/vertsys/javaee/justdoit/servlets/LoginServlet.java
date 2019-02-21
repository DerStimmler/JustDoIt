/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.servlets;

import dhbwka.wwi.vertsys.javaee.justdoit.login.LoginForm;
import dhbwka.wwi.vertsys.javaee.justdoit.register.RegisterForm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tim.schneider
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    public static final String URL = "/login";
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Objekt mit leeren Eingabewerten im Request Context ablegen,
        // damit es beim Erstaufruf nicht zum Absturz kommt
        HttpSession session = request.getSession();
        LoginForm form = (LoginForm) session.getAttribute("login_form");
        
        if (form == null) {
            session.setAttribute("login_form", new LoginForm());
        }
        
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("login.jsp").forward(request, response);

        // Fehlermeldungen und so weiter aus der Session löschen, damit sie
        // beim nächsten Aufruf verschwinden
        session.removeAttribute("login_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
        
        // Eingegebene Werte prüfen
        LoginForm form = new LoginForm();
        
        form.setName(request.getParameter("name"));
        form.setPassword(request.getParameter("password"));
        form.setRememberMe(Boolean.parseBoolean(request.getParameter("rememberMe")));

        form.checkValues();
        
        // Formular erneut anzeigen, wenn es Fehler gibt        
        if (!form.errors.isEmpty()) {
            // Formular erneut anzeigen, wenn es Fehler gibt
            HttpSession session = request.getSession();
            session.setAttribute("login_form", form);

            response.sendRedirect(request.getContextPath() + LoginServlet.URL);
            return;
        }

        //TODO: Login durchführen
        response.sendRedirect(request.getContextPath());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for the Login Page";
    }

}
