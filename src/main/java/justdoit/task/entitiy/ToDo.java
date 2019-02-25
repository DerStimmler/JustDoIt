/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.task.entitiy;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import justdoit.user.User;

/**
 *
 * @author Lichter, Ansgar
 */
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
    
    @NotNull(message = "Bitte geben Sie ein Fälligkeitsdatum an")
    private Date dueDate;
    
    @NotNull(message = "Bitte geben Sie eine Uhrzeit an")
    private Time dueTime;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer zugeordnet werden")
    private User user;
   
//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public ToDo(String name, Category category, String description, ToDoStatus status, ToDoPriority priority, Date dueDate, Time dueTime, User user) {
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

    public User getUser() {
        return user;
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

    public void setUsername(User user) {
        this.user = user;
    }
    //</editor-fold>
    
    @Override
    public String toString() {
        return "justdoit.task.ToDo[ id=" + id + " ]";
    }
    
}
