/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.servlets;

import dhbwka.wwi.vertsys.javaee.justdoit.account.AccountBean;
import dhbwka.wwi.vertsys.javaee.justdoit.login.LoginForm;
import dhbwka.wwi.vertsys.javaee.justdoit.register.RegisterForm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
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
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    public static final String URL = "/register";
    
    @EJB
    AccountBean accountBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Objekt mit leeren Eingabewerten im Request Context ablegen,
        // damit es beim Erstaufruf nicht zum Absturz kommt
        HttpSession session = request.getSession();
        RegisterForm form = (RegisterForm) session.getAttribute("register_form");
        
        if (form == null) {
            session.setAttribute("register_form", new RegisterForm());
        }
        
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("register.jsp").forward(request, response);

        // Fehlermeldungen und so weiter aus der Session löschen, damit sie
        // beim nächsten Aufruf verschwinden
        session.removeAttribute("register_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            
        request.setCharacterEncoding("utf-8");
        
        // Eingegebene Werte prüfen
        RegisterForm form = new RegisterForm();
        
        form.setName(request.getParameter("name"));
        form.setPassword(request.getParameter("password"));
        form.setEmail(request.getParameter("email"));

        form.checkValues();
        
        // Formular erneut anzeigen, wenn es Fehler gibt        
        if (!form.errors.isEmpty()) {
            // Formular erneut anzeigen, wenn es Fehler gibt
            HttpSession session = request.getSession();
            session.setAttribute("register_form", form);

            response.sendRedirect(request.getContextPath() + RegisterServlet.URL);
            return;
        }

        // Eintrag speichern und zurück zur Startseite
        this.accountBean.registerAccount(form.getName(), form.getEmail(), form.getPassword());
        response.sendRedirect(request.getContextPath() + LoginServlet.URL);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for the Registration Page";
    }

}
