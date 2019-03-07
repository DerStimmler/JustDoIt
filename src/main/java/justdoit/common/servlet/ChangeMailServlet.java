package justdoit.common.servlet;

import justdoit.common.jpa.Form;
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
import justdoit.common.ejb.ValidationBean;
import justdoit.hash.HashGenerator;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(urlPatterns = {"/view/user/changemail/"})
public class ChangeMailServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Inject
    HashGenerator hashGenerator;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/user/changemail.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("changeMail_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // username automatisch auslesen & Formulareingaben auslesen
        User user = userBean.getCurrentUser();
        //String username = request.getParameter("username");
        String username = user.getUsername();
        long id = user.getId();
        String passwordakt = user.getPassword();
        String password0 = request.getParameter("password0");
        String email = request.getParameter("email");
        List<String> errors = new ArrayList<String>();
        Form form = new Form();
        form.setValues(request.getParameterMap());
//ALTES PASSWORT ÜBERPRÜFEN
        password0 = this.hashGenerator.getHashText(password0);
        if (!passwordakt.equals(password0)) {
            errors.add("Aktuelles Passwort ist nicht korrekt.");
        }

        user.setEmail(email);
        //Check neue Email in valdiationBean
        errors = validationBean.validate(user, errors);
        if (errors.isEmpty()) {
            this.userBean.update(user);
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        } else {
            form.setErrors(errors);
            // Fehler: Formuler erneut anzeigen
            HttpSession session = request.getSession();
            session.setAttribute("changeMail_form", form);
            response.sendRedirect(request.getRequestURI());
        }
    }
}
