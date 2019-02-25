package justdoit.servlet;

import java.io.IOException;
import javafx.scene.control.Alert;
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
import org.omg.CORBA.CTX_RESTRICT_SCOPE;

/**
 *
 * @author Goeller
 */
@WebServlet(urlPatterns = {"/changemail/"})
public class ChangeMailServlet extends HttpServlet {
           
    @EJB
    UserBean userBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/changemail.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("changeMail_form");
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
        String password0 =request.getParameter("password0");
        String email = request.getParameter("email");
        
        //password 0 ist das angegebene aktuelle Passwort
        ChangeMailForm form = new ChangeMailForm(password0, email);
        form.checkMailValues();
        
        // Mail ändern
        if (form.getErrors().isEmpty()) {
            try {
                this.userBean.changeMail(user, passwordakt,password0, email);
            } catch (OldPasswordIncorrectException ex) {
                //form.errors.add(ex.getMessage());
            }
        }
        // Weiter zur nächsten Seite
        if (form.getErrors().isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        } else {
            // Fehler: Formuler erneut anzeigen
            HttpSession session = request.getSession();
            session.setAttribute("changeMail_form", form);
            
            response.sendRedirect(request.getRequestURI());
        }
    }
    
}

   