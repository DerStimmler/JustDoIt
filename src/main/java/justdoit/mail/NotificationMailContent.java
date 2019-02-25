/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.mail;

import java.sql.Time;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import justdoit.user.User;
import justdoit.user.UserBean;

/**
 *
 * @author tim.schneider
 */
@Stateless
public class NotificationMailContent extends MailContent {

    private String taskName;
    private Date dueDate;
    private Time dueTime;

    public NotificationMailContent(User user, String taskName, Date dueDate, Time dueTime) {
        super(user, "Benachrichtigungsmail");
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public NotificationMailContent() {

    }

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

}
