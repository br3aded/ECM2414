import java.util.ArrayList; 
import java.util.Random;

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

    public synchronized boolean checkWin()
    {
    	for(int i=0; i<4;i++) {
    		if(hand.getHandList().get(i) != null) {
		        if(hand.getHandList().get(i).getValue() != id) {
		        		return false;
		        }
    		}
    	}
    	return true;
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
    	//private int handSize = 4;
        private ArrayList<Card> hand = new ArrayList<Card>();
        

        public ArrayList<Card> getHandList()
        {
            return hand;
        }
        
        public void addToHand(Card card)
        {
        	if (card == null || getHandList().size() >= 4) return;
        	
        	hand.add(card);
        }

        public synchronized void drawCard()
        {	
        	Card newCard = left.deQueue();
		    this.addToHand(newCard);
        }
        
        public synchronized void pushCard()
        {
	        ArrayList<Card> tempCardLocation = new ArrayList<Card>();
	        for(int i =0; i<this.hand.size(); i++) {
	        	if(this.hand.get(i) != null) {
		        	if(this.hand.get(i).getValue() != getId()) {
		        		tempCardLocation.add(hand.get(i));
		        	}
	        	}
	        }
	        Random rand = new Random();
	        int n = rand.nextInt(tempCardLocation.size());
	        System.out.println(tempCardLocation.get(n));
	        right.enQueue(tempCardLocation.get(n));
	        hand.remove(tempCardLocation.get(n));
        }
        
        public synchronized ArrayList<Integer> displayHand(){
			ArrayList<Integer> displayHand = new ArrayList<Integer>();
        	for(int i =0;i<this.hand.size();i++) {
        		displayHand.add(this.hand.get(i).getValue());
        	}
        	return displayHand;
        	
        }
    }
}
