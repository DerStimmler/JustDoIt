package justdoit.common.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String entityClass) {
        super("Der/Die " + entityClass + " exisitert bereits!");
    }
}
