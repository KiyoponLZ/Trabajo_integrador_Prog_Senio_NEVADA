package integrado.prog2.exceptions;

// Hereda de RuntimeException para que no nos obligue a usar try-catch en todos lados
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
}