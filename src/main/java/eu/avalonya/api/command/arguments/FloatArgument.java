package eu.avalonya.api.command.arguments;

public class FloatArgument extends NumbersArgument<Float>
{
    @Override
    public boolean test(String input)
    {
        try
        {
            Float.parseFloat(input);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    @Override
    public Float get()
    {
        return Float.parseFloat(getInput());
    }

    @Override
    public String getUsage()
    {
        return "float";
    }
}
