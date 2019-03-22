package justdoit.common.servlet;

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
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(urlPatterns = {"/activate/*"})
public class ActivateAccountServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<>();
        session.removeAttribute("errors");

        long uniqueNumberOfUser = 0;
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                uniqueNumberOfUser = Long.parseLong(pathInfo.split("/")[1], 2);
            } catch (NumberFormatException ex) {
                errors.add("Die ID wurde in der URL nicht im erwarteten Format angegeben! Bitte kontaktieren Sie den Support!");
            }
        }
        errors = this.activateAccount(uniqueNumberOfUser, errors);

        if (!errors.isEmpty()) {
            session.setAttribute("errors", errors);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/activate.jsp");
        dispatcher.forward(request, response);
    }

    private List<String> activateAccount(long uniqueNumberOfUser, List<String> errors) {
        User activateUser = this.userBean.findByUniqueNumber(uniqueNumberOfUser);
        if (activateUser != null) {
            activateUser.removeFromGroup("justdoit-user-inactive");
            activateUser.addToGroup("justdoit-user");
            this.userBean.update(activateUser);
        } else {
            errors.add("Zur ID des Aktivierungslinks konnte kein Benutzer ermittelt werden! Bitte kontatkieren Sie den Support!");
        }
        return errors;
    }
}
