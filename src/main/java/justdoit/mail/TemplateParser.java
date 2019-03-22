package justdoit.mail;

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

            content = this.replaceFields(obj, content);

        } catch (IllegalArgumentException | IllegalAccessException | IOException ex) {
            ex.printStackTrace();
        }

        return content;
    }

    private String replaceFields(Field[] fields, String content, Object obj) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(obj) != null) {
                content = content.replace("$" + field.getName() + "$", field.get(obj).toString());
            }
        }
        return content;
    }

    private String replaceFields(Object obj, String content) throws IllegalAccessException {
        Field[] fields = obj.getClass().getSuperclass().getDeclaredFields();        //geerbte Felder
        content = this.replaceFields(fields, content, obj);

        fields = obj.getClass().getDeclaredFields();            //eigene Felder
        content = this.replaceFields(fields, content, obj);

        return content;
    }
}
