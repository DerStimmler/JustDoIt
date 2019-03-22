package justdoit.mail;

import java.sql.Date;
import java.sql.Time;
import justdoit.common.jpa.FormatUtils;
import justdoit.common.jpa.User;

public class NotificationMailContent extends MailContent {

    private String taskName;
    private String dueDate;
    private String dueTime;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public NotificationMailContent(User user, String taskName, Date dueDate, Time dueTime) {
        super(user, "Ein Aufgabe ist morgen fällig! | " + user.getUsername() + " | JustDoIt");
        this.taskName = taskName;
        this.dueDate = FormatUtils.formatDate(dueDate);
        this.dueTime = FormatUtils.formatTime(dueTime);
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
