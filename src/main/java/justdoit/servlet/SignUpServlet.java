package justdoit.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        SignUpForm form = new SignUpForm(username, password1, password2, email);
        form.checkValues();

        // Neuen Benutzer anlegen
        if (form.getErrors().isEmpty()) {
            try {
                this.userBean.signup(username, password1, email);
                User user = this.userBean.findByUsername(username);
                RegisterMailContent mailContent = new RegisterMailContent(user);
                this.mailBean.sendMail(mailContent);

            } catch (UserAlreadyExistsException ex) {
                form.errors.add(ex.getMessage());
            }
            // Weiter zur nächsten Seite
            if (form.getErrors().isEmpty()) {
                // Keine Fehler: Startseite aufrufen
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {
                // Fehler: Formuler erneut anzeigen
                HttpSession session = request.getSession();
                session.setAttribute("signup_form", form);

                response.sendRedirect(request.getRequestURI());
            }
        }

    }
}
