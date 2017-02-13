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


import java.awt.Component;
import java.awt.Graphics;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class GUICard
{
    private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
    private static Icon iconBack;
    private static Icon blankIcon;
    static boolean iconsLoaded = false;
    
    static void loadCardIcons()
    {
        // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
        // in a SHORT loop.  For each file name, read it in and use it to
        // instantiate each of the 57 Icons in the icon[] array.
        if (!iconsLoaded)
        {
            int count = 0;
            for (int j = 0; j < 4; j++)
            {
                for (int i = 0; i < 14; i++)
                {
                    String file = "images/" + turnIntIntoCardValue(i) +
                            turnIntIntoCardSuit(j) + ".gif";
                    iconCards[i][j] = new ImageIcon(file);
                }
            }
            iconBack = new ImageIcon("images/BK.gif");
            blankIcon = new ImageIcon("images/BL.gif");
            iconsLoaded = true;
        }
    }

    // turns 0 - 14 into "A", "2", "3", ... "Q", "K", "X"
    static String turnIntIntoCardValue(int k)
    {
        String[] cardValues = { "A", "2", "3", "4", "5", "6", "7", "8",
            "9", "T", "J", "Q", "K", "X" };
        if (k >=0 && k<= 13)
        {
            return cardValues[k];
        }
        return "";
    }

    // turns 0 - 3 into "C", "D", "H", "S"
    static String turnIntIntoCardSuit(int j)
    {
        String[] suites = { "C", "D", "H", "S" };
        if (j >=0 && j<= 3)
        {
            return suites[j];
        }
        return "";
    }
    
    static public Icon getIcon(Card card)
    {        
        return iconCards[Card.valueAsInt(card)][Card.suitAsInt(card)];
    }
    
    static public Icon getBackCardIcon()
    {
        return iconBack;
    }
    
    static public Icon getBlankIcon()
    {
        return blankIcon;
    }
    
}
