package eu.avalonya.api.exceptions;

public class CitizenAlreadyHasTownException extends Exception
{

    public CitizenAlreadyHasTownException()
    {
        super("Citizen already has a town");
    }

}
