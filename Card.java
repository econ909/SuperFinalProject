
/**
 * Write a description of class Card here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card  {
    private int suit;
    private int rank;
    
    private static final String[] SUITS = { "Clubs", "Diamonds", "Hearts", "Spades" };
    private static final String[] RANKS = { "0", "1", "2", "3", "4", "5", "6",
                   "7", "8", "9", "10", "Ace" ,"Jack", "Queen", "King" };
    
                   
    public Card() { 
        this(0, 0);
    }
    
    public Card(int suit, int ranking) { 
        this.suit = suit;
        this.rank = ranking;
    }
    
    public int getSuit()  {
       return this.suit;
    }
    
    public int getRank()  {
        return this.rank;
    }
    
    /**
     * Converts Card object into readable form.
     */
    @Override
    public String toString() {
        return RANKS[rank] + " of " + SUITS[suit];
    }
    
    /**
     * Return true if the cards are equivalent to each other.
     */
    @Override
    public boolean equals(Object obj) {
      boolean b = false;
      if (obj instanceof Card) {
        Card that = (Card) obj;
        b = this.compareTo(that) == 0;
      }
      return b;
      
    }
    
    
    /**
     * Compares two cards: returns 1 if the first card is greater
     * -1 if the seconds card is greater, and 0 if they are the equivalent.
     */
    public int compareTo(Card that) {
      // first compare the suits
      if (this.suit > that.suit) return 1;
      if (this.suit < that.suit) return -1;
      
      // compare the ranks
      int rank1 = (this.rank + 11) % 13;
      int rank2 = (that.rank + 11) % 13;
      
      if (rank1 > rank2) return 1;
      if (rank2 < rank1) return -1;
      return 0;
    }
    
    public static void printCard(Card card) {
      System.out.println(card);
    }

}

