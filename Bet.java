
/**
 * Write a description of class Bet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bet  {
    
    private int dollars;
    private int pot;
    private static final int ANNIE_BET = 10;
    
    public Bet()  {
        this(ANNIE_BET, 100);
        
    }
    
    public Bet(int money, int pot)  {
        this.dollars = money;
        this.pot = pot;
    }
    
    @Override
    public String toString() {
      return "$ " + dollars + " out of " + pot;
    }
    
    public int loseBet()  {
        pot -= dollars;
        return pot;
    }
    
    public int winBet()  {
        pot += dollars;
        return pot;
    }
}

