/* ==================================================================
*
*   PROGRAM NAME:
*       Assignment6 - Timed High-Card Game
*
*   Description:
*	
*	>   Change the program into a Model-View-Controller Design Pattern. 
*           
*	>   Add a new part to the High-Card game by putting a timer on the 
*           side of the screen.  It will be on a timer to update every second,  
*           but in order for you to still play the game, you will need to use  
*           multithreading.  (Timer class) 
*           
*	>   Design a new game. 
*           
*	>   Redraw the UML diagram so that it represents your new structure.
*
*   Classes:
*       Card
*
*   Parameters:
*       1. none
*
*   Additional Files:
*
*   Created:
*       2017/02/08
*
*   Author/s:
*       Faiga Revah, Roderick Burkhardt, Oswaldo Minez
*
* ==================================================================*/


public class Hand
{

    public static int MAX_CARDS = 50;
    private Card[] myCards;
    private int numCards;

    // Default Constructor
    public Hand()
    {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
    }

    // Method that removes all cards from the Hand[] array
    public void resetHand()
    {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
    }

    // This method adds cards to the next available position
    public boolean takeCard(Card card)
    {
        if (numCards >= MAX_CARDS)
        {
            return false;
        }
        else
        {
            myCards[numCards++] = new Card(card.getValue(), card.getSuit());
            return true;
        }
    }

    // This method returns and removes the card in the top position of the array
    public Card playCard()
    {
        Card card = myCards[numCards - 1];
        myCards[--numCards] = null;
        return card;
    }
    
    public Card playCard(int cardIndex)
    {
        if ( numCards == 0)
        {
            return new Card('M', Card.Suit.spades);
        }
        Card card = myCards[cardIndex];
        for(int i = cardIndex; i < numCards - 1; i++)
            myCards[i] = myCards[i + 1];
        myCards[--numCards] = null;
        return card; 
    }

    // This method is a stringizer that is used prior to displaying the entire hand
    public String toString()
    {
        String str = "Hand = ( ";
        if (numCards == 0)
        {
            str += "empty hand )";
        }
        else
        {
            for (int i = 0; i < numCards - 1; i++)
            {
                str += myCards[i] + ", ";
            }
            str += myCards[numCards - 1] + " )";
        }
        return str;
    }

    // Getter for numCards
    public int getNumCards()
    {
        return numCards;
    }

    // Accessor to inspect an individual card, returns erroFlag = true if k is bad
    public Card inspectCard(int k)
    {
        Card card;
        if (k >= numCards)
        {
            card = null;//new Card('T', Card.Suit.spades);
        }
        else
        {
            card = myCards[k];
        }
        return card;
    }
    
    public void sort()
    {
        if ( this.getNumCards() != 0 )
            Card.arraySort(myCards, numCards);
    }
    
    public void sortByVal()
    {
        if( this.getNumCards() != 0 )
            Card.arraySortByValue(myCards, numCards);
    }
}
