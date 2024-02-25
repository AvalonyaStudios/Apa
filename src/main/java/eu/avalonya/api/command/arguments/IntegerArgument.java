package eu.avalonya.api.command.arguments;

public class IntegerArgument extends NumbersArgument<Integer>
{
    @Override
    public boolean test(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    @Override
    public Integer get()
    {
        return Integer.parseInt(getInput());
    }

    @Override
    public String getUsage()
    {
        return "integer";
    }
}
