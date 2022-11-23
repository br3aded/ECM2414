import static org.junit.jupiter.api.Assertions.*;
import junit.runner.Version;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CardDeckTest {

	private CardDeck deck;
	@Before
	public void setupTest()
	{
		deck = new CardDeck();
		
		

		System.out.println("JUnit version is: " + Version.id());
	}
	
	@Test
	public void testDeQueueValid()
	{
		Card card = new Card(1);
		deck.enQueue(card);
		assertTrue(deck.deQueue() == card);
	}
	
	@Test
	public void testDeQueueNoCardsInDeck()
	{
		// DrawCard throws exception and then recurses
		assertThrows(NullPointerException.class, () -> {
    		deck.deQueue();
	    });
	}
	
	@Test
	public void testDisplayDeck()
	{
		int[] expected = {1,1,1,1};
		
		for(int i =0;i<4;i++)
		{
			deck.enQueue(new Card(1));
    	}
		
		assertArrayEquals( deck.displayDeck().stream().mapToInt(i -> i).toArray(), expected);
	}
}
