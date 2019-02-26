package justdoit.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 * This bean validates an entity before saving the entity to the database. (cf.
 * https://www.baeldung.com/javax-validation).
 *
 */
@Stateless
public class ValidationBean {

    @Resource
    Validator validator;

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

        return errors;
    }
}
