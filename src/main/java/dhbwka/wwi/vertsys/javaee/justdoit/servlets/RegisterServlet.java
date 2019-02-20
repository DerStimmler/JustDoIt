/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.justdoit.servlets;

import dhbwka.wwi.vertsys.javaee.justdoit.account.AccountBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    AccountBean accountBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("register.jsp").forward(request, response);

        // In der Session liegende Fehlermeldung verwerfen, damit wir beim
        // nächsten Aufruf wieder mit einem leeren Eingabefeld anfangen
        HttpSession session = request.getSession();
        session.removeAttribute("fehler");
        session.removeAttribute("name");
        session.removeAttribute("email");
        session.removeAttribute("password");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Prüfen, ob der Anwender seinen Namen eingegeben hat
        HttpSession session = request.getSession();

        List<String> fehler = new ArrayList<String>();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (name == null || name.trim().isEmpty()) {
            fehler.add("Bitte gib erst deinen Namen ein.");
            session.setAttribute("fehler", fehler);
            session.setAttribute("name", name);
        }
        if (password == null || password.trim().isEmpty()) {
            fehler.add("Bitte gib erst dein Passwort ein.");
            session.setAttribute("fehler", fehler);
            session.setAttribute("password", password);
        }
        if (email == null || email.trim().isEmpty()) {
            fehler.add("Bitte gib erst deine E-Mail-Adresse ein.");
            session.setAttribute("fehler", fehler);
            session.setAttribute("email", email);
        }
        // Neuen Eintrag speichern
        if (fehler.isEmpty()) {
            this.accountBean.registerAccount(name, email, password);
            response.sendRedirect("/login");
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getContextPath());
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
