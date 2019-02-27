package justdoit.mail.ejb;

import justdoit.mail.ejb.MailContent;
import java.sql.Time;
import java.util.Date;
import javax.ejb.Stateless;
import justdoit.common.jpa.User;

@Stateless
public class NotificationMailContent extends MailContent {

    private String taskName;
    private Date dueDate;
    private Time dueTime;

//<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public NotificationMailContent(User user, String taskName, Date dueDate, Time dueTime) {
        super(user, "Benachrichtigungsmail");
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }
//</editor-fold>

}
