package justdoit.todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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

@WebServlet(name = "EditToDoServlet", urlPatterns = {"/view/todo/edit/*"})
public class EditToDoServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        //Angeforderter ToDo ermitteln
        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        HttpSession session = request.getSession();
        List<User> users = this.userBean.findAll();
        session.setAttribute("users", users);
        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        session.setAttribute("categories", categories);
        ToDoPriority[] priorities = ToDoPriority.values();
        session.setAttribute("priorities", priorities);
        ToDo todo = toDoBean.findById(id);
        List<User> userstodo = userBean.getUsers(id);
        /* Zurück auf ToDo Übersicht seite wenn es keinen ToDo dieser ID gibt
         if (todo == null) {
            response.sendRedirect(request.getContextPath() + HIERÜBERSICHTSERVLET.URL);
            return;
        }
         */

        request.setAttribute("todo", todo);
        request.setAttribute("userstodo", userstodo);
        request.getRequestDispatcher("/WEB-INF/view/editToDo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("edit")) {
            this.editToDo(request, response);
        } else if (action.equals("break")) {
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
            //Startseite wird aufgerufen
        }
        //response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

    private void editToDo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Angeforderter ToDo ermitteln
        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }

        List<User> users = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        ToDo todo = toDoBean.findById(id);
        Category todoCategory = null;
        HttpSession session = request.getSession();

        User currentUser = this.userBean.getCurrentUser();
        String[] todo_user = request.getParameterValues("todo_user");
        for (String user : todo_user) {
            User todoUser = this.userBean.findByUsername(user);
            users.add(todoUser);
        }

        for (String user : todo_user) {
            CategoryId idc = new CategoryId(user, request.getParameter("todo_category"));
            todoCategory = this.categoryBean.findById(idc);

            User todoUser = this.userBean.findByUsername(user);

            if (todoUser != currentUser) {
                if (todoCategory == null) {
                    try {
                        todoCategory = new Category(request.getParameter("todo_category"), todoUser);
                        this.categoryBean.saveNew(todoCategory, idc);
                    } catch (EJBException ex) {
                        if (ex.getCausedByException() instanceof EntityAlreadyExistsException) {
                            errors.add("Das ToDo kann dem Benutzer $user nicht unter der Kategorie $category zugewiesen werden"
                                    .replace("$user", todoUser.getUsername())
                                    .replace("$category", request.getParameter("todo_category")));
                        }
                    }
                }
            };
        }
        String dueDate = FormatUtils.formatDate(request.getParameter("todo_due_date"));
        String dueTime = FormatUtils.formatTime(request.getParameter("todo_due_time"));

        ToDoPriority priority = ToDoPriority.valueOf(request.getParameter("todo_priority"));
        todo.setName(request.getParameter("todo_title"));
        todo.setCategory(todoCategory);
        todo.setDescription(request.getParameter("todo_description"));
        todo.setStatus(ToDoStatus.OPEN);
        todo.setPriority(priority);
        todo.setDueDate(dueDate);
        todo.setDueTime(dueTime);
        todo.setUser(users);
        //errors = this.validationBean.validate(todo, errors);

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            session.setAttribute("todo_form", form);

            response.sendRedirect(request.getRequestURI());
        } else {
            this.toDoBean.update(todo);
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        }
    }
}
