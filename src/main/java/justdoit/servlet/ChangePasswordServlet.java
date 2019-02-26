package justdoit.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.exceptions.OldPasswordIncorrectException;
import justdoit.user.User;
import justdoit.user.UserBean;

@WebServlet(urlPatterns = {"/changepw/"})
public class ChangePasswordServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/changepassword.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("change_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // username automatisch auslesen & Formulareingaben auslesen
        User user = userBean.getCurrentUser();
        //String username = request.getParameter("username");
        String username = user.getUsername();
        long id = user.getId();
        String passwordakt = user.getPassword();
        String password0 = request.getParameter("password0");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        //ChangeForm form = new ChangeForm(username,password0 ,password1, password2, email);
        ChangePasswordForm form = new ChangePasswordForm(password0, password1, password2);
        form.checkValues();

        // Passwort ändern
        if (form.getErrors().isEmpty()) {
            try {
                this.userBean.changePassword(user, passwordakt, password0, password1);
            } catch (OldPasswordIncorrectException ex) {
                form.errors.add(ex.getMessage());
            }
        }
        // Weiter zur nächsten Seite
        if (form.getErrors().isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        } else {
            // Fehler: Formuler erneut anzeigen
            HttpSession session = request.getSession();
            session.setAttribute("change_form", form);

            response.sendRedirect(request.getRequestURI());
        }
    }

}
