import java.util.ArrayList; 

public class Player {
    private static int counter;
    private int id;
    private CardDeck left;
    private CardDeck right;

    public Player(CardDeck left, CardDeck right)
    {
        this.id = ++counter;
        this.left = left;
        this.right = right;
    }

    public int getId()
    {
        return id;
    }

    private void checkWin()
    {
        // If win: stop all threads and end game.
    }
    
    public class PlayerHand
    {
    	private int handSize = 4;
        private ArrayList<Card> hand = new ArrayList<Card>();
        

        public ArrayList<Card> getHand()
        {
            return hand;
        }

        private void drawCard()
        {	
        	if (hand.size() <= handSize) return;
        	
        	Card newCard = left.deQueue();
        	hand.add(newCard);
        }

        private void pushCard(Card card)
        {
        	hand.remove(card);
            right.enQueue(card);
        }
    }
}
