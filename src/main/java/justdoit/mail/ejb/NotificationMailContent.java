package justdoit.mail.ejb;

import javax.ejb.Stateless;
import justdoit.common.jpa.User;

@Stateless
public class NotificationMailContent extends MailContent {

    private String taskName;
    private String dueDate;
    private String dueTime;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public NotificationMailContent(User user, String taskName, String dueDate, String dueTime) {
        super(user, "Ein Aufgabe ist morgen fällig! | " + user.getUsername() + " | JustDoIt");
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.setContent();
    }

    public NotificationMailContent() {

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter&Setter">
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
//</editor-fold>

}
