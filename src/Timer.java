
import javax.swing.JLabel;

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

public class Timer extends Thread {

    private Counter counterObject;
    private JLabel updateLabel;
    private boolean doProcess = true;
    public static final int PAUSE = 1000;

    public Timer() {
        doProcess = true;
        counterObject = ctr;
        updateLabel = lbl;
    }

    public void run() {
        //countObject.increment();
        while (doProcess) {

            gameCounter.increment();
            System.out.println(gameCounter.toString());

            timerText.setText(gameCounter.toString());
            this.repaintTimer();
            doNothing(PAUSE);
        }
        System.out.println(gameCounter.toString());

    }

    public void repaintTimer() {
        timerText.repaint();
        table.pnlTimerText.repaint();

    }

    public void reset() {
        doProcess = true;
    }

    public void kill() {
        doProcess = false;
        this.repaintTimer();
    }

    public void doNothing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Unexpected interrupt");
            System.exit(0);
        }
    }
}
