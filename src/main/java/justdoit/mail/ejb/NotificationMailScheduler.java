package justdoit.mail.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import justdoit.common.jpa.User;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.ToDo;

@Singleton
public class NotificationMailScheduler {

    @EJB
    MailBean mailBean;

    @EJB
    ToDoBean todoBean;

    @Schedule(dayOfWeek = "*", hour = "12", minute = "0", second = "0", persistent = false)
    public void sendNotificationMails() {
        List<ToDo> todoList = this.todoBean.getDueTasks();
        List<NotificationMailContent> notificationMailContentList = new ArrayList<NotificationMailContent>();
        for (ToDo todo : todoList) {
            for (User user : todo.getUser()) {
                notificationMailContentList.add(new NotificationMailContent(user, todo.getName(), todo.getDueDate(), todo.getDueTime()));
            }
        }
        for (NotificationMailContent notificationMailContent : notificationMailContentList) {
            this.mailBean.sendMail(notificationMailContent);
        }
    }

}
