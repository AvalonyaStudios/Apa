package eu.avalonya.api.command;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArgumentCollection implements Iterable<Argument<?>>
{

    private final List<Argument<?>> arguments = new ArrayList<>();
    private final List<String> rest = new ArrayList<>();

    public void add(Argument<?> argument)
    {
        arguments.add(argument);
    }

    public Argument<?> get(int index)
    {
        try
        {
            return arguments.get(index);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    public <T> T get(int index, Class<T> type)
    {
        try
        {
            return type.cast(arguments.get(index).get());
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    @NotNull
    @Override
    public Iterator<Argument<?>> iterator()
    {
        return arguments.iterator();
    }

    public int size()
    {
        return arguments.size();
    }

    public void addRest(String rest)
    {
        this.rest.add(rest);
    }

    /**
     * Get the rest of the arguments (no used arguments)
     * @return the rest of the arguments
     */
    public List<String> getRest()
    {
        return rest;
    }
}
