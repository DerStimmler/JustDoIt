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

        ToDo todo = this.getEditToDo(request.getPathInfo());
        List<User> users = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<User> toDoUser = todo.getUser();
        List<Category> oldtodoCategories = todo.getCategories();
        List<Category> todoCategories = new ArrayList<>();
        Category todoCategory = null;
        HttpSession session = request.getSession();
        Category currentCategory = (Category) session.getAttribute("currentCategory");
        String currentCategoryName = "";
        String toDoCategoryName = request.getParameter("todo_category");
        User currentUser = this.userBean.getCurrentUser();
        String[] todo_user = request.getParameterValues("todo_user");

        if (currentCategory == null) {
            currentCategoryName = this.noCategory;
        } else {
            currentCategoryName = currentCategory.getCategoryName();
        }
        todo.setCategories(todoCategories);
        for (String user : todo_user) {
            //Fügt alle User eine Userliste hinzu
            User inputUser = this.userBean.findById(user);
            users.add(inputUser);

            CategoryId categoryId = new CategoryId(user, toDoCategoryName);
            todoCategory = this.categoryBean.findById(categoryId);

            if (!toDoUser.contains(inputUser)) {
                toDoUser.add(inputUser);
                todo = this.addCategoryForNewUser(todoCategory, inputUser, toDoCategoryName, todo);
            } else if (inputUser.getUsername().equals(currentUser.getUsername())) {
                if (!toDoCategoryName.equals(this.noCategory)) {
                    todo.addCategory(todoCategory);
                }
            } else {
                Category categoryInputUser = this.getCategoryForUsername(inputUser.getUsername(), oldtodoCategories);
                todoCategories.add(categoryInputUser);
            }
        }

        Date dueDate = FormatUtils.parseDate(request.getParameter("todo_due_date"));
        Time dueTime = FormatUtils.parseTime(request.getParameter("todo_due_time"));

        //Hinzufügen der neuen Daten zum ToDo
        ToDoPriority priority = ToDoPriority.valueOf(request.getParameter("todo_priority"));
        todo.setName(request.getParameter("todo_title"));
        todo.setDescription(request.getParameter("todo_description"));
        todo.setStatus(ToDoStatus.OPEN);
        todo.setPriority(priority);
        todo.setDueDate(dueDate);
        todo.setDueTime(dueTime);
        todo.setUser(users);

        //Validierung des ToDos
        errors = this.validationBean.validate(todo, errors);
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

    private ToDo getEditToDo(String pathInfo) {
        long id = -1;
        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }
        return toDoBean.findById(id);
    }

    private ToDo addCategoryForNewUser(Category todoCategory, User inputUser, String toDoCategoryName, ToDo todo) {
        //Ist die Kategorie in category table oder Keine Kategorie muss nichts getan werden.
        if (!toDoCategoryName.equals(this.noCategory)) {
            if (todoCategory == null) {
                try {
                    todoCategory = new Category(toDoCategoryName, inputUser);
                    this.categoryBean.saveNew(todoCategory, todoCategory.getId());
                } catch (EJBException ex) {
                    if (ex.getCausedByException() instanceof EntityAlreadyExistsException) {
                        //Kategorie exisitert bereits
                    }
                }
            }
            todo.addCategory(todoCategory);
        }
        return todo;
    }

    private Category getCategoryForUsername(String username, List<Category> oldtodoCategories) {
        for (Category category : oldtodoCategories) {
            if (category.getUsername().equals(username)) {
                return category;
            }
        }
        return null;
    }
}
