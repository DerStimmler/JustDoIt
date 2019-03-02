package justdoit.common.jpa;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import justdoit.comment.jpa.Comment;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.ToDo;

@Entity
@Table(name = "JUSTDOIT_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ID")
    private Long id;

    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Ihr gewünschter Benutzername darf nur zwischen 5 und 64 Zeichen lang sein")
    @NotNull(message = "Bitte geben Sie einen Benutzernamen ein!")
    private String username = "";

//    //Length 64 reuqired becuase of sha-256
    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Bitte geben Sie ein Passwort ein!")
    private String password = "";

    @Column(name = "EMAIL")
    @NotNull(message = "Bitte geben Sie eine E-Mail-Adresse ein!")
    private String email = "";

    @ElementCollection
    @CollectionTable(
            name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();

    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ToDo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<Comment> comments = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + (10000 + new Random().nextInt(90000))); //aktuelles Datum + Zeit + 5 stellige Random Zahl
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }
    //</editor-fold>

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
