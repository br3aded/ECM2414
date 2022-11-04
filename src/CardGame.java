import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	public static void main(String[] args) throws IOException {
		ArrayList<Player> Players = new ArrayList<Player>();
		ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
		
		Scanner myScan1 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");
	    Integer numberOfPlayers = myScan1.nextInt();  // Read user input
	    
	    ArrayList<Card> Cards = packReader(numberOfPlayers);
	    System.out.println(Cards);
	    myScan1.close();
	}
	
	public static ArrayList<Card> packReader(Integer numberOfPlayers) throws IOException{
    	
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
