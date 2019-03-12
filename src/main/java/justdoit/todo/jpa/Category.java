package justdoit.todo.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import justdoit.common.jpa.User;

@Entity
@IdClass(CategoryId.class)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue()
    private Long uniqueNumber;

    @Id
    @NotNull(message = "Der Kategorie muss ein Name gegeben werden")
    @Size(min = 1, message = "Bitte geben Sie der Kategorie einen gültigen Namen")
    private String categoryName;

    @Id
    @Column(name = "USERNAME_PK")
    @NotNull(message = "Die Kategorie muss einem Benutzer zugeordnet werden")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "USERNAME_PK", referencedColumnName = "USERNAME")
    @NotNull(message = "Die Kategorie muss einem Benutzer zugeordnet werden")
    private User user;

//    @Id
//    @NotNull
//    private String categoryUsername;
    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    List<ToDo> toDos = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Category() {

    }

    public Category(String categoryName, User user) {
        this.categoryName = categoryName;
        this.user = user;
        this.username = user.getUsername();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public User getCategoryUser() {
        return user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setCategoryUser(User user) {
        this.user = user;
        this.username = user.getUsername();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    public Long getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Long uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoryId getId() {
        return new CategoryId(username, categoryName);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Equals and hashCode">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uniqueNumber);
        hash = 47 * hash + Objects.hashCode(this.categoryName);
        hash = 47 * hash + Objects.hashCode(this.username);
        hash = 47 * hash + Objects.hashCode(this.user);
        hash = 47 * hash + Objects.hashCode(this.toDos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Category)) {
            return false;
        }
        final Category other = (Category) obj;
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.uniqueNumber, other.uniqueNumber)) {
            return false;
        }
        return true;
    }
//</editor-fold>

}
