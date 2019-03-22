package justdoit.common.servlet;

import justdoit.common.jpa.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import justdoit.mail.MailBean;
import justdoit.mail.RegisterMailContent;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

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

        request.setCharacterEncoding("UTF-8");

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/signUp.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("signUp_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Formulareingaben auslesen
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String email = request.getParameter("email");
        User user = new User(username, password, email);
        List<String> errors = new ArrayList<>();

        // Passwort länge nicht in ValidationBean geprüft da dort der hash (immer 64 Zeichen) getestet wird
        if (password.length() < 5 || password.length() > 50) {
            errors.add("Das Passwort muss zwischen 5 und 50 Zeichen lang sein");
        }
        if (!password.equals(passwordConfirm)) {
            errors.add("Die Passwörter stimmen nicht überein");
        }
        errors = validationBean.validate(user, errors);
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, password, email);
                User usermail = this.userBean.findById(username);

                String activationUrl = "/activate/" + Long.toBinaryString(usermail.getUniqueNumber());

                RegisterMailContent mailContent = new RegisterMailContent(usermail, activationUrl);
                this.mailBean.sendMail(mailContent);
                response.sendRedirect(request.getContextPath() + "/index.html");
            } catch (EJBException ex) {
                Exception exc = ex.getCausedByException();
                if (exc instanceof UserAlreadyExistsException) {
                    errors.add(this.userAlreadyExistsExceptionMessage.replace("$username", user.getUsername()));
                }
            } catch (MessagingException ex) {
                errors.add("Die E-Mail Adresse ist ungültig!");
            }
        }
        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("signUp_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }
}
