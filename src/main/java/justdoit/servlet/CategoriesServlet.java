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
import justdoit.task.entitiy.ToDo;
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

        //Alle vorhandenen Kategorien suchen
        List<Category> categories = this.categoryBean.findAll();

        //Kategorien als Attribut setzen
        request.setAttribute("categories", categories);

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/categories.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
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
        //Action auslesen
        String action = request.getParameter("action");

        /*
        * Abhängig davon, welcher Button gedrückt wurde, wird entweder eine
        * neue Kategorie erstellt oder die ausgewählten Kategorien werden gelöscht.
        * Dies ist möglich, da beide Buttons ein Attribut mit dem namen "Action"
        * besitzen und entsprechend Ihrer aufgabe wird dem Action-Parameter als
        * Wert "Create" oder "Delete" übergeben.
         */
        if (action.equals("create")) {
            this.createCategory(request, response);
        } else if (action.equals("delete")) {
            this.deleteCategory(request, response);
        }
        response.sendRedirect(request.getRequestURI());
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Category category = new Category(request.getParameter("category_name"));
        category.setUser(this.userBean.getCurrentUser());
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

        //Ausgewählte Kategorien besorgen
        String[] selectedCategoryIds = request.getParameterValues("category");
        Category category;

        for (String categoryId : selectedCategoryIds) {
            try {
                category = this.categoryBean.findById(Long.parseLong(categoryId));
            } catch (NumberFormatException ex) {
                continue;
            }
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
