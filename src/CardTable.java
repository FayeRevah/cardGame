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

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardTable extends JFrame
{
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

    private int numCardsPerHand;
    private int numPlayers;

    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

    public CardTable(String title, int numCardsPerHand, int numPlayers)
    {
        super(title);
        if (numCardsPerHand <= MAX_CARDS_PER_HAND)
            this.numCardsPerHand = numCardsPerHand;
        if (numPlayers <= MAX_PLAYERS)
            this.numPlayers = numPlayers;
        
        frameInit();
        this.setLayout( new GridLayout(3, 1) );
        
        pnlComputerHand = new JPanel( new GridLayout( 1, numCardsPerHand ) );
        pnlPlayArea = new JPanel( new GridLayout( 2, 2 ) );
        pnlHumanHand = new JPanel( new GridLayout( 1, numCardsPerHand ) );
        
        this.add( pnlComputerHand );
        this.add( pnlPlayArea );
        this.add( pnlHumanHand );
    }
    
    public int getNumCardsPerHand()
    {
        return this.numCardsPerHand;
    }
    
    public int getNumPlayers()
    {
        return this.numPlayers;
    }
    
}
