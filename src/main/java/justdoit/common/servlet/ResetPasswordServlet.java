package justdoit.common.servlet;

import justdoit.common.jpa.Form;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ejb.ValidationBean;
import justdoit.hash.HashGenerator;
import justdoit.mail.ejb.MailBean;
import justdoit.mail.ejb.ResetPasswordMailContent;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(urlPatterns = {"/resetpw/"})
public class ResetPasswordServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    MailBean mailBean;

    @EJB
    ValidationBean validationBean;

    @Inject
    HashGenerator hashGenerator;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/resetpw.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("resetpw_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Formulareingaben auslesen
        String username = request.getParameter("username");

        List<String> errors = this.validationBean.validate(username);

        User user = this.userBean.findById(username);

        if (user == null) {
            errors.add("Der Benutzer existiert nicht!");
        }

        Form form = new Form();
        form.setValues(request.getParameterMap());
        form.setErrors(errors);

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            this.resetPassword(user);
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(request.getContextPath() + "/index.html");
        } else {
            // Fehler: Formuler erneut anzeigen
            HttpSession session = request.getSession();
            session.setAttribute("resetpw_form", form);

            response.sendRedirect(request.getRequestURI());
        }
    }

    private void resetPassword(User user) {

        String newPassword = this.generateNewPassword();
        user.setPassword(this.hashGenerator.getHashText(newPassword));
        this.userBean.update(user);
        this.sendResetPasswordMail(user, newPassword);
    }

    private void sendResetPasswordMail(User user, String newPassword) {
        ResetPasswordMailContent resetPasswordMailContent = new ResetPasswordMailContent(user, newPassword);

        try {
            this.mailBean.sendMail(resetPasswordMailContent);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    private String generateNewPassword() {
        return Long.toHexString(Double.doubleToLongBits(Math.random())).substring(0, 9);
    }
}
