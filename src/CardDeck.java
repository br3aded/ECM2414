import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CardDeck
{
    private static int counter;
    private int id;
    private ArrayList<Card> deck = new ArrayList<Card>();

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
        //System.out.println("Deck" + id + " added a card");
    }

    public synchronized Card deQueue()
    {
        // Removes and returns the head of the queue
    	//System.out.println("Deck" + id + " removed a card");
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