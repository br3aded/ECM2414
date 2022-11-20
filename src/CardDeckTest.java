import static org.junit.jupiter.api.Assertions.*;

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
		deck.displayDeck();
	}
}
