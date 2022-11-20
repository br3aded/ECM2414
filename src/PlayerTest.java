import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

	Player player;
	@BeforeEach
	void setupTest()
	{
		CardDeck left = new CardDeck();
		CardDeck right = new CardDeck();
		player = new Player(left, right);
		
		left.enQueue(new Card(1));
		left.enQueue(new Card(1));
		left.enQueue(new Card(2));
		left.enQueue(new Card(2));
		
		right.enQueue(new Card(1));
		right.enQueue(new Card(1));
		right.enQueue(new Card(2));
		right.enQueue(new Card(2));
		
		System.out.println("Executing setup");
	}
	
	@Test
	void testCheckWinValid()
	{
		System.out.println("Executed test 1");
		// ID == 1
		for(int i =0;i<4;i++)
		{
			player.getHand().addToHand(new Card(player.getId()));
    	}
		
		assertTrue(player.checkWin());
	}
	
	@Test
	void testCheckWinInvalid()
	{
		System.out.println("Executed test 2");
		// ID == 1
		for(int i =0;i<3;i++)
		{
			player.getHand().addToHand(new Card(1));
    	}
		
		player.getHand().addToHand(new Card(2));
		
		assertFalse(player.checkWin());
	}

	@Test
	void testAddCardValid()
	{
		System.out.println("Executed test 3");
		ArrayList<Card> hand = player.getHand().getHandList();
		Card card = new Card(1);
		int oldLength = hand.size();
		
		player.getHand().addToHand(card);
		assertTrue((hand.size() > oldLength && hand.get(hand.size()-1) == card));	
	}

	@Test
	void testAddCardNull()
	{
		assertThrows(NullPointerException.class, () -> {
	    	System.out.println("Executed test 4");
    		
    		player.getHand().addToHand(null);
	    });
	}
	
	@Test 
	void testDrawCardValid()
	{
		System.out.println("Executed test 5");
		player.getHand().drawCard();
		assertTrue((player.getLeft().getDeck().size()) == 3 && 
				   (player.getHand().getHandList().size() == 1));
	}
	
	/*
	@Test
	void testAddCardMoreThan4()
	{
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			System.out.println("Executed test 5");
			ArrayList<Card> hand = player.getHand().getHandList();
			
			for(int i =0;i<10;i++)
			{
				player.getHand().addToHand(new Card(i));
	    	}
	    });
	}*/
	
	@Test
	void testPushCardValid()
	{
		player.getHand().drawCard();
		assertTrue(player.getHand().pushCard() == player.getRight().getDeck().get(player.getRight().getDeck().size()-1));
	}
	
	@Test
	void testPushCardPreferredValue()
	{
		int preferred = player.getId();
		player.getHand().addToHand(new Card(preferred));
		
		// Valid positive non-zero integer
		player.getHand().addToHand(new Card(preferred+1));
		player.getHand().pushCard();
		
		System.out.println(player.getHand().getHandList().get(0).getValue());
		// Assert true if remaining card is preferred
		assertTrue(player.getHand().getHandList().get(0).getValue() == preferred);
	}
	
	@Test
	void testPushCardHandSizeIsZero()
	{
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			player.getHand().pushCard();
	    });
	}
}
