import java.util.Scanner;
/**
 * Write a description of class BlackJack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlackJack {
    public final static int TOTAL_PLAYERS = 2;
    private static int numPlayersStay = 0;
    private static Card [] deck = makeDeck();
    
    public static Card[] getDeck()  {
        return deck;
    }
    
    public static void main(String[] args) {
        Player [] players = makePlayers(TOTAL_PLAYERS);

        System.out.print("\f");
        int initialBet = 0;
        newGame:
        while (true)  { 
            numPlayersStay = 0;
            if (initialBet == 0)  { // skips opening bet if a tie breaker is needed
                for (int i = 0; i < players.length; i++)  {
                    System.out.println("Player " + (i+1) + ":" );
                    players[i].bet.firstBet(/*initialBet*/); //must schange the firstBet to have while loop instead of recursion
                }
            }
            for (int i = 0; i < players.length; i++)  {
                deal(deck);
                players[i].setScore(players[i].playerHand(i));
                //System.out.println ("Player " + (i+1) + " score = " + players[i].getScore());
            }

            // Check to see if any players got immediate Blackjack:
            for (int i = 0; i < players.length; i++) {
                if (players[i].getScore() == 21) {
                    System.out.println("Player " + (i+1) + " got BLACKJACK!");
                    //System.out.println("Player " + (i+1) + " current score and bet: " + players[i].getScore() + " " + players[i].bet);
                    players[i].bet.winBet();
                    for (int k = 0; k < TOTAL_PLAYERS; k++)  {
                        if (k != i) {
                            players[k].bet.loseBet();
                        }
                    }
                    continue newGame;
                }            
            }

            // CHANGE BACK TO 'for' loop and get rid of 'while' loop
            //for (int i = 0; i < players.length; i++)  { 

            //System.out.println("Player " + (i+1) + ":");

            for (int i = 0; i < players.length; i++)  {
                //System.out.println("Before startGame(): Player " + (i+1) + "'s" + " score = " + players[i].getScore());
                int gameOverValue = startGame(players, i);
                if (gameOverValue >= 21 && gameOverValue < 100) break; // somebody blackjacked or busted
                if (gameOverValue < 0)   break; // everybody stayed...
                if (gameOverValue == 0)  continue;  // only one person stayed so next player's turn
                if (gameOverValue >= 100) {
                    initialBet = gameOverValue - 100;  // carry over the last bet since new Game will be tie-breaker
                    
                    continue newGame;
                }
            }
            //System.out.println("Player " + (i+1) + ":");
            //if (i >= players.length) {
            
            System.out.println("Play Again? (Y/N) ");
            Scanner keyboard = new Scanner(System.in);
            String answer = keyboard.next();
            System.out.println();
            if(answer.equalsIgnoreCase("N"))  {
                System.err.println("GAME OVER");
                System.exit(0);
            }
            initialBet = 0;  // brand new Game's initial bet will be zero
            for(int i = 0; i < deck.length; i++)  {
                deck[i].setInPlay(false);
            }
                
        }
        //System.out.println("Player " + (i+1) + ":");

    }

    public static int startGame(Player [] players, int i)  {

        while (players[i].getScore() <= 21)  {
            if (players[i].getScore() == 21)  {
                System.out.println("Player " + (i+1) + " got BLACKJACK!");
                System.out.println();
                //System.out.println("Player " + (i+1) + " current score and bet: " + players[i].getScore() + " " + players[i].bet);
                players[i].bet.winBet();
                for(int k = 0; k < TOTAL_PLAYERS; k++)  {
                    if(k != i) {
                        players[k].bet.loseBet();
                    }
                }
                return players[i].getScore(); //player.bet.getPot();
            }
            players[i].bet.remainBet(i);
            int nextCard = hitMe(players[i]);
            if(nextCard < 0)  {  // player chose to Stay instead of Hit
                ++ numPlayersStay;
                if(numPlayersStay >= TOTAL_PLAYERS)  {
                    int maxPlayerIndex = -1;
                    int maxScore = -1;
                    boolean tiedScore = false;
                    for(int k = 0; k < TOTAL_PLAYERS; k++){  
                        if (players[k].getScore() > maxScore) {
                            maxScore = players[k].getScore();
                            maxPlayerIndex = k;
                            tiedScore = false;
                        }
                        else if (players[k].getScore()== maxScore) {
                            tiedScore = true;
                        }

                    }
                    if (tiedScore) {
                        System.out.println ("Both scores are the same! All bets are reset to $0! Tie Breaker Round!!");
                        System.out.println();
                        return players[i].getBet().getBet() + 100;  // need to increment last bet by 100 in case was zero bet
                    }                                    // (tie-breaker logic in main program loop will decrement)
                    else {
                        System.out.println ("Player " + (maxPlayerIndex+1) + " wins with a score of " + maxScore);
                        players[maxPlayerIndex].bet.winBet();
                        for(int k = 0; k < TOTAL_PLAYERS; k++){
                            if(k != maxPlayerIndex)  {
                                players[k].bet.loseBet(); 
                            }
                        }
                        // ISNT WORKING YET!
                        // ALL players have stayed, so return a negative value
                        // IF Tie need to play again automatically and print alert
                        return -1;
                    }
                }
                // player chose to stay, but not all players have stayed yet...
                return 0; //nextCard; // -player.bet.getPot(); 
            }
            else {
                // player chose to get hit
                players[i].setScore(nextCard + players[i].getScore());
                System.out.println("Player " + (i+1) + " current score and bet: " + players[i].getScore() + " " + players[i].bet);  
            } 
        }

        players[i].bet.loseBet();

        for (int k = 0; k < TOTAL_PLAYERS; k++)  {
            if (k != i) {
                players[k].bet.winBet();
            }
        }
        
        
        System.out.println("Player " + (i+1) + " has BUSTED!!");

        
        //System.out.println("Your current score and bet: " + score + " " + bet);
        return players[i].getScore();  // return the player's score who went over 21
    }

    public static Player [] makePlayers(int numPlayers)  {
        Player[] players = new Player[TOTAL_PLAYERS];
        for(int i = 0; i < numPlayers; i++) {
            players[i] = new Player();

        }
        return players;
    }

    public static Card [] makeDeck()  {
        int index = 0;
        Card[] cards = new Card[52];
        for(int suit = 0; suit <= 3; suit++) {
            for(int rank = 1; rank <= 13; rank++) {
                cards[index] = new Card(suit, rank);
                index++;
            }
        }
        return cards;
    }

    // Define method printDeck here

    public static void printDeck(Card[] cards) {
        for (int i = 0; i < cards.length; i++)  {
            Card.printCard(cards[i]);
        }
    }

    public static void printDeck(Card[] cards, int index) {
        for (int i = 0; i < index; i++)  {
            Card.printCard(cards[i]);

        }
    }
    // Define method handScore here

    public static int handScore(Card[] cards, int scoreSoFar)  {
        int foundAce = 0;
        int score = 0;       
        for (int i = 0; i < cards.length; i++)  {
            if (cards[i].getRank() == 1) {
                ++foundAce;
            }

            else if (cards[i].getRank() > 10) {
                score += 10;
            }

            else if (cards[i].getRank() <= 10)  {
                score += cards[i].getRank();
            }
            else {
                System.err.println ("BUG in handScore()");
            }
        }
        if (foundAce > 0) {  // && player.getScore() >= 11)  {

            if (foundAce == 1) {

                if (scoreSoFar >= 11)
                    score += 1;
                else
                    score += 11;
            }
            else  // foundAce = 2
                score = 12; // Special case on initial hand only
            //foundAce = 0;    
        }

        return score;
    }

    public static int hitMe(Player player)  {
        Card[] card = new Card[1];  // declare Card as a single-element one-dimensional array (reserves space for array)
        card[0] = new Card();       // declare actual element of the single-element array
        String answer;
        do {
            System.out.println("Hit or Stay? (type 'h' for hit Or 's' to stay) : ");
            Scanner keyboard = new Scanner(System.in);
            answer = keyboard.next();
            System.out.println();
            if(answer.equalsIgnoreCase("h"))  {
                card[0] = deal(deck);
                printDeck(card,1);
                return handScore(card, player.getScore());
            }
            else if(answer.equalsIgnoreCase("s"))  {
                return -1;

            }

            System.out.println("PLEASE CHOOSE 'h' or 's' ONLY!");
            System.out.println();
            
        } while (!answer.equalsIgnoreCase("h") && !answer.equalsIgnoreCase("s"));
        return -1;
    }
    // Define method deal here
    public static Card deal(Card[] cards)  {
        int z = (int)(Math.random() * 52);
        while(cards[z].getInPlay())  {
            z = (int)(Math.random() * 52);
        }
        cards[z].setInPlay(true);
        return cards[z];
    }
}

