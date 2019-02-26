package justdoit.servlet;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.ValidationBean;
import justdoit.task.bean.CategoryBean;
import justdoit.task.bean.ToDoBean;
import justdoit.task.entitiy.Category;
import justdoit.task.entitiy.CategoryId;
import justdoit.task.entitiy.ToDo;
import justdoit.user.User;
import justdoit.user.UserBean;

@WebServlet(name = "CategriesServlet", urlPatterns = {"/categories/"})
public class CategoriesServlet extends HttpServlet {

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

        User currentUser = this.userBean.getCurrentUser();
        List<Category> categories = this.categoryBean.findByUser(currentUser);

        request.setAttribute("categories", categories);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/categories.jsp");
        dispatcher.forward(request, response);

        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
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
        Category category = new Category(request.getParameter("category_name"), this.userBean.getCurrentUser());
        List<String> errors = this.validationBean.validate(category);

        if (errors.isEmpty()) {
            this.categoryBean.saveNew(category);
        } else {
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

        for (String categoryName : selectedCategoryNames) {
            CategoryId categoryId = new CategoryId(currentUser.getUsername(), categoryName);
            category = this.categoryBean.findById(categoryId);
            if (category == null) {
                continue;
            }
            List<ToDo> toDos = category.getToDos();
            if (toDos != null) {
                toDos.forEach((ToDo toDo) -> {
                    toDo.setCategory(null);
                    this.toDoBean.update(toDo);
                });
            }
            this.categoryBean.delete(category);
        }
    }

}
