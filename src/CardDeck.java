import java.util.LinkedList;
import java.util.Queue;

public class CardDeck
{
    private static int counter;
    private int id;
    private Queue<Card> deck = new LinkedList<>(); 

    public CardDeck ()
    {
        this.id = ++counter;
    }

    public int getId() 
    {
        return id;
    }

    public Queue<Card> getDeck() 
    {
        return deck;
    }

    private void enQueue(Card card)
    {
        deck.add(card);
    }

    private Card deQueue()
    {
        // Removes and returns the head of the queue
        return deck.poll(); 
    }
}