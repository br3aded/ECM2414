import java.util.ArrayList; 
import java.util.Random;

public class Player {
	//Variables used within the Player Class
    private static int counter;
    private int id;
    private CardDeck left;
    private CardDeck right;
    private volatile PlayerHand hand;
    
    //the Player Constructor
    public Player(CardDeck left, CardDeck right)
    {
        this.id = ++counter;
        this.left = left;
        this.right = right;
        this.hand = new PlayerHand();
    }
    
    //returns player id
    public int getId()
    {
        return id;
    }
    
    //runs a check to see if all the cards in the player Hand are the same as the player preferred value and returns a boolean based on this
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
    
    //returns CardDeck left of the player
    public CardDeck getLeft()
    {
    	return left;
    }
    
    //returns CardDeck right of the player
    public CardDeck getRight()
    {
    	return right;
    }
    
    //returns the players Hand
    public PlayerHand getHand() {
		return hand;
    }
    
    //Creates an inner class
    public class PlayerHand
    {
    	//the ArrayList used to store each players hand
        private ArrayList<Card> hand = new ArrayList<Card>();
        
        //returns the hand list
        public ArrayList<Card> getHandList()
        {
            return hand;
        }
        
        //adds a given card to the player hand
        public synchronized void addToHand(Card card) throws NullPointerException, ArrayIndexOutOfBoundsException
        {
        	if (card == null) {throw new NullPointerException("Card value cannot be Null");}
        	hand.add(card);
        }
        
        //takes a card from the top of the left deck using a Card Deck method , adds the card to player hand and then returns card
        public synchronized Card drawCard()
        {	
        	Card newCard = left.deQueue();
		    this.addToHand(newCard);
			return newCard;
        }
        
        //picks a card from the player hand that isn't a preferred value , removes from player hand and adds to back of the right card deck and returns the card
        public synchronized Card pushCard()
        {
        	//creates a temporary card ArrayList with cards from the player hand that aren't the preferred value
        	ArrayList<Card> tempCardLocation = new ArrayList<Card>();
		    for(int i =0; i<this.hand.size(); i++) {
		        if(this.hand.get(i) != null) {
			        if(this.hand.get(i).getValue() != getId()) {
			        	tempCardLocation.add(hand.get(i));
			        }
		        }
		    }
		    //uses a random number generator  
		    Random rand = new Random();
		    int n = rand.nextInt(tempCardLocation.size());
		    //adds the chosen card to the end of the right card deck
		    right.enQueue(tempCardLocation.get(n));
		    //removes chosen card from player hand
		    hand.remove(tempCardLocation.get(n));
		    //returns the chosen card
		    return tempCardLocation.get(n);
        }
        
        //used to display the card list with the values of the card instead of the card object
        public synchronized ArrayList<Integer> displayHand(){
			ArrayList<Integer> displayHand = new ArrayList<Integer>();
        	for(int i =0;i<this.hand.size();i++) {
        		displayHand.add(this.hand.get(i).getValue());
        	}
        	return displayHand;
        	
        }
    }
}
