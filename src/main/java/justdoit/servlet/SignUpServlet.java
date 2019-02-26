package justdoit.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ValidationBean;
import justdoit.exceptions.UserAlreadyExistsException;
import justdoit.mail.MailBean;
import justdoit.mail.RegisterMailContent;
import justdoit.user.User;
import justdoit.user.UserBean;

/**
 * Sign Up Controller
 */
@WebServlet(urlPatterns = {"/signup/"})
public class SignUpServlet extends HttpServlet {

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
        if (!password1.equals(password2)) {
            errors.add("Die Passwörter stimmen nicht überein");
        }
        errors = validationBean.validate(user, errors);
        if (errors.isEmpty()) {
            try {
                this.userBean.signup(username, password1, email);
                User usermail = this.userBean.findByUsername(username);
                RegisterMailContent mailContent = new RegisterMailContent(usermail);
                this.mailBean.sendMail(mailContent);
                // Keine Fehler: Startseite aufrufen
                response.sendRedirect(request.getContextPath() + "/index.html");
            } catch (UserAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        } else {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("signup_form", form);
        }
    }
}
