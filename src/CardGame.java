import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
	
	private volatile static ArrayList<Player> Players = new ArrayList<Player>();
	private static ArrayList<CardDeck> CardDecks = new ArrayList<CardDeck>();
	private static ArrayList<Thread> PlayerThreads = new ArrayList<Thread>();
	private static Integer numberOfPlayers;
	private static boolean done;
	private static Integer winner;
	private static Integer threadsFinished;
	
	public static void main(String[] args) throws IOException, InterruptedException {	
		Scanner myScan1 = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Please enter the number of players:");//Asks for number of players
	    numberOfPlayers = myScan1.nextInt();  // Read user input
	    
	    ArrayList<Card> Cards = packReader(numberOfPlayers);
	    myScan1.close();
	    generateDecks();
	    generatePlayers();
	    distributeCards(Cards);
	    done = false;
	    threadsFinished = 0;
	    createThreads();
	    startThreads();
	}
	
	private static void createThreads() throws IOException{
		for(int i=0; i<numberOfPlayers ;i++) {
			final int passThreadCounter = i;
			Thread thread = new Thread(new Runnable(){
	            @Override
	            public void run(){
	            	int threadCounter = passThreadCounter;
		            File outputFile = new File("player" + (threadCounter+1) + "_output.txt" );
		            try {
		            	FileWriter writer = new FileWriter(outputFile,true);
						writer.write("player " + (threadCounter +1) + " inital hand " + Players.get(threadCounter).getHand().displayHand());
						writer.write("\r\n");
						writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		            //does an initial check to see if anyone has won
		            if ((Players.get(threadCounter)).checkWin() == true) {
	                	done = true;
	                	System.out.println("Player " + (threadCounter+1) +" has won");
                	}
		            
		            while(!done) { //maybe change this to a while loop
		            	try{
		            		FileWriter writer = new FileWriter(outputFile,true);
				            writer.write("player " + (threadCounter +1) + " draws a " + Players.get(threadCounter).getHand().drawCard().getValue() + " from deck " + Players.get(threadCounter).getLeft().getId());
				            writer.write("\r\n");
				            writer.write("player " + (threadCounter +1) + " discards a " + Players.get(threadCounter).getHand().pushCard().getValue() + " to deck " + Players.get(threadCounter).getRight().getId());
				            writer.write("\r\n");
				            writer.close();
				         } catch (IOException e) {
				            e.printStackTrace();
				         } 
				         if ((Players.get(threadCounter)).checkWin() == true) {
				        	 done = true;
				             winner = threadCounter + 1;
				             System.out.println("Player " + (threadCounter+1) +" has won");
			            }
		            }
		            
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
		            
		            if(threadsFinished == PlayerThreads.size()-1) {
		            	try {
							deckOutput();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }else {threadsFinished++ ;}
	            }
	            
	        });
			PlayerThreads.add(thread);
		}
	}
	
	private static void startThreads() {
		for(int i=0; i<PlayerThreads.size();i++) {
			PlayerThreads.get(i).start();
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
	
	private static void deckOutput() throws IOException {
		for(int i = 0 ; i< CardDecks.size(); i++) {
			File outputFile = new File("deck"+(i+1)+"_output.txt");
			FileWriter writer = new FileWriter(outputFile,true);
			writer.write("deck " + (i+1) + " contents: " + CardDecks.get(i).displayDeck());
			writer.close();
		}
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
