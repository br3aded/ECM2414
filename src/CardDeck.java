import java.util.ArrayList;

public class CardDeck
{
	//Variables used within the card deck class
    private static int counter;
    private int id;
    private volatile ArrayList<Card> deck = new ArrayList<Card>();

    //the constructor for each card deck method
    public CardDeck ()
    {
        this.id = ++counter;
    }
    
    //returns card deck id
    public int getId() 
    {
        return id;
    }
    
    //returns the array list of card objects in the card list
    public ArrayList<Card> getDeck() 
    {
        return deck;
    }
    
    //adds a card to the end of the array list
    public synchronized void enQueue(Card card)
    {
        deck.add(card);
    }
    
 // Removes and returns the head of the list
    public synchronized Card deQueue() throws NullPointerException
    {
    	if (deck.size() == 0) throw new NullPointerException("Deck contains no cards.");
    	
		Card tempCard = deck.get(0);
    	deck.remove(0);
        return tempCard;
}
    
    //returns an array list of the decks card values instead of card objects
    public synchronized ArrayList<Integer> displayDeck(){
		ArrayList<Integer> displayDeck = new ArrayList<Integer>();
    	for(int i =0;i<this.deck.size();i++) {
    		displayDeck.add(this.deck.get(i).getValue());
    	}
    	return displayDeck;
    	
    }
    
}