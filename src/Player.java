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

    public boolean checkWin()
    {
        for(int i=0; i<4;i++) {
        	if(hand.getHandList().get(i).getValue() == id) {
        		
        	} else {
        		return false;
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
        	//if (hand.size() <= handSize) return;
        	
        	Card newCard = left.deQueue();
        	hand.add(newCard);
        }
        
        public void pushCard()
        {
        	ArrayList<Card> tempCardLocation = new ArrayList<Card>();
        	for(int i =0; i<this.hand.size(); i++) {
        		//issue with this sometimes
        		if(hand.get(i).getValue() != getId()) {
        			tempCardLocation.add(hand.get(i));
        		}
        	}
        	if(tempCardLocation.size() == 0) {
        		return;
        	}
        	Random rand = new Random();
        	int n = rand.nextInt(tempCardLocation.size());
        	right.enQueue(tempCardLocation.get(n));
        	hand.remove(tempCardLocation.get(n));
        }
        
        public ArrayList<Integer> displayHand(){
			ArrayList<Integer> displayHand = new ArrayList<Integer>();
        	for(int i =0;i<4;i++) {
        		displayHand.add(this.hand.get(i).getValue());
        	}
        	return displayHand;
        	
        }
    }
}
