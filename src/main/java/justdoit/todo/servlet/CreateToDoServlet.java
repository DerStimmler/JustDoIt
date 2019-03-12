package justdoit.todo.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.jpa.FormatUtils;
import justdoit.common.ejb.ValidationBean;
import justdoit.common.exception.EntityAlreadyExistsException;
import justdoit.common.jpa.Form;
import justdoit.todo.ejb.CategoryBean;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.CategoryId;
import justdoit.todo.jpa.ToDo;
import justdoit.todo.jpa.ToDoPriority;
import justdoit.todo.jpa.ToDoStatus;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(name = "CreateToDoServlet", urlPatterns = {"/view/todo/create/"})
public class CreateToDoServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        List<User> users = this.userBean.findAll();
        session.setAttribute("users", users);

        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        session.setAttribute("categories", categories);
        //TODO: In deutscher Sprache anzeigen
        ToDoPriority[] priorities = ToDoPriority.values();
        session.setAttribute("priorities", priorities);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/createToDo.jsp");
        dispatcher.forward(request, response);

        session.removeAttribute("todo_form");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        List<User> users = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<Category> todoCategories = new ArrayList<>();
        ToDo todo;
        Category todoCategory = null;
        HttpSession session = request.getSession();

        User currentUser = this.userBean.getCurrentUser();
        String[] todo_user = request.getParameterValues("todo_user");
        for (String user : todo_user) {
            User todoUser = this.userBean.findById(user);
            users.add(todoUser);
        }

        for (String user : todo_user) {
            CategoryId id = new CategoryId(user, request.getParameter("todo_category"));
            todoCategory = this.categoryBean.findById(id);

            User todoUser = this.userBean.findById(user);

            if (todoUser != currentUser) {
                if (todoCategory == null) {
                    try {
                        todoCategory = new Category(request.getParameter("todo_category"), todoUser);
                        this.categoryBean.saveNew(todoCategory, id);
                    } catch (EJBException ex) {
                        if (ex.getCausedByException() instanceof EntityAlreadyExistsException) {
                            errors.add("Das ToDo kann dem Benutzer $user nicht unter der Kategorie $category zugewiesen werden"
                                    .replace("$user", todoUser.getUsername())
                                    .replace("$category", request.getParameter("todo_category")));
                        }
                    }
                }
            }
            todoCategories.add(todoCategory);
        }
        Date dueDate = FormatUtils.parseDate(request.getParameter("todo_due_date"));
        Time dueTime = FormatUtils.parseTime(request.getParameter("todo_due_time"));

        ToDoPriority priority = ToDoPriority.valueOf(request.getParameter("todo_priority"));
        todo = new ToDo(request.getParameter("todo_title"),
                todoCategories,
                request.getParameter("todo_description"),
                ToDoStatus.OPEN,
                priority,
                dueDate,
                dueTime,
                users);
        errors = this.validationBean.validate(todo, errors);

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            session.setAttribute("todo_form", form);

            response.sendRedirect(request.getRequestURI());
        } else {
            this.toDoBean.saveNew(todo, todo.getId());
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        }
    }
}
