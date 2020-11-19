package cz.cvut.fit.ryntluka.exceptions;

public class EntityMissingException extends Exception {
    public EntityMissingException(int id) {
        super("Entity with id: " + id + "was not found in the database.");
    }

    public EntityMissingException() {
        super("At least one entity was not found in the database");
    }
}
