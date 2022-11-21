import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

class CardGameTest {
	
	int numberOfPlayers = 4;
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<CardDeck> cardDecks = new ArrayList<CardDeck>();
	ArrayList<Card> cards = new ArrayList<Card>();
	
	@BeforeEach
	void setupTest() throws NoSuchMethodException
	{
		for (int x = 0; x < numberOfPlayers; x++)
		{
			cardDecks.add(new CardDeck());
		}
		
		for (int x = 0; x < cardDecks.size(); x++)
		{
			if (x < cardDecks.size()-1)
			{
				players.add(new Player(cardDecks.get(x), cardDecks.get(x+1)));
			}
			else if (x == cardDecks.size()-1)
			{
				players.add(new Player(cardDecks.get(x), cardDecks.get(0)));
			}
		}
		
		for (int x = 0; x < numberOfPlayers * 8; x++)
		{
			cards.add(new Card(x));
		}
	}
	
	private Method getMethodByName(String methodName) throws NoSuchMethodException 
	{
		Method method = CardGame.class.getDeclaredMethod(methodName);
	    method.setAccessible(true);
	    return method;
	}
	
	@Test
	void testGeneratePlayers()
	{
		assertEquals(players.size(), numberOfPlayers);
	}
	
	@Test
	void testGeneratePlayersMaxIndex()
	{
		assertSame(players.get(numberOfPlayers-1).getRight(), cardDecks.get(0));
		assertSame(players.get(numberOfPlayers-1).getLeft(), cardDecks.get(numberOfPlayers-1));
	}
	
	@Test
	void testGenerateDecks()
	{
		assertEquals(cardDecks.size(), numberOfPlayers);
	}
}
