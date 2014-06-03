SuperFinalProject
=================

The 3rd time trying this shit


Got the Bet Class added. Beginning stages of Final Project

Stages 2 and 3 are complete. Just a little tinkering left to make it perfect.


This program makes a gmae of blackjackwith 2 players.
Can bet money, win money, lose money.
You can get a hit (another card) or stay.
You win if you get 21 or stay with a higher score than the other player.
You lose if you go over 21 or syat with a lower score than the other player.
If both scores are the same the bet ammounts are set back to $0 and players play again with tie breaker round.
After winner/loser you are asked if you want to play again.

Made one deck of cards for both players and not have the players get the same cards.

  -the deal() has a boolean called inPlay to show if card is in play.

Made Aces worth 1 or 11 so if dealed two Aces you don't bust.

  -if playerHand is higher than 11 aces are assigned a value of 1. Used a foundAce variable

Made players win money with 21 or higher score and added money to their pot.

  -Nested Bet Class in Player Class and put winBet()
  
Made players lose money with score over 21 or lower score and subtracted money to their pot.

  -Nested Bet Class in Player Class and put winBet()
  
Made tie breaker round if both players have same score.

  -if statement in main() comparing gameOverValue to initiate tie breaker
  
