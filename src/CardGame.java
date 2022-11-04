import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	public static void main(String[] args) {
		ArrayList<Card> Cards = new ArrayList<Card>();
		ArrayList<Player> Players = new ArrayList<Player>();
		ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");
	    String numberOfPlayers = myObj.nextLine();  // Read user input
	
	
	
	
	
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
