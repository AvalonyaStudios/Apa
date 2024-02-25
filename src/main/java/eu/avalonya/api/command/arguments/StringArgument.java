package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;

public class StringArgument extends Argument<String>
{
    @Override
    public boolean test(String input)
    {
        return true;
    }

    @Override
    public String get()
    {
        return getInput();
    }

    @Override
    public String getUsage()
    {
        return "string";
    }
}
