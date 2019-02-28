package justdoit.mail.ejb;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class TemplateParser {

    private final String pathRegisterMailTemplate = "/RegisterMailTemplate.html";
    private final String pathNotificationMailTemplate = "/NotificationMailTemplate.html";
    private final String pathResetPasswordMailTemplate = "/ResetPasswordMailTemplate.html";

    public String parseTemplate(Object obj) {
        String path = "";
        if (obj instanceof RegisterMailContent) {
            path = pathRegisterMailTemplate;
        } else if (obj instanceof NotificationMailContent) {
            path = pathNotificationMailTemplate;
        } else if (obj instanceof ResetPasswordMailContent) {
            path = pathResetPasswordMailTemplate;
        }

        String content = "";
        try {
            InputStream in = getClass().getResourceAsStream(path);
            content = IOUtils.toString(in, StandardCharsets.UTF_8.name());

            Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();        //unschön: hier nur geerbte Felder
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    content = content.replace("$" + field.getName() + "$", field.get(obj).toString());
                }
            }

            fields = obj.getClass().getDeclaredFields();            //unschön: hier nur eigene Felder
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null) {
                    content = content.replace("$" + field.getName() + "$", field.get(obj).toString());
                }
            }

        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return content;
    }
}
