public class Card {
	//variables used for the card class
    private static int counter;
    private int id;
    private int value;

    //the constructor for the card class
    public Card (int value)
    {
        this.id = ++counter;
        this.value = value;
    }
    
    //returns the card id
    public int getId()
    {
        return id;
    }
    
    //returns the value of the card
    public int getValue()
    {
        return value;
    }
}
