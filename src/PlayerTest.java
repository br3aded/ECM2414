import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	public Player player;
	@Before
	public void setupTest()
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
	}
	
	@Test
	public void testCheckWinValid()
	{
		// ID == 1
		for(int i =0;i<4;i++)
		{
			player.getHand().addToHand(new Card(player.getId()));
    	}
		
		assertTrue(player.checkWin());
	}
	
	@Test
	public void testCheckWinInvalid()
	{
		for(int i =0;i<3;i++)
		{
			player.getHand().addToHand(new Card(1));
    	}
		
		player.getHand().addToHand(new Card(2));
		
		assertFalse(player.checkWin());
	}

	@Test
	public void testAddCardValid()
	{
		ArrayList<Card> hand = player.getHand().getHandList();
		Card card = new Card(1);
		int oldLength = hand.size();
		
		player.getHand().addToHand(card);
		assertTrue((hand.size() > oldLength && hand.get(hand.size()-1) == card));	
	}

	@Test
	public void testAddCardNull()
	{
		assertThrows(NullPointerException.class, () -> {
    		player.getHand().addToHand(null);
	    });
	}
	
	@Test 
	public void testDrawCardValid()
	{
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
	public void testPushCardValid()
	{
		player.getHand().drawCard();
		assertTrue(player.getHand().pushCard() == player.getRight().getDeck().get(player.getRight().getDeck().size()-1));
	}
	
	@Test
	public void testPushCardPreferredValue()
	{
		int preferred = player.getId();
		player.getHand().addToHand(new Card(preferred));
		
		// Valid positive non-zero integer
		player.getHand().addToHand(new Card(preferred+1));
		player.getHand().pushCard();
		
		// Assert true if remaining card is preferred
		assertEquals(player.getHand().getHandList().get(0).getValue(), preferred);
	}
	
	@Test
	public void testPushCardHandSizeIsZero()
	{
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			player.getHand().pushCard();
	    });
	}
	
	@Test
	public void testDisplayHand()
	{
		int[] expected = {1,1,1,1};
		
		// Test hand size is zero
		assertTrue(player.getHand().displayHand().size() == 0);
		
		for(int i =0;i<4;i++)
		{
			player.getHand().addToHand(new Card(1));
    	}
		
		//
		assertArrayEquals(player.getHand().displayHand().stream().mapToInt(i -> i).toArray(), expected);
	}
}
