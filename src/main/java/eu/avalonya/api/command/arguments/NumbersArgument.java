package eu.avalonya.api.command.arguments;

import eu.avalonya.api.command.Argument;

public abstract class NumbersArgument<T extends Number> extends Argument<T>
{

    private T min;
    private T max;

    public NumbersArgument()
    {
        super();
    }

    public NumbersArgument(T min, T max)
    {
        super();
    }

    public T getMin()
    {
        return min;
    }

    public T getMax()
    {
        return max;
    }

}
