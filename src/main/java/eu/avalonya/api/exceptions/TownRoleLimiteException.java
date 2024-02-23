package eu.avalonya.api.exceptions;

public class TownRoleLimiteException extends Exception{
    public TownRoleLimiteException() {
        super("Maximum de rôles crées atteint");
    }
}
