package justdoit.dashboard.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ejb.UserBean;
import justdoit.common.jpa.User;
import justdoit.todo.ejb.CategoryBean;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.ToDo;
import justdoit.todo.jpa.ToDoStatus;
import org.apache.commons.collections.map.MultiValueMap;

@WebServlet(urlPatterns = {"/view/dashboard/"})
public class ViewServlet extends HttpServlet {

    @EJB
    UserBean userBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    ToDoBean todoBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");

        //TODO: Ids ändern, damit jedes einzeln eingeklappt werden kann
        HttpSession session = request.getSession();
        User currentUser = this.userBean.getCurrentUser();
        Map<String, MultiValueMap> dashboardContent = new HashMap<>();

        //Alle Kategorien auslesen
        List<Category> categories = this.categoryBean.findByUser(currentUser);
        //Pro Kategorie die ToDos auslesen und in die Map packen
        categories.forEach((Category category) -> {
            List<ToDo> todos = this.todoBean.searchToDo("", category, null, null, currentUser.getUsername());
            MultiValueMap status = new MultiValueMap();
            todos.forEach((ToDo todo) -> {
                status.put(todo.getStatus().getLabel(), todo);
            });
            dashboardContent.put(category.getCategoryName(), status);
        });

        ToDoStatus[] status = ToDoStatus.values();
        session.setAttribute("statuses", status);
        session.setAttribute("categories", categories);
        session.setAttribute("dashboard", dashboardContent);
        request.getRequestDispatcher("/WEB-INF/view/view.jsp").forward(request, response);
    }

}
