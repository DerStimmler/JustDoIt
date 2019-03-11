package justdoit.common.servlet;

import justdoit.common.jpa.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.mail.ejb.MailBean;
import justdoit.mail.ejb.RegisterMailContent;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

/**
 * Sign Up Controller
 */
@WebServlet(urlPatterns = {"/activation/resend/"})
public class ResendActivationServlet extends HttpServlet {

    public final String userDoesNotExistExceptionMessage = "Der Benutzername $username existiert nicht!";
    public final String userAlreadyActivatedExceptionMessage = "Der Benutzer $username wurde bereits aktiviert!";

    @EJB
    UserBean userBean;

    @EJB
    MailBean mailBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/resendactivation.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("activation_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        List<String> errors = new ArrayList<String>();
        User activationUser = this.userBean.findByUsername(username);

        if (activationUser == null) {
            errors.add(this.userDoesNotExistExceptionMessage.replace("$username", username));
        } else if (!activationUser.getGroups().contains("justdoit-user-inactive")) {
            errors.add(this.userAlreadyActivatedExceptionMessage.replace("$username", username));
        } else {
            try {

                String activationUrl = "/activate/" + Long.toBinaryString(activationUser.getId());
                RegisterMailContent mailContent = new RegisterMailContent(activationUser, activationUrl);
                this.mailBean.sendMail(mailContent);

            } catch (MessagingException ex) {
                errors.add("Die E-Mail Adresse ist ungültig!");
            }
        }

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("activation_form", form);
            response.sendRedirect(request.getRequestURI());
        } else {
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }
}
