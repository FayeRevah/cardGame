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
*       Card, Hand, Deck, GUICard, CardTable extends JFrame, CardGameFramework
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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Assignment6
{    

    static CardTable myCardTable;
    static int NUM_CARDS_PER_HAND = 7;
    static int NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
    static JLabel timerLabel;
    static JButton cannotPlayButton;
    static CardGameFramework highCardGame;
    static boolean gameInPlay = false;
    static Hand[] cardStacks = new Hand[2];
    static int[] cannotPlayCount = new int[NUM_PLAYERS];
    static int playerCardToPlay;
    
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
        }        
    }
    
    public static void buildPanels ()
    {
        int k;
        Icon tempIcon;
        
        CardClickListener clickListener = new CardClickListener();
        
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
            playedCardLabels[k] = new JLabel(GUICard.getBlankIcon(),
                    JLabel.CENTER);
            playedCardLabels[k].addMouseListener(clickListener);
            if ( k % NUM_PLAYERS == 0 )
            {
                playLabelText[k] = new JLabel("Computer", JLabel.CENTER);
            }
            else
            {
                String temp = "Player " + k;
                playLabelText[k] = new JLabel(temp, JLabel.CENTER);
            }
            cannotPlayCount[k] = 0;
        }
        
        cardStacks[0] = new Hand();
        cardStacks[1] = new Hand();
        playerCardToPlay = 0;
        
        timerLabel = new JLabel();
        cannotPlayButton = new JButton("I cannot play");
        cannotPlayButton.addMouseListener(clickListener);

        // ADD LABELS TO PANELS -----------------------------------------
        for ( k = 0; k < NUM_CARDS_PER_HAND; k++ )
        {
            myCardTable.pnlComputerHand.add(computerLabels[k]);
            myCardTable.pnlHumanHand.add(humanLabels[k]);
        }
        
        myCardTable.pnlPlayArea.add(playedCardLabels[0]);
        myCardTable.pnlPlayArea.add(timerLabel);
        myCardTable.pnlPlayArea.add(playedCardLabels[1]);
        myCardTable.pnlPlayArea.add(playLabelText[0]);
        myCardTable.pnlPlayArea.add(cannotPlayButton);
        myCardTable.pnlPlayArea.add(playLabelText[1]);

        myCardTable.setVisible(true);
        myCardTable.repaint();
        
    }
    
    public static void replaceCard (int player, int removed)
    {
        
        highCardGame.getHand(player).takeCard(highCardGame.getCardFromDeck());
//        for ( int i = removed; i < labels.length; i++ )
//        {
//            if ( i == labels.length - 1 )
//            {
//                labels[i] = new JLabel(GUICard.getBlankIcon());
//            }
//            else
//            {
//                labels[i] = labels[i + 1];
//            }            
//        }
    }
    
    public static void updatePanels ()
    {
        //myCardTable.pnlComputerHand.removeAll();
        myCardTable.pnlHumanHand.removeAll();
        for ( int i = 0; i < NUM_CARDS_PER_HAND; i++ )
        {
            //myCardTable.pnlComputerHand.add(computerLabels[i]);
            myCardTable.pnlHumanHand.add(humanLabels[i]);
        }
        
        //myCardTable.pnlComputerHand.revalidate();
        myCardTable.pnlHumanHand.revalidate();
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
            else if ( event.getSource() == playedCardLabels[0] )
            {
                System.out.println("played card label 0");
            }
            else if ( event.getSource() == playedCardLabels[1] )
            {
                System.out.println("played card label 1");
            }
            else if ( event.getSource() == cannotPlayButton)
            {
                System.out.println("I Cannot Play");
            }

//            myCardTable.pnlPlayArea.removeAll();
//            playedCardLabels[0] = new JLabel(GUICard.getIcon(computerCard));
//            playedCardLabels[1] = new JLabel(GUICard.getIcon(humanCard));
//            myCardTable.pnlPlayArea.add(playedCardLabels[0]);
//            myCardTable.pnlPlayArea.add(playedCardLabels[1]);
//            myCardTable.pnlPlayArea.add(playLabelText[0]);
//            myCardTable.pnlPlayArea.add(playLabelText[1]);
//            myCardTable.pnlPlayArea.revalidate();
//            
//            if (Card.valueAsInt(computerCard) > Card.valueAsInt(humanCard))
//            {
//                JOptionPane.showMessageDialog(null, ("Computer Wins Round!"));
//                playerPlays[0].takeCard(computerCard);
//                playerPlays[0].takeCard(humanCard);
//            }
//            else if (Card.valueAsInt(computerCard) == Card.valueAsInt(humanCard))
//            {
//                JOptionPane.showMessageDialog(null, ("Its a Draw No One Wins Round!"));
//            }
//            else 
//            {
//                JOptionPane.showMessageDialog(null, ("Player 1 Wins Round!"));
//                playerPlays[1].takeCard(computerCard);
//                playerPlays[1].takeCard(humanCard);
//            }
//            roundsPlayed++;
//            
//            if (roundsPlayed == NUM_CARDS_PER_HAND)
//            {
//                if (playerPlays[0].getNumCards() > playerPlays[1].getNumCards())
//                    JOptionPane.showMessageDialog(null, ("Computer Wins Game!\nThanks for Playing"));
//                else if (playerPlays[0].getNumCards() > playerPlays[1].getNumCards())
//                    JOptionPane.showMessageDialog(null, ("Tie Game!\nThanks for Playing"));
//                else
//                    JOptionPane.showMessageDialog(null, ("Player 1 Wins Game!\nThanks for Playing"));
//            }
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
    
    public static int computersPlay (Hand hand)
    {
        hand.sortByVal();//sorts hand lowest to highest
        
        int numCards = hand.getNumCards();        
        int middleCard = (numCards - 1) / 2; //middle index of hand

        //if there are two remaining cards, returns the lowest
        //if one remaining card, returns it.
        if ( numCards == 1 || numCards == 2 )
        {
            return 0;
        }
        if ( numCards == 0 )
        {
            return -1;
        }
        
        return middleCard;        
    }
    
    public void playCard(int player, int stack)
    {
        if ( (Card.valueAsInt(cardStacks[0].inspectCard(cardStacks[0].getNumCards()-1))
                        - Card.valueAsInt(highCardGame.getHand(player).inspectCard(playerCardToPlay)) == 1) || 
                        (Card.valueAsInt(cardStacks[0].inspectCard(cardStacks[0].getNumCards()-1))
                        - Card.valueAsInt(highCardGame.getHand(player).inspectCard(stack)) == -1 ))
                        {
                            cardStacks[0].takeCard(highCardGame.getHand(player).playCard(playerCardToPlay));
                            replaceCard(highCardGame.getHand(player), player);
                        }
                else
                {
                    JOptionPane.showMessageDialog(null, "Not a Valid Play", "Error", JOptionPane.ERROR_MESSAGE);
                }
    }
}
