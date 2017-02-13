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
