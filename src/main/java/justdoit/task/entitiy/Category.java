/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.task.entitiy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import justdoit.user.User;

/*
 *
 * @author Lichter, Ansgar
 */
@Entity
public class Category implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    //TODO: Schlüssel User und Name?
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @NotNull(message = "Die Kategorie muss einem Benutzer zugeordnet werden")
    private User user;
    
    @NotNull(message = "Der Kategorie muss ein Name gegeben werden")
    private String category;
    
    @OneToMany(mappedBy = "category")
    List<ToDo> toDos = new ArrayList<>();
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Category() {
        
    }
    
    public Category(String category) {
        this.category = category;
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public Long getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getCategory() {
        return category;
    }
    
    public List<ToDo> getToDos() {
        return toDos;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }
    //</editor-fold>    
}
