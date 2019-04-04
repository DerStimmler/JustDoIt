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
public class DashboardServlet extends HttpServlet {

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

        HttpSession session = request.getSession();
        User currentUser = this.userBean.getCurrentUser();
        List<String> categoryNames = this.getAllCategoryNames();
        Map<String, MultiValueMap> dashboardContent = this.getDashboardContent(currentUser);

        String[] statusColors = {"bg-primary", "bg-warning", "bg-success", "bg-danger"};
        session.setAttribute("statuses", ToDoStatus.values());
        session.setAttribute("todos", this.todoBean.findByUsername(currentUser.getUsername()));
        session.setAttribute("dashboard", dashboardContent);
        session.setAttribute("statusColors", statusColors);

        if (dashboardContent.get(this.noCategory) == null) {
            categoryNames.remove(this.noCategory);
        }
        session.setAttribute("categories", categoryNames);
        request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        this.changeStatus(request, response);
        this.doSearch(request, response);
    }

    private Map<String, MultiValueMap> getDashboardContent(User currentUser) {
        Map<String, MultiValueMap> dashboardContent = new HashMap<>();

        List<ToDo> userTasks = this.todoBean.findByUsername(currentUser.getUsername());
        if (!userTasks.isEmpty()) {
            for (ToDo todo : userTasks) {
                MultiValueMap status;
                String categoryName = this.noCategory;
                List<Category> categories = todo.getCategories();

                for (Category category : categories) {
                    if (category.getUsername().equals(currentUser.getUsername())) {
                        categoryName = category.getCategoryName();
                        break;
                    }
                }

                if (dashboardContent.containsKey(categoryName)) {
                    status = dashboardContent.get(categoryName);
                } else {
                    status = new MultiValueMap();
                }
                status.put(todo.getStatus().getLabel(), todo);
                dashboardContent.put(categoryName, status);
            }
        } else {
            List<Category> categories = this.categoryBean.findByUser(currentUser);
            for (Category category : categories) {
                dashboardContent.put(category.getCategoryName(), null);
            }
        }
        return dashboardContent;
    }

    private void changeStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<ToDoStatus> status = Arrays.asList(ToDoStatus.values());
        ToDo todo = null;

        if (request.getParameter("back") != null) {
            Long backId = Long.parseLong(request.getParameter("back"));
            todo = this.todoBean.findById(backId);
            int index = status.indexOf(todo.getStatus());
            todo.setStatus(status.get(--index));
        }
        if (request.getParameter("forward") != null) {
            Long forwardId = Long.parseLong(request.getParameter("forward"));
            todo = this.todoBean.findById(forwardId);
            int index = status.indexOf(todo.getStatus());
            todo.setStatus(status.get(++index));
        }
        if (todo != null) {
            this.todoBean.update(todo);
            response.sendRedirect(request.getRequestURI());
        }
    }

    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String searchToDo = request.getParameter("searchToDo");

        if (searchToDo != null && !searchToDo.equals("")) {
            Long searchId = Long.parseLong(searchToDo);
            response.sendRedirect(request.getContextPath() + "/view/todo/detail/" + searchId); // Detailseite des gesuchten Todos aufrufen
        }
    }

    private List<String> getAllCategoryNames() {
        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        List<String> categoryNames = new ArrayList<>();
        categories.forEach((category) -> {
            categoryNames.add(category.getCategoryName());
        });
        categoryNames.add(this.noCategory);
        return categoryNames;
    }
}
