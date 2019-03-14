package justdoit.dashboard.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final String noCategory = "Keine Kategorie";

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

        //Alle TODOs des aktuellen Benutzers holen
        List<ToDo> userTasks = this.todoBean.findByUsername(currentUser.getUsername());
        for (ToDo todo : userTasks) {
            MultiValueMap status;
            String categoryName = this.noCategory;
            List<Category> categories = todo.getCategories();
            //Check if category of current User is in this list
            for (Category category : categories) {
                if (category.getUsername().equals(currentUser.getUsername())) {
                    categoryName = category.getCategoryName();
                }
            }
            //Prüfen, ob bereits Eintrag für aktuelle Kategorie in Map
            if (dashboardContent.containsKey(categoryName)) {
                status = dashboardContent.get(categoryName);
            } else {
                status = new MultiValueMap();
            }

            //Eintrag für die aktuelle Aufgabe hinzufügen
            status.put(todo.getStatus().getLabel(), todo);

            //Status Map wieder in dashboardConent
            dashboardContent.put(categoryName, status);
        }
//        //Alle Kategorien auslesen
//        List<Category> categories = this.categoryBean.findByUser(currentUser);
//        //Pro Kategorie die ToDos auslesen und in die Map packen
//        categories.forEach((Category category) -> {
//            List<ToDo> todos = this.todoBean.searchToDo("", category, null, null, currentUser.getUsername());
//            MultiValueMap status = new MultiValueMap();
//            todos.forEach((ToDo todo) -> {
//                status.put(todo.getStatus().getLabel(), todo);
//            });
//            dashboardContent.put(category.getCategoryName(), status);
//        });
//
        String[] statusColors = {"bg-primary", "bg-warning", "bg-success", "bg-danger"};
        ToDoStatus[] status = ToDoStatus.values();
        session.setAttribute("statuses", status);
        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        List<String> categoryNames = new ArrayList<>();
        categories.forEach((category) -> {
            categoryNames.add(category.getCategoryName());
        });
        categoryNames.add(this.noCategory);
        session.setAttribute("categories", categoryNames);
        session.setAttribute("dashboard", dashboardContent);
        session.setAttribute("statusColors", statusColors);
        request.getRequestDispatcher("/WEB-INF/view/view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Long backId = null;
        Long forwardId = null;
        Long searchId = null;

        if (request.getParameter("back") != null) {
            backId = Long.parseLong(request.getParameter("back"));
        }
        if (request.getParameter("forward") != null) {
            forwardId = Long.parseLong(request.getParameter("forward"));
        }
        if (request.getParameter("searchToDo") != null && !request.getParameter("searchToDo").equals("")) {
            searchId = Long.parseLong(request.getParameter("searchToDo"));
        }

        // Detailseite des gesuchten Todos aufrufen
        if (searchId != null) {
            response.sendRedirect(request.getContextPath() + "/view/todo/detail/" + searchId);
            return;
        }
        if (request.getParameter("searchToDo") != null && request.getParameter("searchToDo").equals("")) {
            searchId = Long.parseLong(request.getParameter("searchToDo"));
            response.sendRedirect(request.getContextPath() + "/view/todo/detail/" + searchId); // Detailseite des gesuchten Todos aufrufen
        }

        // move ToDo to another Status Column
        List<ToDoStatus> status = Arrays.asList(ToDoStatus.values());
        ToDo todo = null;

        if (request.getParameter("back") != null) {
            backId = Long.parseLong(request.getParameter("back"));
            todo = this.todoBean.findById(backId);
            int index = status.indexOf(todo.getStatus());
            todo.setStatus(status.get(--index));
        }
        if (request.getParameter("forward") != null) {
            forwardId = Long.parseLong(request.getParameter("forward"));
            todo = this.todoBean.findById(forwardId);
            int index = status.indexOf(todo.getStatus());
            todo.setStatus(status.get(++index));
        }

        this.todoBean.update(todo);

        response.sendRedirect(request.getRequestURI());
    }

}
