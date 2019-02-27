package justdoit.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ValidationBean;
import justdoit.hash.HashGenerator;
import justdoit.user.User;
import justdoit.user.UserBean;

@WebServlet(urlPatterns = {"/view/user/changepw/"})
public class ChangePasswordServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Inject
    HashGenerator hashGenerator;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/user/changepassword.jsp");
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
        String username = user.getUsername();
        long id = user.getId();
        String passwordakt = user.getPassword();
        String password0 = request.getParameter("password0");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        List<String> errors = new ArrayList<String>();
        Form form = new Form();
        form.setValues(request.getParameterMap());
        //ALTES PASSWORT ÜBERPRÜFEN
        password0 = this.hashGenerator.getHashText(password0);
        if (!passwordakt.equals(password0)) {
            errors.add("Aktuelles Passwort ist nicht korrekt.");
        }

        //Passwort Hashen dann uNeues Passwort in User Objekt
        password1 = this.hashGenerator.getHashText(password1);
        password2 = this.hashGenerator.getHashText(password2);
        user.setPassword(password1);
        //Check neues Passwort in valdiationBean
        errors = validationBean.validate(user, errors);
        //Prüfung neues Passwort korrekt wiederholt?
        if (!password1.equals(password2)) {
            errors.add("Die Passwörter stimmen nicht überein");
        }
        if (errors.isEmpty()) {
            this.userBean.update(user);
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        } else {
            form.setErrors(errors);
            // Fehler: Formuler erneut anzeigen
            HttpSession session = request.getSession();
            session.setAttribute("change_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }
}
