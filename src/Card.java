public class Card {
    private static int counter;
    private int id;
    private int value;

    public Card (int value)
    {
        this.id = ++counter;
        this.value = value;
    }

    public int getId()
    {
        return id;
    }

    public int getValue()
    {
        return value;
    }

    //TODO create unique identifier based on value, id etc.
}
