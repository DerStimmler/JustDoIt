package justdoit.todo.servlet;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ejb.ValidationBean;
import justdoit.common.exception.EntityAlreadyExistsException;
import justdoit.common.jpa.Form;
import justdoit.todo.ejb.CategoryBean;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.CategoryId;
import justdoit.todo.jpa.ToDo;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(name = "CategriesServlet", urlPatterns = {"/view/categories/"})
public class CategoriesServlet extends HttpServlet {

    public final String categoryAlreadyExistsExceptionMessage = "Die Kategorie $category exisitert bereits! Bitte wählen Sie einen anderen Namen!";
    public final String unexpectedExceptionMessage = "Es ist ein unterwarteter Fehler auftereten! Bitte versuchen Sie es erneut!";
    public final String invalidCategoryName = "Der Name $categoryname ist nicht erlaubt!";

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

        User currentUser = this.userBean.getCurrentUser();
        List<Category> categories = this.categoryBean.findByUser(currentUser);
        request.setAttribute("categories", categories);

        request.getRequestDispatcher("/WEB-INF/view/categories.jsp").forward(request, response);
        request.getSession().removeAttribute("category_form");
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

        String action = request.getParameter("action");

        if (action.equals("create")) {
            this.createCategory(request, response);
        } else if (action.equals("delete")) {
            this.deleteCategory(request, response);
        }
        response.sendRedirect(request.getRequestURI());
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryId categoryId = new CategoryId(this.userBean.getCurrentUser().getUsername(), request.getParameter("category_name"));
        Category category = new Category(request.getParameter("category_name"), this.userBean.getCurrentUser());
        List<String> errors = this.validationBean.validate(category);

        if (category.getCategoryName().equals("Keine Kategorie")) {
            errors.add(this.invalidCategoryName.replace("$categoryname", category.getCategoryName()));
        }

        if (errors.isEmpty()) {
            try {
                this.categoryBean.saveNew(category, categoryId);
                return;
            } catch (EJBException ex) {
                Exception exc = ex.getCausedByException();
                if (exc instanceof EntityAlreadyExistsException) {
                    errors.add(this.categoryAlreadyExistsExceptionMessage.replace("$category", category.getCategoryName()));
                } else {
                    errors.add(this.unexpectedExceptionMessage);
                }
            }
        }

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("category_form", form);
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User currentUser = this.userBean.getCurrentUser();
        String[] selectedCategoryNames = request.getParameterValues("category");
        Category category;

        if (selectedCategoryNames == null) {
            return;
        }

        for (String categoryName : selectedCategoryNames) {
            CategoryId categoryId = new CategoryId(currentUser.getUsername(), categoryName);
            category = this.categoryBean.findById(categoryId);
            if (category == null) {
                continue;
            }
            List<ToDo> toDos = category.getToDos();
            if (toDos != null) {
                for (int i = 0; i < toDos.size(); i++) {
                    ToDo todo = toDos.get(i);
                    todo.removeCategory(category);
                    this.toDoBean.update(todo);
                }
            }
            this.categoryBean.delete(category);
        }
    }

}
