package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;

import java.util.List;

public class BooleanArgument extends Argument<Boolean>
{

    @Override
    public boolean test(String input)
    {
        return input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false");
    }

    @Override
    public Boolean get()
    {
        return Boolean.parseBoolean(getInput());
    }

    @Override
    public List<String> getCompletions()
    {
        return List.of("true", "false");
    }

    @Override
    public String getUsage()
    {
        return "boolean";
    }
}
