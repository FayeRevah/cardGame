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
*       none
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


import java.util.Arrays;

public class Card
{
    // Suit enum of possible suits for cards

    public enum Suit
    {
        clubs, diamonds, hearts, spades;
    }
    
    public static char[]valuRanks = { 'A', '2', '3', '4', '5', '6', '7', '8',
        '9', 'T', 'J', 'Q', 'K', 'X' };

    // private data members
    private char value;
    private Suit suit;
    private boolean errorFlag;

    // default constructor
    public Card()
    {
        set('A', Suit.spades);
    }

    // overloaded constructor that accepts a value and suit
    // calls on set() method to set the values
    public Card(char value, Suit suit)
    {
        set(Character.toUpperCase(value), suit);
    }

    // if no errors, returns a string with the card value and suit
    // if errorFlag is true, returns an error message
    public String toString()
    {
        if (errorFlag == true)
        {
            return "[Invalid card]";
        }
        return value + " of " + suit;
    }

    // sets the values of the card
    // uses private method isValid() to determine if values are appropriate
    // then sets errorFlag based on return of isValid()
    public boolean set(char value, Suit suit)
    {
        if (isValid(value, suit))
        {
            this.value = value;
            this.suit = suit;
            errorFlag = false;
            return true;
        }
        errorFlag = true;
        return false;
    }

    // accessor for errorFlag
    public boolean getErrorFlag()
    {
        return errorFlag;
    }

    // accessor for suit
    public Suit getSuit()
    {
        return suit;
    }

    // accessor for value 
    public char getValue()
    {
        return value;
    }

    // compares this object to another card. Determines if they are equal
    // based on value and suit
    public boolean equals(Card card)
    {
        if (value == card.value && suit == card.suit)
        {
            return true;
        }
        return false;
    }

    // private method to validate data
    // only checks for validity of value
    private boolean isValid(char value, Suit suit)
    {
        if ((value >= '2' && value <= '9')
                || value == 'A' || value == 'T'
                || value == 'J' || value == 'Q'
                || value == 'K' || value == 'X')
        {
            return true;
        }
        return false;
    }
    
    public static void arraySort( Card cards[], int arraySize )
    {
        Card temp;
        boolean sort = true;
        while (sort)
        {            
            sort = false;
            for ( int i = 0; i < arraySize-1; i++ )
            {
                if ( suitAsInt(cards[i]) > suitAsInt(cards[i+1]) )
                {
                    swapCards(cards, i, i+1);
                    sort = true;
                }
                else if ( suitAsInt(cards[i]) == suitAsInt(cards[i+1]) )
                {
                    if ( valueAsInt(cards[i]) > valueAsInt(cards[i+1]) )
                    {
                        swapCards(cards, i, i+1);
                        sort = true;
                    }
                }
            }
        }
    }
    
    public static int valueAsInt(Card card)
    {
        char testValue = card.value;
        switch ((char)testValue)
        {
            case 'A':
                return 0;
            case '2':
                return 1;
            case '3':
                return 2;
            case '4':
                return 3;
            case '5':
                return 4;
            case '6':
                return 5;
            case '7':
                return 6;
            case '8':
                return 7;
            case '9':
                return 8;
            case 'T':
                return 9;
            case 'J':
                return 10;
            case 'Q':
                return 11;
            case 'K':
                return 12;
            default:
                return 13;
        }
    }
    
    public static int suitAsInt(Card card)
    {
        if (card.suit == Suit.clubs)
            return 0;
        else if (card.suit == Suit.diamonds)
            return 1;
        else if (card.suit == Suit.hearts)
            return 2;
        else 
            return 3;
    }
    
    private static void swapCards( Card[] cards, int one, int two )
    {
        Card temp = cards[one];
        cards[one] = cards[two];
        cards[two] = temp;
    }
    
    public static void arraySortByValue(Card cards[], int arraySize)
    {
        Card temp;
        boolean sort = true;
        while(sort)
        {
            sort = false;
            for(int i = 0; i < arraySize - 1; i++)
            {
               if ( valueAsInt(cards[i]) > valueAsInt(cards[i+1]) )
               {
                    swapCards(cards, i, i+1);
                    sort = true;
               } 
            }
        }
    }
}