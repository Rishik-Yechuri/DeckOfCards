import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DeckOfCards {
    public static void main(String[] args) {
        try
        {
            DeckOfCards obj = new DeckOfCards();
            obj.run (args);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
    public void run(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select Number of players:");
        int numOfPlayers = scanner.nextInt();
        BlackJack blackJackGame = new BlackJack(numOfPlayers);
        blackJackGame.startGame();
    }
}
class Card{
    public int cardValue;
    public boolean isAce;
    public boolean isFace;
    public String suite;
    public Card(int cardValue,String suite){
        this.cardValue = cardValue;
        this.suite = suite;
        isAce = false;
        if(cardValue == 1){
            isAce = true;
        }
        isFace = false;
        if(cardValue >= 11){
            isFace  = true;
        }
    }
}
class Deck{
    public ArrayList<Card> holdCards;
    public Deck(){
        holdCards = new ArrayList<Card>();
        for(int x=1;x<=13;x++){
            holdCards.add(new Card(x,"diamond"));
            holdCards.add(new Card(x,"heart"));
            holdCards.add(new Card(x,"spade"));
            holdCards.add(new Card(x,"club"));
        }
        Collections.shuffle(holdCards);
    }
    public Card getTopCard(){
        return holdCards.remove(0);
    }
    public Card getBottomCard(){
        return holdCards.remove(holdCards.size()-1);
    }
    public void shuffleDeck(){
        Collections.shuffle(holdCards);
    }
}
class BlackJack{
    public int highestScore;
    public int indexOfHighestScore;
    public boolean isTied;
    public int numOfPlayers;
    public Deck deck;
    public ArrayList<ArrayList<Card>> playersCards;
    public BlackJack(int numOfPlayers){
        highestScore = 0;
        indexOfHighestScore = -1;
        isTied = false;
        this.numOfPlayers = numOfPlayers;
        deck = new Deck();
        playersCards = new ArrayList<ArrayList<Card>>();
        for(int x=0;x<numOfPlayers;x++){
            playersCards.add(new ArrayList<Card>());
        }
    }
    public void startGame(){
        Scanner scanner = new Scanner(System.in);
        for(int x=0;x<numOfPlayers;x++){
            boolean keepGoing = true;
            boolean hasAce = false;
            int playerScore = 0;
            System.out.println("Player " + (x+1) + "'s turn(select 'hit' or 'stop')");
            while(playerScore <= 21 && keepGoing){
                String action = scanner.nextLine();
                if(action.equals("hit")){
                    int cardVal = deck.getTopCard().cardValue;
                    if(cardVal == 1){
                        System.out.println("Ace Picked");
                        hasAce = true;
                    }else if(cardVal > 10){
                        if(cardVal == 11){
                            System.out.println("Jack Picked");
                        }
                        if(cardVal == 12){
                            System.out.println("Queen Picked");
                        }
                        if(cardVal == 13){
                            System.out.println("King Picked");
                        }
                    }else{
                        System.out.println(cardVal + " Picked");
                    }
                    playerScore+=cardVal > 10 ? 10 : cardVal;
                    if(playerScore > 21){
                        System.out.println("You went bust with " + playerScore);
                    }
                    else if(hasAce && playerScore <= 11){
                        System.out.println("Hand Value(Ace as 11):" + (playerScore+10));
                    }else{
                        System.out.println("Hand Value:" + playerScore);
                    }
                }else if(action.equals("stop")){
                    if(hasAce && playerScore <= 11){
                        playerScore+=10;
                    }
                    System.out.println("Final Hand Value:" + playerScore);
                    keepGoing = false;
                    if(playerScore > highestScore){
                        highestScore = playerScore;
                        indexOfHighestScore = x;
                        isTied = false;
                    }else if(playerScore == highestScore){
                        isTied = true;
                    }
                }
            }
        }
        if(isTied){
            System.out.println("Game Tied");
        }else if(indexOfHighestScore == -1){
            System.out.println("Game Tied");
        }else{
            System.out.println("Player " + (indexOfHighestScore+1) + " won");
        }
    }
}