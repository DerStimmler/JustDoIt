/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import justdoit.task.bean.CategoryBean;
import justdoit.task.bean.ToDoBean;
import justdoit.task.entitiy.Category;
import justdoit.task.entitiy.ToDo;
import justdoit.user.UserBean;

/**
 *
 * @author Lichter, Ansgar
 */
@WebServlet(name = "CategriesServlet", urlPatterns = {"/categories/"})
public class CategoriesServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;
    
    @EJB
    ToDoBean toDoBean;
    
    @EJB
    UserBean userBean;

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
            //Neue Kategorie speichern
            Category category = new Category(request.getParameter("category_name"));
            category.setUsername(this.userBean.getCurrentUser());
            //TODO: Category validieren
            this.categoryBean.saveNew(category);
        } else if (action.equals("delete")) {
            //Ausgewählte Kategorien löschen
            
            //Ausgewählte Kategorien besorgen
            String[] selectedCategoryIds = request.getParameterValues("category");
            Category category;
            
            for(String categoryId : selectedCategoryIds) {
                // Kategorie suchen
                try{
                category = this.categoryBean.findById(Long.parseLong(categoryId));
                } catch (NumberFormatException ex) {
                    continue;
                }
                //Wenn keine Kategorie gefunden wurde, mit der näcshten fortfahren
                if(category == null) {
                   continue;
                }
                //Kategorie in den zugeordneten Aufgaben löschen
                List<ToDo> toDos = category.getToDos();
                if(toDos != null) {
                    toDos.forEach((ToDo toDo) -> {
                        toDo.setCategory(null);
                        this.toDoBean.update(toDo);
                    });
                }
                //Kategorie löschen
                this.categoryBean.delete(category);
            }
        }
        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "List of all Categories with the possibility of creating new Categories";
    }

}
