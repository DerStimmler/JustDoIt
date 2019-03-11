package justdoit.todo.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import justdoit.comment.jpa.Comment;
import justdoit.common.jpa.User;

@Entity
public class ToDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Der Aufgabe muss ein Name gegeben werden")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull(message = "Die Aufgabe muss einer Kategorie zugeordnet werden")
    private List<Category> categories;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    //vgl. http://tomee.apache.org/examples-trunk/jpa-enumerated/
    @NotNull(message = "Der Aufgabe muss ein Status zugeordnet werden")
    private ToDoStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Der Aufgabe muss eine Priorität zugeordnet werden")
    private ToDoPriority priority;

    @NotNull(message = "Bitte geben Sie ein Fälligkeitsdatum in der Form DD.MM.YYYY an")
    private String dueDate;

    @NotNull(message = "Bitte geben Sie eine Uhrzeit in der Form HH:MM an")
    private String dueTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull(message = "Die Aufgabe muss mindestens einem Benutzer zugeordnet werden")
    private List<User> user = new ArrayList<User>();

    @OneToMany(mappedBy = "todo", fetch = FetchType.EAGER)
    List<Comment> comments = new ArrayList<>();

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ToDo(String name, List<Category> categories, String description, ToDoStatus status, ToDoPriority priority, String dueDate, String dueTime, List<User> user) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.user = user;
    }

    public ToDo() {
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public ToDoPriority getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }

    public void setPriority(ToDoPriority priority) {
        this.priority = priority;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public void removeCategory(Category category) {
        if (this.categories.contains(category)) {
            this.categories.remove(category);
        }
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "justdoit.task.ToDo[ id=" + id + " ]";
    }

}
