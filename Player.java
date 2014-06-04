import java.util.Scanner;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    protected Bet bet = new Bet();  
    private int score;
    //private static Card [] deck = BlackJack.makeDeck();

    public Player()  {
        this(0);
    }

    public Player(int score)  {
        this.score = score;
    }

    public int getScore()  {
        return this.score;
    }
    public Bet getBet()  {
        return this.bet;
    }
    
    public void setScore(int totalScore)  {
        this.score = totalScore;
    }

    public int playerHand(int i) {

        //*======= Problem 5 Playing one hand ============
        //a) Create a player's hand (an array of Cards called hand)
        //   Since we don't know how many cards we may want to receive,
        //   make the size of the array at least 10.
        //   Use a for loop to initialize all the cells of the array
        //   with a default Card object
        Card[] hand = new Card[10];

        for (int k = 0; k < hand.length; k++)  {
            hand[k] = new Card();
        }

        //b) Also declare an integer index and set it to 0. This will
        //   keep track of the index where the next card you receive
        //   will be stored in the array.
        int index = 0;
        //c) As an example of what we are doing, execute these statements
        hand[index] = BlackJack.deal(BlackJack.getDeck());
        index++;
        hand[index] = BlackJack.deal(BlackJack.getDeck());
        index++;
        BlackJack.printDeck(hand, index);
        System.out.println("The score of Player "  + (i+1) + " hand is " + BlackJack.handScore(hand, this.score));
        System.out.println();
        return BlackJack.handScore(hand,this.score);

    }

    public class Bet  {

        private int dollars;
        private int pot;
        private static final int ANTE_BET = 10;
        //private static final int INVAILD_BET = -1; 

        public Bet()  {
            this(ANTE_BET, 100);

        }

        public Bet(int money, int pot)  {
            this.dollars = money;
            this.pot = pot;
        }

        public int getBet()  {
            return this.dollars;
        }

        public void setBet(int dollars)  {
            this.dollars = dollars; 
        }

        public int getPot()  {
            return this.pot;
        }

        public void setPot(int bank)  {
            this.pot = bank;
        }

        @Override
        public String toString() {
            return "$" + dollars;
        }

        public int firstBet()  {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("You have $" + pot + " to play with");
            System.out.print("Choose a bet between $1 - $10:  ");
            
            this.dollars = keyboard.nextInt();
            System.out.println();
            if (dollars < 1 || dollars > 10)  {
                System.out.println("Incorrect bet ammount!");
                System.out.println();
                dollars = firstBet();
                
            }
            if (dollars > pot)  {
                System.out.println("You don't have enough money left to bet that much, DUMB ASS!");
                System.out.println();
                dollars = firstBet();
            }
            return dollars;
        }

        public int remainBet(int playerNum)  {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Player " + (playerNum + 1) + ": Bet $0 - $10: ");
            int bet = keyboard.nextInt();
            if (bet < 0 || bet > 10)  {
                System.out.println("Incorrect bet ammount!");
                bet = remainBet(playerNum);
            }
            if (bet + dollars > pot)  {
                System.out.println("Your pot is $" + pot + " and you've bet $" + (bet + dollars) + ". Can't bet more money than you have, Dumb Ass!  ");
                bet = remainBet(playerNum);
            }
            dollars += bet;
            return dollars;
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

}

