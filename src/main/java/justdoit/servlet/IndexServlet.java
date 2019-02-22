package justdoit.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for the welcome page /index.html. The user is redirected to 
 * "/view/dashboard/"-page. If he is not logged in he will be redirected to the
 * login page to authenticate himself
 */
@WebServlet(urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

}