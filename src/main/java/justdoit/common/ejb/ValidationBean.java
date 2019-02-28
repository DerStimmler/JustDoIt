package justdoit.common.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import justdoit.common.jpa.User;

/**
 * This bean validates an entity before saving the entity to the database. (cf.
 * https://www.baeldung.com/javax-validation).
 *
 */
@Stateless
public class ValidationBean {

    @Resource
    Validator validator;

    private final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    /**
     * This method validates an entity and returns a new List of errors.
     *
     * @param <T>
     * @param object
     * @return List with error messages
     */
    public <T> List<String> validate(T object) {
        List<String> errors = new ArrayList<>();
        return this.validate(object, errors);
    }

    /**
     * This method validates an entity and adds the error message to a already
     * existing list which is passed as a parameter.
     *
     * @param <T>
     * @param object
     * @param errors
     * @return List with error messages
     */
    public <T> List<String> validate(T object, List<String> errors) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(object);

        violations.forEach((ConstraintViolation<T> violation) -> {
            errors.add(violation.getMessage());
        });

        if (object instanceof User) {
            User user = (User) object;
            String email = user.getEmail();
            if (!email.matches(regex)) {
                errors.add("Bitte gib eine gültige E-Mail-Adresse ein");
            }
        }

        return errors;
    }
}
