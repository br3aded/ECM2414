import java.util.ArrayList;

public class CardDeck
{
    private static int counter;
    private int id;
    private volatile ArrayList<Card> deck = new ArrayList<Card>();

    public CardDeck ()
    {
        this.id = ++counter;
    }

    public int getId() 
    {
        return id;
    }

    public ArrayList<Card> getDeck() 
    {
        return deck;
    }

    public void enQueue(Card card)
    {
        deck.add(card);
    }

    public synchronized Card deQueue()
    {
        // Removes and returns the head of the queue
    	Card tempCard = deck.get(0);
    	deck.remove(0);
        return tempCard; 
    }
    
    public synchronized ArrayList<Integer> displayDeck(){
		ArrayList<Integer> displayDeck = new ArrayList<Integer>();
    	for(int i =0;i<this.deck.size();i++) {
    		displayDeck.add(this.deck.get(i).getValue());
    	}
    	return displayDeck;
    	
    }
    
}