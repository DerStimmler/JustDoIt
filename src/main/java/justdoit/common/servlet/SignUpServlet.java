package justdoit.common.servlet;

import justdoit.common.jpa.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ejb.ValidationBean;
import justdoit.common.exception.UserAlreadyExistsException;
import justdoit.mail.ejb.MailBean;
import justdoit.mail.ejb.RegisterMailContent;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

/**
 * Sign Up Controller
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {

    public final String userAlreadyExistsExceptionMessage = "Der Benutzername $username ist bereits vergeben! Bitte wählen Sie einen andere!";

    @EJB
    UserBean userBean;

    @EJB
    MailBean mailBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signup.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signup_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben auslesen
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        User user = new User(username, password1, email);
        List<String> errors = new ArrayList<String>();

        // Passwort länge nicht in ValidationBean geprüft da dort der hash (immer 64 Zeichen) getestet wird
        if (password1.length() < 5 || password1.length() > 50) {
            errors.add("Das Passwort muss zwischen 5 und 50 Zeichen lang sein");
        }
        if (!password1.equals(password2)) {
            errors.add("Die Passwörter stimmen nicht überein");
        }
        errors = validationBean.validate(user, errors);
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, password1, email);
                User usermail = this.userBean.findByUsername(username);
                RegisterMailContent mailContent = new RegisterMailContent(usermail);
                try {
                    this.mailBean.sendMail(mailContent);
                } catch (MessagingException ex) {
                    errors.add("Die E-Mail Adresse ist ungültig!");
                }
                // Keine Fehler: Startseite aufrufen
                response.sendRedirect(request.getContextPath() + "/index.html");
            } catch (EJBException ex) {
                Exception exc = ex.getCausedByException();
                if (exc instanceof UserAlreadyExistsException) {
                    errors.add(this.userAlreadyExistsExceptionMessage.replace("$username", user.getUsername()));
                }
            }
        }
        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }
}
