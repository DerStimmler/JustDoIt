package justdoit.todo.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import justdoit.common.jpa.User;

@Entity
public class ToDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Der Aufgabe muss ein Name gegeben werden")
    private String name;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einer Kategorie zugeordnet werden")
    private Category category;

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
    private Date dueDate;

    @NotNull(message = "Bitte geben Sie eine Uhrzeit in der Form HH:MM an")
    private Time dueTime;

    @ManyToMany
    @NotNull(message = "Die Aufgabe muss mindestens einem Benutzer zugeordnet werden")
    List<User> user=new ArrayList<>();

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ToDo(String name, Category category, String description, ToDoStatus status, ToDoPriority priority, Date dueDate, Time dueTime, List<User> user) {
        this.name = name;
        this.category = category;
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
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Time getDueTime() {
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

    public Category getCategory() {
        return category;
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

    public void setCategory(Category category) {
        this.category = category;
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
    //</editor-fold>

    @Override
    public String toString() {
        return "justdoit.task.ToDo[ id=" + id + " ]";
    }

}
