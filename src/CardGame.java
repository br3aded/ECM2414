import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	public static void main(String[] args) throws IOException {
		ArrayList<Card> Cards = new ArrayList<Card>();
		ArrayList<Player> Players = new ArrayList<Player>();
		ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
		
		Scanner myScan1 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");
	    Integer numberOfPlayers = myScan1.nextInt();  // Read user input
	    myScan1.close();
	    
	    BufferedReader pack = getPack(numberOfPlayers);
	
	}
	
	public static BufferedReader getPack(Integer numberOfPlayers) throws IOException{
    	
    	Scanner myScan2 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the file Name:");
	    String fileName = myScan2.nextLine();  // Read user input
	    myScan2.close();
	    
	    int lines = 0;
	    BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(fileName));
	 	    while (reader.readLine() != null) lines++;
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    if(lines != numberOfPlayers *8) {
	    	getPack(numberOfPlayers);
	    }
		return reader;
    }
	
    private Card createCard(int value)
    {
        Card card = new Card(value);
        return card;
    }

    private CardDeck createCardDeck(int value)
    {
        CardDeck cardDeck = new CardDeck();
        return cardDeck;
    }

    private Player createPlayer(int value)
    {
        Player player = new Player();
        return player;
    }

}
