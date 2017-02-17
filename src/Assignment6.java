/* ==================================================================
*
*   PROGRAM NAME:
*       Assignment6 - BUILD Game
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
*       Card, Hand, Deck, GUICard, CardTable extends JFrame, CardGameFramework,
*       counter
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

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Assignment6
{

    static CardTable myCardTable;
    static int NUM_CARDS_PER_HAND = 7;
    static int NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[56];
    static JLabel[] humanLabels = new JLabel[56];
    static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
    static Counter timerCounter;
    static JButton cannotPlayButton;
    static CardGameFramework highCardGame;
    static Hand[] cardStacks = new Hand[2];
    static int[] cannotPlayCount = new int[NUM_PLAYERS];
    static int[] noMoves = new int[NUM_PLAYERS];
    static int playerCardToPlay;
    static int currentPlayer;
    static CardClickListener clickListener;

    public static void main (String[] args)
    {
        GUICard.loadCardIcons();

        // Create CardGameFramework
        int numPacksPerDeck = 1;
        int numJokersPerPack = 0;
        int numUnusedCardsPerPack = 0;
        Card[] unusedCardsPerPack = null;

        highCardGame = new CardGameFramework(numPacksPerDeck,
                numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
                NUM_PLAYERS, NUM_CARDS_PER_HAND);

        highCardGame.deal();
        cardStacks[0] = new Hand();
        cardStacks[1] = new Hand();

        myCardTable
                = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(800, 600);
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCardTable.setVisible(true);

        int playOrNot = JOptionPane.showConfirmDialog(null,
                "Ready to play BUILD?", "", JOptionPane.YES_NO_OPTION);
        if ( playOrNot == JOptionPane.YES_OPTION )
        {
            buildPanels();
            for (int item : noMoves)
                item = 0;
            timerCounter.activateTimer();
        }
    }

    public static void buildPanels ()
    {
        int k;
        Icon tempIcon;

        clickListener = new CardClickListener();
        timerCounter = new Counter();

        for ( k = 0; k < NUM_CARDS_PER_HAND; k++ )
        {
            computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
            if ( highCardGame.getHand(1).inspectCard(k) == null )
            {
                tempIcon = GUICard.getBlankIcon();
            }
            else
            {
                tempIcon = GUICard.getIcon(highCardGame.getHand(1).inspectCard(k));
            }
            humanLabels[k] = new JLabel(tempIcon);
            humanLabels[k].addMouseListener(clickListener);
        }

        for ( k = 0; k < NUM_PLAYERS; k++ )
        {
            playLabelText[k] = new JLabel("Stack " + k, JLabel.CENTER);
            cannotPlayCount[k] = 0;
            cardStacks[k].takeCard(highCardGame.getCardFromDeck());
            playedCardLabels[k] = new JLabel(GUICard.getIcon(cardStacks[k].inspectCard(0)),
                    JLabel.CENTER);
            playedCardLabels[k].addMouseListener(clickListener);
        }

        playerCardToPlay = -5;
        currentPlayer = 1;

        cannotPlayButton = new JButton("I cannot play");
        cannotPlayButton.addMouseListener(clickListener);

        // ADD LABELS TO PANELS -----------------------------------------
        for ( k = 0; k < NUM_CARDS_PER_HAND; k++ )
        {
            myCardTable.pnlComputerHand.add(computerLabels[k]);
            myCardTable.pnlHumanHand.add(humanLabels[k]);
        }

        myCardTable.pnlPlayArea.add(playedCardLabels[0]);
        myCardTable.pnlPlayArea.add(timerCounter);
        myCardTable.pnlPlayArea.add(playedCardLabels[1]);
        myCardTable.pnlPlayArea.add(playLabelText[0]);
        myCardTable.pnlPlayArea.add(cannotPlayButton);
        myCardTable.pnlPlayArea.add(playLabelText[1]);

        timerCounter.startCounter();

        myCardTable.setVisible(true);
        myCardTable.repaint();

    }

    public static void updateHumanPanel ()
    {
        highCardGame.getHand(1).takeCard(highCardGame.getCardFromDeck());
        myCardTable.pnlHumanHand.removeAll();
        for ( int k = 0; k < highCardGame.getHand(1).getNumCards(); k++ )
        {

            humanLabels[k] = new JLabel(GUICard.getIcon(highCardGame.getHand(1).inspectCard(k)));         
            humanLabels[k].addMouseListener(clickListener);
            myCardTable.pnlHumanHand.add(humanLabels[k]);
        }
        myCardTable.pnlHumanHand.revalidate();
    }

    public static void updatePlayArea ()
    {
        for ( int i = 0; i < cardStacks.length; i++ )
        {
            playedCardLabels[i].setIcon(GUICard.getIcon(cardStacks[i].inspectCard(cardStacks[i].getNumCards() - 1)));
            playedCardLabels[i].revalidate();
        }
    }

    public static class CardClickListener implements MouseListener
    {

        public void mouseClicked (MouseEvent event)
        {
            if ( event.getSource() == humanLabels[0] )
            {
                playerCardToPlay = 0;
            }
            else if ( event.getSource() == humanLabels[1] )
            {
                playerCardToPlay = 1;
            }
            else if ( event.getSource() == humanLabels[2] )
            {
                playerCardToPlay = 2;
            }
            else if ( event.getSource() == humanLabels[3] )
            {
                playerCardToPlay = 3;
            }
            else if ( event.getSource() == humanLabels[4] )
            {
                playerCardToPlay = 4;
            }
            else if ( event.getSource() == humanLabels[5] )
            {
                playerCardToPlay = 5;
            }
            else if ( event.getSource() == humanLabels[6] )
            {
                playerCardToPlay = 6;
            }
            else if ( event.getSource() == humanLabels[7] )
            {
                playerCardToPlay = 7;
            }
            else if ( event.getSource() == humanLabels[8] )
            {
                playerCardToPlay = 8;
            }
            else if ( event.getSource() == humanLabels[9] )
            {
                playerCardToPlay = 9;
            }
            else if ( event.getSource() == humanLabels[10] )
            {
                playerCardToPlay = 10;
            }
            else if ( event.getSource() == humanLabels[11] )
            {
                playerCardToPlay = 11;
            }
            else if ( event.getSource() == humanLabels[12] )
            {
                playerCardToPlay = 12;
            }
            else if ( event.getSource() == humanLabels[13] )
            {
                playerCardToPlay = 13;
            }
            else if ( event.getSource() == humanLabels[14] )
            {
                playerCardToPlay = 14;
            }
            else if ( event.getSource() == humanLabels[15] )
            {
                playerCardToPlay = 15;
            }
            else if ( event.getSource() == humanLabels[16] )
            {
                playerCardToPlay = 16;
            }
            else if ( event.getSource() == humanLabels[17] )
            {
                playerCardToPlay = 17;
            }
            else if ( event.getSource() == humanLabels[18] )
            {
                playerCardToPlay = 18;
            }
            else if ( event.getSource() == humanLabels[19] )
            {
                playerCardToPlay = 19;
            }
            else if ( event.getSource() == humanLabels[20] )
            {
                playerCardToPlay = 20;
            }
            else if ( event.getSource() == humanLabels[21] )
            {
                playerCardToPlay = 21;
            }
            else if ( event.getSource() == humanLabels[22] )
            {
                playerCardToPlay = 22;
            }
            else if ( event.getSource() == playedCardLabels[0] )
            {
                if ( playCard(1, 0) )
                {
                    noMoves[1] = 0;
                    currentPlayer = 0;
                    if (!checkForEnd())
                    {
                        computersPlay(highCardGame.getHand(currentPlayer));
                    }
                }
                
            }
            else if ( event.getSource() == playedCardLabels[1] )
            {
                if ( playCard(1, 1) )
                {
                    noMoves[1] = 0;
                    currentPlayer = 0;
                    if (!checkForEnd())
                    {
                        computersPlay(highCardGame.getHand(currentPlayer));
                    }
                }
            }
            else if ( event.getSource() == cannotPlayButton )
            {
                if ( currentPlayer == 1 )
                {
                    cannotPlayCount[1]++;
                    noMoves[1]++;
                    updateHumanPanel();
                    currentPlayer = 0;
                    if (!checkForEnd())
                    {
                        computersPlay(highCardGame.getHand(currentPlayer));
                    }
                }
            }
        }

        public void mousePressed (MouseEvent e)
        {
        }

        public void mouseReleased (MouseEvent e)
        {
        }

        public void mouseEntered (MouseEvent e)
        {
        }

        public void mouseExited (MouseEvent e)
        {
        }
    }

    public static void computersPlay (Hand hand)
    {
        int k;
        int stackTopCardVals[] = { -1, -1 };
        
        hand.sortByVal();//sorts hand lowest to highest

//        for ( int i = 0; i < NUM_CARDS_PER_HAND; i++ )
//        {
//            System.out.println(hand.inspectCard(i));
//        }
        for (k = 0; k < stackTopCardVals.length; k++)
        {
            stackTopCardVals[k] = Card.valueAsInt(cardStacks[k].inspectCard(
                cardStacks[k].getNumCards() - 1));
        }
        
        int stackToBePlayed = testCard(stackTopCardVals, hand);
        
        if ( stackToBePlayed > -1)
        {
            cardStacks[stackToBePlayed].takeCard(hand.playCard(playerCardToPlay));
            noMoves[0] = 0;
            updatePlayArea();
        }
        else
        {
            cannotPlayCount[0]++;
            noMoves[0]++;
        }
        highCardGame.getHand(0).takeCard(highCardGame.getCardFromDeck());

        currentPlayer = 1;
        checkForEnd();
    }

    public static int testCard (int[] stackTopCardVals, Hand hand)
    {
        for (int l = 0; l < stackTopCardVals.length; l++)
        {
            for ( int k = 0; k < hand.getNumCards(); k++ )
            {
                int cardVal = Card.valueAsInt(hand.inspectCard(k));
                if ( Math.abs(cardVal - stackTopCardVals[l]) == 1 )
                {
                    playerCardToPlay = k;
                    return l;
                }
            }
        }
        
        return -1;
    }

    public static boolean playCard (int player, int stack)
    {
        if ( (Card.valueAsInt(cardStacks[stack].inspectCard(cardStacks[stack].getNumCards() - 1))
                - Card.valueAsInt(highCardGame.getHand(player).inspectCard(playerCardToPlay)) == 1)
                || (Card.valueAsInt(cardStacks[stack].inspectCard(cardStacks[stack].getNumCards() - 1))
                - Card.valueAsInt(highCardGame.getHand(player).inspectCard(playerCardToPlay)) == -1) )
        {
            cardStacks[stack].takeCard(highCardGame.getHand(player).playCard(playerCardToPlay));
            updatePlayArea();
            if ( player != 0 )
            {
                updateHumanPanel();
            }
        }
        else
        {
            if ( player != 0 )
            {
                JOptionPane.showMessageDialog(null, "Not a Valid Play", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
        return true;
    }
    
    public static boolean checkForEnd()
    {
        if ( highCardGame.getNumCardsRemainingInDeck() == 0) 
                //|| (noMoves[0] > 3 || noMoves[1] > 3))
        {
            if ( cannotPlayCount[0] > cannotPlayCount[1] )
            {
                JOptionPane.showMessageDialog(null, "Human Wins Game!\nThanks for Playing");
            }
            else if ( cannotPlayCount[0] < cannotPlayCount[1] )
            {
                JOptionPane.showMessageDialog(null, "Computer Wins Game!\nThanks for Playing");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Tie Game!\nThanks for Playing");
            }
            System.out.println("computer card count: " 
                    + highCardGame.getHand(0).getNumCards() 
                    + "\nHuman card count: " 
                    + highCardGame.getHand(1).getNumCards());
        }
        return false;
    }
}
