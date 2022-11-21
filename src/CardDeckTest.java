import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardDeckTest {

	private CardDeck deck;
	@BeforeEach
	void setupTest()
	{
		deck = new CardDeck();
	}
	
	@Test
	void testDeQueueValid()
	{
		Card card = new Card(1);
		deck.enQueue(card);
		assertTrue(deck.deQueue() == card);
	}
	
	@Test
	void testDeQueueNullPointer()
	{
		// DrawCard throws exception and then recurses
		assertThrows(NullPointerException.class, () -> {
    		deck.deQueue();
	    });
	}
	
	@Test
	void testDisplayDeck()
	{
		int[] expected = {1,1,1,1};
		
		for(int i =0;i<4;i++)
		{
			deck.enQueue(new Card(1));
    	}
		
		assertArrayEquals( deck.displayDeck().stream().mapToInt(i -> i).toArray(), expected);
	}
}
