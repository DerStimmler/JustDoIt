package justdoit.todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import justdoit.comment.ejb.CommentBean;
import justdoit.comment.jpa.Comment;
import justdoit.common.ejb.UserBean;
import justdoit.common.jpa.FormatUtils;
import justdoit.common.jpa.User;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.ToDo;

@WebServlet(name = "DetailToDoServlet", urlPatterns = {"/view/todo/detail/*"})
public class DetailToDoServlet extends HttpServlet {

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @EJB
    CommentBean commentBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        User currentUser = this.userBean.getCurrentUser();

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
        ToDo todo = toDoBean.findById(id);
        // Zurück auf ToDo Übersicht seite wenn es keinen ToDo dieser ID gibt
        if (todo == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        List<User> users = todo.getUser();
        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
        List<Comment> comments = commentBean.findByToDoId(id);
        //Wenn der aktuelle User nicht in den Benutzer des ToDos vorkommt, hat er keine Anzeigerechte
        if (!usernames.contains(currentUser.getUsername())) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        String categoryName = "Keine Kategorie";
        for (Category category : todo.getCategories()) {
            if (category.getUsername().equals(currentUser.getUsername())) {
                categoryName = category.getCategoryName();
            }
        }
        request.setAttribute("todo", todo);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("dueDate", FormatUtils.formatDate(todo.getDueDate()));
        request.setAttribute("users", users);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("/WEB-INF/view/detailToDo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        long id = -1;
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 2) {
            try {
                id = Long.parseLong(pathInfo.split("/")[1]);
            } catch (NumberFormatException ex) {
                // URL enthält keine gültige Long-Zahl
            }
        }

        String action = request.getParameter("action");
        if (action.equals("edit")) {
            this.editToDo(request, response, id);
        } else if (action.equals("delete")) {
            this.deleteToDo(request, response, id);
        } else if (action.equals("comment")) {
            this.addComment(request, response, id);
        }
        //response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

    private void deleteToDo(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {

        ToDo todo = toDoBean.findById(id);
        this.toDoBean.delete(todo);
        response.sendRedirect(request.getContextPath() + "/view/dashboard/");
    }

    private void editToDo(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {

        response.sendRedirect(request.getContextPath() + "/view/todo/edit/" + id);
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {

        User currentUser = this.userBean.getCurrentUser();
        ToDo todo = toDoBean.findById(id);
        String text = request.getParameter("todo_comment");
        if (text.trim().length() != 0) { //check if comment only consists of spaces
            Comment comment = new Comment(currentUser, todo, text);
            this.commentBean.saveNew(comment, id);
        }

        response.sendRedirect(request.getRequestURI());
    }
}
