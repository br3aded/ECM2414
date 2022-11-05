import java.util.ArrayList; 

public class Player {
    private static int counter;
    private int id;
    private CardDeck left;
    private CardDeck right;
    private PlayerHand hand;

    public Player(CardDeck left, CardDeck right)
    {
        this.id = ++counter;
        this.left = left;
        this.right = right;
        this.hand = new PlayerHand();
    }

    public int getId()
    {
        return id;
    }

    public void checkWin()
    {
        // If win: stop all threads and end game.
    }
    
    public CardDeck getLeft()
    {
    	return left;
    }
    
    public CardDeck getRight()
    {
    	return right;
    }
    
    public PlayerHand getHand() {
		return hand;
    }
    
    public class PlayerHand
    {
    	private int handSize = 4;
        private ArrayList<Card> hand = new ArrayList<Card>();
        

        public ArrayList<Card> getHandList()
        {
            return hand;
        }
        
        public void addToHand(Card card)
        {
        	hand.add(card);
        }

        public void drawCard()
        {	
        	if (hand.size() <= handSize) return;
        	
        	Card newCard = left.deQueue();
        	hand.add(newCard);
        }
        
        //need to change this to pick a card from the hand
        public void pushCard(Card card)
        {
        	hand.remove(card);
            right.enQueue(card);
        }
    }
}
