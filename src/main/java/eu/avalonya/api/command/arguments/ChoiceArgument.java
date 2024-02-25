package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;

import java.util.List;

public class ChoiceArgument extends Argument<String>
{

    private List<String> choices;

    public ChoiceArgument(List<String> choices)
    {
        this(choices, false);
    }

    public ChoiceArgument(List<String> choices, boolean required)
    {
        super(required);

        this.choices = choices;
    }

    @Override
    public boolean test(String input)
    {
        return choices.contains(input);
    }

    @Override
    public String get()
    {
        return getInput();
    }

    @Override
    public List<String> getCompletions()
    {
        return choices;
    }

    @Override
    public String getUsage()
    {
        return String.join("|", choices);
    }
}
