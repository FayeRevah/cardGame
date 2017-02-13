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
