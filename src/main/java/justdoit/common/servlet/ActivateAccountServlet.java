package justdoit.common.servlet;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import justdoit.common.ejb.ValidationBean;
import justdoit.mail.ejb.MailBean;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(urlPatterns = {"/activate/*"})
public class ActivateAccountServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    MailBean mailBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //ID aus der URL auslesen
        long id = 0;
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1], 2);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }

        //User auf aktiv setzen
        User activateUser = this.userBean.findById(id);
        activateUser.removeFromGroup("justdoit-user-inactive");
        activateUser.addToGroup("justdoit-user");
        //User speichern
        this.userBean.update(activateUser);

        //Aktivierungsnachricht
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/activate.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
