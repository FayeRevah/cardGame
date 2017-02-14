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
    static int roundsPlayed = 0;
    static Hand[] playerPlays = new Hand[NUM_PLAYERS];
    static int [] cannotPlayCount = new int[NUM_PLAYERS];
    
    public static void main(String[] args)
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
        
        int playOrNot = JOptionPane.showConfirmDialog(null, "Ready to play War?", "", JOptionPane.YES_NO_OPTION);
        if (playOrNot == JOptionPane.YES_OPTION)
        {
            buildPanels();
        }  
    }
    
    public static void buildPanels()
    {
        int k;
        Icon tempIcon;
        
        CardClickListener clickListener = new CardClickListener();
        
        for (k = 0; k < NUM_CARDS_PER_HAND; k++)
        {
            computerLabels[k] = new JLabel( GUICard.getBackCardIcon());
            if (highCardGame.getHand(1).inspectCard(k) == null)
                tempIcon = GUICard.getBlankIcon();
            else
                tempIcon = GUICard.getIcon(highCardGame.getHand(1).inspectCard(k));
            humanLabels[k] = new JLabel( tempIcon );
            humanLabels[k].addMouseListener(clickListener);   
        }

        for (k = 0; k < NUM_PLAYERS; k++)
        {
            playedCardLabels[k] = new JLabel( GUICard.getBlankIcon(),
                    JLabel.CENTER );
            playedCardLabels[k].addMouseListener(clickListener);
            if ( k % NUM_PLAYERS == 0 )
            {
                playLabelText[k] = new JLabel( "Computer", JLabel.CENTER );
            }
            else
            {
                String temp = "Player " + k;
                playLabelText[k] = new JLabel( temp, JLabel.CENTER );
            }
            playerPlays[k] = new Hand();
        }
        
        timerLabel = new JLabel();
        cannotPlayButton = new JButton("I cannot play");

        // ADD LABELS TO PANELS -----------------------------------------
        for (k = 0; k < NUM_CARDS_PER_HAND; k++)
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
        
//        for (k = 0; k < NUM_PLAYERS; k++)
//            myCardTable.pnlPlayArea.add(playedCardLabels[k]);
//        for (k = 0; k < NUM_PLAYERS; k++)
//            myCardTable.pnlPlayArea.add(playLabelText[k]);
        myCardTable.setVisible(true);
        myCardTable.repaint();
        
    }
    
    public static void removeCard( JLabel[] labels, int removed )
    {
        for (int i = removed; i < labels.length; i++)
        {
            if (i == labels.length-1)
            {
                labels[i] = new JLabel(GUICard.getBlankIcon());
            }
            else
            {
                labels[i] = labels[i+1];
            }            
        }
    }
    
    public static void updatePanels()
    {
        myCardTable.pnlComputerHand.removeAll();
        myCardTable.pnlHumanHand.removeAll();
        for (int i = 0; i < NUM_CARDS_PER_HAND; i ++)
        {
            myCardTable.pnlComputerHand.add(computerLabels[i]);
            myCardTable.pnlHumanHand.add(humanLabels[i]);
        }
        
        myCardTable.pnlComputerHand.revalidate();
        myCardTable.pnlHumanHand.revalidate();
    }
    
    public static class CardClickListener implements MouseListener
    {
        Card humanCard;
        Card computerCard;
        
        public void mouseClicked(MouseEvent event)
        {
            if (event.getSource() == humanLabels[0])
            {
                humanCard = highCardGame.playCard(1, 0);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 0);
                removeCard(computerLabels, 0);
                updatePanels();
                //System.out.println(" Card 0 ");
                
            }
            else if (event.getSource() == humanLabels[1])
            {
                humanCard = highCardGame.playCard(1, 1);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 1);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == humanLabels[2])
            {
                humanCard = highCardGame.playCard(1, 2);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 2);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == humanLabels[3])
            {
                humanCard = highCardGame.playCard(1, 3);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 3);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == humanLabels[4])
            {
                humanCard = highCardGame.playCard(1, 4);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 4);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == humanLabels[5])
            {
                humanCard = highCardGame.playCard(1, 6);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 5);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == humanLabels[6])
            {
                humanCard = highCardGame.playCard(1, 6);
                computerCard = highCardGame.playCard(0, computersPlay(highCardGame.getHand(0)));
                removeCard(humanLabels, 6);
                removeCard(computerLabels, 0);
                updatePanels();
            }
            else if (event.getSource() == playedCardLabels[0])
            {
                //
            }
            else if (event.getSource() == playedCardLabels[1])
            {
                //
            }
            
            myCardTable.pnlPlayArea.removeAll();
            playedCardLabels[0] = new JLabel(GUICard.getIcon(computerCard));
            playedCardLabels[1] = new JLabel(GUICard.getIcon(humanCard));
            myCardTable.pnlPlayArea.add(playedCardLabels[0]);
            myCardTable.pnlPlayArea.add(playedCardLabels[1]);
            myCardTable.pnlPlayArea.add(playLabelText[0]);
            myCardTable.pnlPlayArea.add(playLabelText[1]);
            myCardTable.pnlPlayArea.revalidate();
            
            if (Card.valueAsInt(computerCard) > Card.valueAsInt(humanCard))
            {
                JOptionPane.showMessageDialog(null, ("Computer Wins Round!"));
                playerPlays[0].takeCard(computerCard);
                playerPlays[0].takeCard(humanCard);
            }
            else if (Card.valueAsInt(computerCard) == Card.valueAsInt(humanCard))
            {
                JOptionPane.showMessageDialog(null, ("Its a Draw No One Wins Round!"));
            }
            else 
            {
                JOptionPane.showMessageDialog(null, ("Player 1 Wins Round!"));
                playerPlays[1].takeCard(computerCard);
                playerPlays[1].takeCard(humanCard);
            }
            roundsPlayed++;
            
            if (roundsPlayed == NUM_CARDS_PER_HAND)
            {
                if (playerPlays[0].getNumCards() > playerPlays[1].getNumCards())
                    JOptionPane.showMessageDialog(null, ("Computer Wins Game!\nThanks for Playing"));
                else if (playerPlays[0].getNumCards() > playerPlays[1].getNumCards())
                    JOptionPane.showMessageDialog(null, ("Tie Game!\nThanks for Playing"));
                else
                    JOptionPane.showMessageDialog(null, ("Player 1 Wins Game!\nThanks for Playing"));
            }
        }

        public void mousePressed(MouseEvent e){}

        public void mouseReleased(MouseEvent e){}
        
        public void mouseEntered(MouseEvent e){}
        
        public void mouseExited(MouseEvent e){}
    }
    
    public static int computersPlay(Hand hand)
    {
        hand.sortByVal();//sorts hand lowest to highest
        
        int numCards = hand.getNumCards(); 
        int middleCard = (numCards - 1) / 2; //middle index of hand
        
        //if there are two remaining cards, returns the lowest
        //if one remaining card, returns it.
        if(numCards == 1 || numCards == 2)
            return 0;
        if(numCards == 0)
            return -1;
        
        return middleCard;  
    }
    
}
