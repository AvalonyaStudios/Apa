package eu.avalonya.api.command;

import java.util.List;

public abstract class Argument<T>
{
    private boolean required = false;
    private String input;

    public Argument()
    {
        this(false);
    }

    public Argument(boolean required)
    {
        this.required = required;
    }

    public abstract boolean test(String input);
    public abstract T get();

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public boolean isRequired()
    {
        return required;
    }

    public List<String> getCompletions()
    {
        return List.of();
    }

    public void setInput(String input)
    {
        this.input = input;
    }

    public String getInput()
    {
        return input;
    }

    public String getUsage()
    {
        return getClass().getSimpleName();
    }
}
