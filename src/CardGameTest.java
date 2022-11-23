import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

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
	public void testPackReader() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException
	{
		boolean exists = false;
    	Scanner myScan2 = new Scanner(System.in); // Create a Scanner object
    	File f = null;
    	//this while loop will run until a valid file is inputed
    	while(!exists) {
		    String fileName = "testPack.txt";  // Read user input
		    //reads file
		    f = new File(fileName);
		    //if condition to check the file exists
		    if(f.exists() == true) {
		    	//if file exists breaks the while loop
		    	exists = true;
		    }else {
		    	System.out.println("This is not a valid file");
		    }
    	}
    	
    	//creates a file reader
	    FileReader fr = new FileReader(f);
	    BufferedReader reader = new BufferedReader(fr);
	    
	    //creates new card ArrayList
	    ArrayList<Card> Cards = new ArrayList<Card>();
	    
	    //reads lines and adds cards objects to the ArrayList
	    String line;
	    while((line = reader.readLine()) != null) {	
	    	if (Integer.parseInt(line) >= 0)
	    	{
	    		Card card = new Card(Integer.parseInt(line));
		    	Cards.add(card);
	    	}    	
	    }
	    
	    //this closes the open scanner and reader and returns the Cards ArrayList
	    myScan2.close();
	    reader.close();
		
		assertEquals(this.cards.size(), 32);
	}

	@Test
	public void testPackReaderNegative() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, IOException
	{
		boolean exists = false;
    	Scanner myScan2 = new Scanner(System.in); // Create a Scanner object
    	File f = null;
    	//this while loop will run until a valid file is inputed
    	while(!exists) {
		    String fileName = "negativeTestPack.txt";  // Read user input
		    //reads file
		    f = new File(fileName);
		    //if condition to check the file exists
		    if(f.exists() == true) {
		    	//if file exists breaks the while loop
		    	exists = true;
		    }else {
		    	System.out.println("This is not a valid file");
		    }
    	}
    	
    	//creates a file reader
	    FileReader fr = new FileReader(f);
	    BufferedReader reader = new BufferedReader(fr);
	    
	    //creates new card ArrayList
	    ArrayList<Card> Cards = new ArrayList<Card>();
	    
	    //reads lines and adds cards objects to the ArrayList
	    String line;
	    while((line = reader.readLine()) != null) {	
	    	if (Integer.parseInt(line) < 0)
	    	{
	    		assert(true);
	    	}    	
	    }
	    
	    //this closes the open scanner and reader and returns the Cards ArrayList
	    myScan2.close();
	    reader.close();
	}
	
	@Test
	public void testCreateThreads() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		getMethodByName("createThreads").invoke(game);
		assertEquals(game.getPlayerThreads().size(), 4);
	}
}
