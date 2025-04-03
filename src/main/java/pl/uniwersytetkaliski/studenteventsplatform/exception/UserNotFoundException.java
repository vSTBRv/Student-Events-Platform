package pl.uniwersytetkaliski.studenteventsplatform.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Użytkownik o ID " + id + " nie został znaleziony. ");
    }
}
