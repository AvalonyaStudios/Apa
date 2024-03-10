package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;

public class RegexArgument extends Argument<String>
{

    private final String regex;

    public RegexArgument(String regex)
    {
        this(regex, false);
    }

    public RegexArgument(String regex, boolean required)
    {
        this.regex = regex;

        setRequired(required);
    }

    @Override
    public boolean test(String input)
    {
        return input.matches(regex);
    }

    @Override
    public String get()
    {
        return getInput();
    }
}
