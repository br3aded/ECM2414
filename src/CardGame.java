//All the imports
import java.io.BufferedReader;
import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	//Creates the ArrayList and some variables used within the threads
	private volatile static ArrayList<Player> Players = new ArrayList<Player>();
	private static ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
	private static ArrayList<Thread> PlayerThreads = new ArrayList<Thread>();
	private static Integer numberOfPlayers;
	private static boolean done;
	private static Integer winner;
	private static Integer threadsFinished;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//asks user for the number of player and stores
		Scanner myScan1 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");//Asks for number of players
	    numberOfPlayers = myScan1.nextInt();  // Read user input
	    
	    //generates all the card objects and stores in Cards
	    ArrayList<Card> Cards = packReader(numberOfPlayers);
	    myScan1.close();
	    
	    //Creates all the CardDeck Objects and stores in CardDecks
	    generateDecks();
	    //Creates all the Player Objects and stores within Players , this must be done after generateDecks
	    generatePlayers();
	    //Distributes all the Cards to each Player and CardDeck Object
	    distributeCards(Cards);
	    //This creates all the Player Threads and stores within PlayerThreads
	    createThreads();
	    //This starts all the [layer Threads
	    startThreads();
	}
	
	public ArrayList<Player> getPlayers()
	{
		return Players;
	}
	
	public ArrayList<CardDeck> getCardDecks()
	{
		return CardDecks;
	}
	
	public ArrayList<Thread> getPlayerThreads()
	{
		return PlayerThreads;
	}
	
	public void clearPlayers()
	{
		Players.clear();
	}
	
	public void clearDecks()
	{
		CardDecks.clear();
	}
	
	public void setNumberOfPlayers(int num)
	{
		numberOfPlayers = num;
	}
	
	private static void createThreads() throws IOException{
		//Initialised some values that are used within the threads
		done = false;
	    threadsFinished = 0;
	    //Creates the create number of threads for the number of players
		for(int i=0; i<numberOfPlayers ;i++) {
			//Used to pass each threads it correct Player Value
			final int passThreadCounter = i;
			//Initialises the threads
			Thread thread = new Thread(new Runnable(){
	            @Override
	            //Code for each thread
	            public void run(){
	            	//Passes the Player Value to the thread
	            	int threadCounter = passThreadCounter;
	            	//Creates a new output file for each thread
		            File outputFile = new File("player" + (threadCounter+1) + "_output.txt" );
		            //Writes the initial hand to the player output file
		            try {
		            	FileWriter writer = new FileWriter(outputFile,true);
						writer.write("player " + (threadCounter +1) + " inital hand " + Players.get(threadCounter).getHand().displayHand());
						writer.write("\r\n");
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		            //does an initial check to see if anyone has won and will prevent the game from starting if this is true
		            if (Players.get(threadCounter).checkWin() == true && winner == null) {
		            	winner = threadCounter + 1;
	                	done = true;
	                	System.out.println("Player " + (threadCounter+1) +" has won");
                	}
		            
		            //the game is actually played in this while loop
		            while(!done) { 
		            	try{
		            		//the file writer is opened
		            		FileWriter writer = new FileWriter(outputFile,true);
		            		//this writes to the player output the actions of a draw and also carries out the player draw from the player class
				            writer.write("player " + (threadCounter +1) + " draws a " + Players.get(threadCounter).getHand().drawCard().getValue() + " from deck " + Players.get(threadCounter).getLeft().getId());
				            writer.write("\r\n");
				            //this writes to the player output the actions of a discard and also carries out the player discard from the player class
				            writer.write("player " + (threadCounter +1) + " discards a " + Players.get(threadCounter).getHand().pushCard().getValue() + " to deck " + Players.get(threadCounter).getRight().getId());
				            writer.write("\r\n");
				            //file writer is closed
				            writer.close();
				         } catch (IOException e) {
				            e.printStackTrace();
				         } 
		            	//runs a check after one turn is played if the player has won
				         if ((Players.get(threadCounter)).checkWin() == true) {
				        	 //sets done to true so all the threads will end after they finish there current iteration
				        	 done = true;
				        	 //creates a value to allow all the output files to display the correct winner
				             winner = threadCounter + 1;
				             System.out.println("Player " + (threadCounter+1) +" has won");
			            }
				        //put all the threads to sleep for 10 milliseconds to prevent threads from being 1 iteration ahead of the winning thread
				        try {
							PlayerThreads.get(threadCounter);
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            
		            //this code executes after a winner has been declared
		            
		            //this writes the game finishing outputs to each players output file
		            try {
						FileWriter writer = new FileWriter(outputFile,true);
						writer.write("player " + winner + " has won");
						writer.write("\r\n");
						writer.write("player " + (threadCounter + 1) + " exits");
						writer.write("\r\n");
						writer.write("player " + (threadCounter +1) + " final hand " + Players.get(threadCounter).getHand().displayHand());
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
		            //this checks to see if the current thread is the final thread to finish , if so it generates the deck output files
		            if(threadsFinished == PlayerThreads.size()-1) {
		            	try {
		            		//this generates the deck output files
							deckOutput();
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }else {threadsFinished++ ;} // adds to threadFinished counter if the current thread isn't the last thread to finish
	            }
	            
	        });
			PlayerThreads.add(thread);//adds each thread to the PlayerThreads ArrayList
		}
	}
	
	private static void startThreads() {
		//uses the PlayerThreads array list to start each thread
		for(int i=0; i<PlayerThreads.size();i++) {
			PlayerThreads.get(i).start();
		}
	}
	
	private static ArrayList<Card> packReader(Integer numberOfPlayers) throws IOException{
		//creates a boolean called exists that is used to make sure the file exists
    	boolean exists = false;
    	Scanner myScan2 = new Scanner(System.in); // Create a Scanner object
    	File f = null;
    	//this while loop will run until a valid file is inputed
    	while(!exists) {
		    System.out.println("Please enter the file Name:");
		    String fileName = myScan2.nextLine();  // Read user input
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
    			System.out.println("Pack cannot contain negative values.");
    			//this closes the open scanner and reader
    	    	myScan2.close();
    	    	reader.close();
    	    	//this will run the packReader function again if the pack size isn't valid
    	    	packReader(numberOfPlayers);
    		}
    		
	    	else if (Integer.parseInt(line) >= 0)
	    	{
	    		Card card = new Card(Integer.parseInt(line));
		    	Cards.add(card);
	    	}    	
	    }
	    
	    //checks to see if the pack is the correct length
	    if(Cards.size() != numberOfPlayers *8) {
	    	System.out.println("Incorrect Pack Length");
	    	//this closes the open scanner and reader
	    	myScan2.close();
	    	reader.close();
	    	//this will run the packReader function again if the pack size isn't valid
	    	packReader(numberOfPlayers);
	    }
	    //this closes the open scanner and reader and returns the Cards ArrayList
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
		for (int x = 0; x < CardDecks.size(); x++)
		{
			if (x < CardDecks.size()-1)
			{
				Players.add(createPlayer(CardDecks.get(x), CardDecks.get(x+1)));
			}
			else if (x == CardDecks.size()-1)
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
	}
	
	//this generates a deck output file for all decks when the game has ended
	private static void deckOutput() throws IOException {
		for(int i = 0 ; i< CardDecks.size(); i++) {
			File outputFile = new File("deck"+(i+1)+"_output.txt");
			FileWriter writer = new FileWriter(outputFile,true);
			writer.write("deck " + (i+1) + " contents: " + CardDecks.get(i).displayDeck());
			writer.close();
		}
	}
	
	//used to instantiate CardDeck Objects
    private static CardDeck createCardDeck()
    {
        CardDeck cardDeck = new CardDeck();
        return cardDeck;
    }
    
    //used to instantiate Player Objects
    private static Player createPlayer(CardDeck left, CardDeck right)
    {
        Player player = new Player(left, right);
        return player;
    }
}
