import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	
	private static ArrayList<Player> Players = new ArrayList<Player>();
	private static ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
	private static ArrayList<Thread> PlayerThreads = new ArrayList<Thread>();
	private static Integer numberOfPlayers;
	
	public static void main(String[] args) throws IOException {		
		Scanner myScan1 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");
	    numberOfPlayers = myScan1.nextInt();  // Read user input
	    
	    ArrayList<Card> Cards = packReader(numberOfPlayers);
	    System.out.println(Cards);
	    myScan1.close();
	    
	    generateDecks();
	    generatePlayers();
	    
	    for (int x = 0; x < CardDecks.size(); x++)
		{
			System.out.println(CardDecks.get(x));
		}
	    
	    System.out.println("|===========|");
	    //System.out.println(Players.get(3).getRight());
	    distributeCards(Cards);
	    System.out.println(Players.get(Players.size()-1).getHand().getHandList());
	    System.out.println(CardDecks.get(CardDecks.size()-1).getDeck());
	    
	}
	
	private static ArrayList<Card> packReader(Integer numberOfPlayers) throws IOException{
    	
    	Scanner myScan2 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the file Name:");
	    String fileName = myScan2.nextLine();  // Read user input
	    
	    File f = new File("src/" + fileName);
	    FileReader fr = new FileReader(f);
	    BufferedReader reader = new BufferedReader(fr);
	    
	    ArrayList<Card> Cards = new ArrayList<Card>();
	    
	    String line;
	    while((line = reader.readLine()) != null) {
	    	Card card = new Card(Integer.parseInt(line));
	    	Cards.add(card);
	    }
	    System.out.println(Cards.size());
	    if(Cards.size() != numberOfPlayers *8) {
	    	System.out.println("Incorrect Pack Length");
	    	packReader(numberOfPlayers);
	    }
	    
	    myScan2.close();
	    reader.close();
	    return Cards;
    }
	
	private static void generateDecks()
	{
		for (int x = 0; x < numberOfPlayers; x++)
		{
			CardDecks.add(createCardDeck());
		}
	}
	
	private static void generatePlayers()
	{
		for (int x = 0; x < numberOfPlayers; x++)
		{
			if (x+1 < numberOfPlayers-1)
			{
				Players.add(createPlayer(CardDecks.get(x), CardDecks.get(x+1)));
			}
			else if (x+1 >= numberOfPlayers-1)
			{
				Players.add(createPlayer(CardDecks.get(x), CardDecks.get(0)));
			}
		}
	}
	
	private static void distributeCards(ArrayList<Card> Cards){
		for(int i=0; i<4;i++) {
			for(int j =0; j<numberOfPlayers;j++) {
				((Players.get(j)).getHand()).addToHand(Cards.get(0));
				Cards.remove(0);
			}
		}
		for(int i=0; i<4;i++) {
			for(int j=0 ; j<numberOfPlayers;j++) {
				(CardDecks.get(j)).enQueue(Cards.get(0));
				Cards.remove(0);
			}
		}
		System.out.println(Cards.size());
	}
	
    private Card createCard(int value)
    {
        Card card = new Card(value);
        return card;
    }

    private static CardDeck createCardDeck()
    {
        CardDeck cardDeck = new CardDeck();
        return cardDeck;
    }

    private static Player createPlayer(CardDeck left, CardDeck right)
    {
        Player player = new Player(left, right);
        return player;
    }

}
