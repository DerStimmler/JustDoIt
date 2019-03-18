package justdoit.todo.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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

@WebServlet(name = "EditToDoServlet", urlPatterns = {"/view/todo/edit/*"})
public class EditToDoServlet extends HttpServlet {

    private final String noCategory = "Keine Kategorie";

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
        User currentUser = this.userBean.getCurrentUser();
        HttpSession session = request.getSession();

        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        ToDo todo = toDoBean.findById(id);
        // Zurück auf ToDo Übersicht seite wenn es keinen ToDo dieser ID gibt
        if (todo == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        List<User> users = this.userBean.findAll();
        Category currentCategory = null;
        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        ToDoPriority[] priorities = ToDoPriority.values();
        for (Category category : categories) {

            List<ToDo> todos = category.getToDos();
            for (ToDo soloToDo : todos) {
                if (soloToDo.getId().equals(todo.getId())) {
                    String user = category.getUsername();
                    if (user.equals(currentUser.getUsername())) {
                        currentCategory = category;
                        break;
                    }
                }
            }
        }
        List<User> userstodo = todo.getUser();
        List<String> usernames = userstodo.stream().map(User::getUsername).collect(Collectors.toList());
        //Wenn der aktuelle User nicht in den Benutzer des ToDos vorkommt, hat er keine Anzeigerechte
        if (!usernames.contains(currentUser.getUsername())) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        session.setAttribute("categories", this.getAllCategoryNames());
        session.setAttribute("priorities", priorities);
        session.setAttribute("users", users);
        session.setAttribute("currentCategory", currentCategory);

        request.setAttribute("todo", todo);
        request.setAttribute("userstodo", userstodo);
        request.getRequestDispatcher("/WEB-INF/view/editToDo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action.equals("edit")) {
            this.editToDo(request, response);
        }
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
        List<Category> todoCategories = new ArrayList<>();
        Category todoCategory = null;
        HttpSession session = request.getSession();

        User currentUser = this.userBean.getCurrentUser();
        String[] todo_user = request.getParameterValues("todo_user");
        for (String user : todo_user) {
            User todoUser = this.userBean.findById(user);
            users.add(todoUser);
        }

        for (String user : todo_user) {
            if (request.getParameter("todo_category").equals(this.noCategory)) {
                break;
            }
            CategoryId idc = new CategoryId(user, request.getParameter("todo_category"));
            todoCategory = this.categoryBean.findById(idc);

            User todoUser = this.userBean.findById(user);

            if (!todoUser.getUsername().equals(currentUser.getUsername())) {
                List<ToDo> alleToDos = todoUser.getTodos();
                if (!alleToDos.isEmpty()) {
                    for (ToDo todos : alleToDos) {
                        if (todos.getId() == id) {
                            break;
                        } else {
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
                        }
                        todoCategories.add(todoCategory);
                    }
                } else {
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
                }
                todoCategories.add(todoCategory);
            }
        }
        Date dueDate = FormatUtils.parseDate(request.getParameter("todo_due_date"));
        Time dueTime = FormatUtils.parseTime(request.getParameter("todo_due_time"));

        ToDoPriority priority = ToDoPriority.valueOf(request.getParameter("todo_priority"));
        todo.setName(request.getParameter("todo_title"));

        for (String user : todo_user) {
            Category idco = (Category) session.getAttribute("currentCategory");
            CategoryId idc = new CategoryId(user, request.getParameter("todo_category"));
            todoCategory = this.categoryBean.findById(idc);
            User todoUser = this.userBean.findById(user);
            if (!todoUser.getUsername().equals(currentUser.getUsername())) {
                List<ToDo> alleToDos = todoUser.getTodos();
                if (!alleToDos.isEmpty()) {
                    for (ToDo todos : alleToDos) {
                        if (todos.getId() == id) {
                            break;
                        } else {
                            todo.addCategory(todoCategory);
                        }
                    }
                } else {
                    todo.addCategory(todoCategory);
                }
            } else {
                if (todoCategory != null) {
                    todo.removeCategory(idco);
                    todo.addCategory(todoCategory);
                } else {
                    todo.removeCategory(idco);
                }
            }
        }
        //todo.setCategories(todoCategories);
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

    private Object getAllCategoryNames() {
        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        List<String> categoryNames = new ArrayList<>();
        categories.forEach((category) -> {
            categoryNames.add(category.getCategoryName());
        });
        categoryNames.add(this.noCategory);
        return categoryNames;
    }
}
