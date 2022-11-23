import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CardGameTest {
	
	CardGame game;
	int numberOfPlayers = 4;
	ArrayList<Card> cards = new ArrayList<Card>();
	
	@Before
	public void setupTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{		
		game = new CardGame();
		
		game.setNumberOfPlayers(numberOfPlayers);
		getMethodByName("generateDecks").invoke(game);
		getMethodByName("generatePlayers").invoke(game);

		for (int x = 0; x < numberOfPlayers * 8; x++)
		{
			cards.add(new Card(x));
		}
	}
	
	@After
	public void tearDown()
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
	public void testGeneratePlayers()
	{
		assertEquals(game.getPlayers().size(), numberOfPlayers);
	}
	
	@Test
	public void testGeneratePlayersMaxIndex()
	{
		assertSame(game.getPlayers().get(numberOfPlayers-1).getRight(), game.getCardDecks().get(0));
		assertSame(game.getPlayers().get(numberOfPlayers-1).getLeft(), game.getCardDecks().get(numberOfPlayers-1));
	}
	
	@Test
	public void testGenerateDecks()
	{
		assertEquals(game.getCardDecks().size(), numberOfPlayers);
	}
	
	@Test
	public void testDistributeCardsSize() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{	
		Method method = CardGame.class.getDeclaredMethod("distributeCards", ArrayList.class);

		method.setAccessible(true);
		
		method.invoke(game, cards);
		
		assertTrue(cards.size() == 0);
		
		assertEquals(game.getCardDecks().get(1).getDeck().size(), game.getPlayers().get(1).getHand().getHandList().size());
		
	}
	
	// TODO AUTOMATICALLY TEST ALL FILES 
	@Test
	public void testPackReader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method method = CardGame.class.getDeclaredMethod("packReader", Integer.class);

		method.setAccessible(true);
		
		Object result = method.invoke(method, numberOfPlayers);
		
		ArrayList<Card> tempCards = (ArrayList<Card>) result;
		
		assertEquals(tempCards.size(), 32);
	}

	@Test
	public void testPackReaderNegative() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		
	}
	
	@Test
	public void testCreateThreads() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		getMethodByName("createThreads").invoke(game);
		assertEquals(game.getPlayerThreads().size(), 4);
	}
}
