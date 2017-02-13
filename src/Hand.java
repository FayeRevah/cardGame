/* ==================================================================
*
*   PROGRAM NAME:
*       Assignment5 - GUI Cards
*
*   Description:
*	Understand the Classes and Problem
*	
*	We wish to move our Card classes from the realm of console apps to 
*	that of GUI apps.  We'll do this in stages.
*	
*	>   Read and display Card pictures - Read .gif image files as Icons, 
*	and attach them to JLabels that we can display on a JFrame.
*	
*	>   Encapsulate the Card Icons in a class GUICard - Once we debug 
*	imagery for cards, above, we can move it into its own class, GUICard.
*	
*	>   Create a CardTable class - This JFrame class will embody the 
*	JPanels and Layout(s) needed for our application. This is where all 
*	the cards and controls will be placed. 
*	
*	>   Use a CardGameFramework class - Use an already created class to 
*	deal cards for display from an actual deck.
*	
*	>Create the game "High-Card"
*	
*	The first phase (item 1) will allow you to debug the problem of reading 
*	the .gif files and displaying them on a JFrame without any excess logic 
*	or class complexity.  The second phase (items 2 and 3) will let you 
*	turn what you did in the first phase into a multi-class project.  The 
*	final phase (items 4 and 5) will add the CardGameFramework class so 
*	that your card tools can be combined with your GUI tools to create a 
*	GUI program that has real computational power for a GUI card game, 
*	"High Card".
*
*   Classes:
*       None
*
*   Parameters:
*       1. none
*
*   Additional Files:
*
*   Created:
*       2017/02/01
*
*   Author/s:
*       Faiga Revah, Oswaldo Minez, Roderick Burkhardt
*
* ==================================================================*/


package assignment5;

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
