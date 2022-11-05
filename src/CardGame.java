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
	    System.out.println("Please enter the number of players:");//Asks for number of players
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
	    createThreads();
	    startThreads();
	    
	    /*
	    System.out.println(Players.get(Players.size()-1).getHand().getHandList());
	    System.out.println(CardDecks.get(CardDecks.size()-1).getDeck());
	    System.out.println(PlayerThreads);
	    System.out.println(PlayerThreads.size());*/
	}
	
	private static void createThreads(){
		for(int i=0; i<numberOfPlayers ;i++) {
			Thread thread = new Thread(new Runnable(){
	            @Override
	            public void run(){
	                for (int i =1; i <=1000000; i++) { //maybe change this to a while loop
	                	
						//((Players.get(i)).getHand()).drawCard();
	                    //will remove null when code changed
	                    //((Players.get(i)).getHand()).pushCard(null);
	                    //(Players.get(i)).checkWin();
					}
	            }
	        });
			PlayerThreads.add(thread);
			}
	}
	
	private static void startThreads() {
		for(int i=0; i<numberOfPlayers;i++) {
			PlayerThreads.get(i).start();
			if(PlayerThreads.get(i).isAlive() == true) {
				System.out.println("Thread " + i + " is Running");
			}
		}
		
	}
	
	//need to check for incorrect pack length and incorrect pack name
	private static ArrayList<Card> packReader(Integer numberOfPlayers) throws IOException{
    	
		//reads file name
    	Scanner myScan2 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the file Name:");
	    String fileName = myScan2.nextLine();  // Read user input
	    
	    //reads file
	    File f = new File("src/" + fileName);
	    FileReader fr = new FileReader(f);
	    BufferedReader reader = new BufferedReader(fr);
	    
	    //creates new card ArrayList
	    ArrayList<Card> Cards = new ArrayList<Card>();
	    
	    //reads lines and adds cards objects to the ArrayList
	    String line;
	    while((line = reader.readLine()) != null) {
	    	Card card = new Card(Integer.parseInt(line));
	    	Cards.add(card);
	    }
	    
	    //checks to see if the pack is the correct length
	    if(Cards.size() != numberOfPlayers *8) {
	    	System.out.println("Incorrect Pack Length");
	    	packReader(numberOfPlayers);
	    }
	    
	    myScan2.close();
	    reader.close();
	    return Cards;
    }
	
	//generates the CardDeck objects and stores in ArrayLists
	private static void generateDecks()
	{
		for (int x = 0; x < numberOfPlayers; x++)
		{
			CardDecks.add(createCardDeck());
		}
	}
	
	//generates the Player Objects , giving each player the CardDeck on the left and right in the topology
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
	
	//Distributes the cards to the players and then to the CardDecks
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
	
	//not sure if we need anything below this
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
