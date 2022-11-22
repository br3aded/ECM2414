import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class CardGameTest {
	
	CardGame game;
	int numberOfPlayers = 4;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	@BeforeEach
	void setupTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		game = new CardGame();
		
		game.setNumberOfPlayers(numberOfPlayers);
		getMethodByName("generateDecks").invoke(game);
		getMethodByName("generatePlayers").invoke(game);

		for (int x = 0; x < numberOfPlayers * 8; x++)
		{
			cards.add(new Card(x));
		}
		
		System.out.println(game);
		System.out.println(game.getCardDecks().size());
		System.out.println(game.getPlayers().size());		
	}
	
	@AfterEach
	void cleanupTest()
	{
		game.clearDecks();
		game.clearPlayers();
		cards.clear();
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
		assertEquals(game.getPlayers().size(), numberOfPlayers);
	}
	
	@Test
	void testGeneratePlayersMaxIndex()
	{
		assertSame(game.getPlayers().get(numberOfPlayers-1).getRight(), game.getCardDecks().get(0));
		assertSame(game.getPlayers().get(numberOfPlayers-1).getLeft(), game.getCardDecks().get(numberOfPlayers-1));
	}
	
	@Test
	void testGenerateDecks()
	{
		assertEquals(game.getCardDecks().size(), numberOfPlayers);
	}
	
	@Test
	void testDistributeCardsSize() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{	
		Method method = CardGame.class.getDeclaredMethod("distributeCards", ArrayList.class);

		method.setAccessible(true);
		
		method.invoke(game, cards);
		
		assertTrue(cards.size() == 0);
		
		assertEquals(game.getCardDecks().get(1).getDeck().size(), game.getPlayers().get(1).getHand().getHandList().size());
		
	}
	
	
}
