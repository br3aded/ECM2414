public class Player {
    private static int counter;
    private int id;
    private int handSize = 4;
    private Card[] hand = new Card[handSize];

    public Player()
    {
        this.id = ++counter;
    }

    public int getId()
    {
        return id;
    }

    public Card[] getHand()
    {
        return hand;
    }

    private void drawCard()
    {
        // Assign previous deck;
        // hand.add(previousDeck.deQueue());
    }

    private void pushCard(Card card)
    {
        // Assign next deck;
        // nextDeck.enQueue(hand.pop(card));
    }

    private void checkWin()
    {
        // If win: stop all threads and end game.
    }
}
