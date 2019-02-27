package justdoit.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("Der Benutzername " + username + " existiert bereits!s");
    }
}
