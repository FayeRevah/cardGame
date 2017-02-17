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


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Counter extends JPanel
{
	private Timer timer;
	public static final int INTERVAL = 1000; //1 second interval for timer

	private JButton startButton; //starts timer from last value
        private JButton stopButton; //stops timer
        private JButton resetButton; //resets timer to 0
	private JLabel timeLabel; //label for the time string 
	private JPanel timerPanel; //panel for time label
        private JPanel buttonPanel; //panel for start, stop and reset buttons

	private int sec;  	//each timer tick is a millisecond
        private int minutes = 0; 
        private int hours = 0;
        private int seconds = 0;	
	private String timeText; //string value of hh:mm:ss
        
        private boolean controlsOnOff;

	public Counter()
	{
            this.controlsOnOff = true;
		sec = 0;  //starts off with clock at 0
		
		timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timeLabel = new JLabel(timeText, SwingConstants.CENTER);

		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		resetButton = new JButton("Reset");

                timer = new Timer(INTERVAL, new TimerIncrementListener());
		startButton.addActionListener(new StartListener());
		stopButton.addActionListener(new StopListener());
		resetButton.addActionListener(new ResetListener());
	}

	public Counter(boolean controlsOnOff)
	{
            this.controlsOnOff = controlsOnOff;
		sec = 0;  //starts off with clock at 0
		
		timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timeLabel = new JLabel(timeText, SwingConstants.CENTER);

		//startButton = new JButton("Start");
		//stopButton = new JButton("Stop");
		//resetButton = new JButton("Reset");

                timer = new Timer(INTERVAL, new TimerIncrementListener());
		//startButton.addActionListener(new StartListener());
		//stopButton.addActionListener(new StopListener());
		//resetButton.addActionListener(new ResetListener());
	}
        
        //ActionListener class for reset button
        //if the stop button has been pressed and the timer pauses, timer resets to 0 and remains paused
        //if the timer is in session, timer resets but continues counting from 0
        public class ResetListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e){
				sec = 0;
                                hours = minutes = seconds = 0;
				timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				timeLabel.setText(timeText);
			}
        }

        //ActionListener for start button. Starts the timer from its last time.
        public class StartListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                timer.start();
            }
        }
        
        //stops the timer and saves the time
        public class StopListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                timer.stop();
            }
        }
        
        //when the timer increments every millisecond, the text on the timerLabel is updated
        public class TimerIncrementListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e) 
            {
                sec++;
		seconds = sec % 60;
                minutes = (sec / 60) % 60;
                hours = sec / 3600;
                timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		timeLabel.setText(timeText);
            }
        }
        
	public void startCounter()
	{
		timerPanel = new JPanel();
                timerPanel.setLayout(new BorderLayout());
                timerPanel.add(timeLabel, BorderLayout.CENTER);
                
                add(timerPanel, BorderLayout.CENTER);
                
                if (controlsOnOff)
                {
                    buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(resetButton);
                add(buttonPanel, BorderLayout.SOUTH);
                }
		
		//setLayout(new BorderLayout());
		
	}

	public void activateTimer()
        {
            timer.start();
        }


}//end of public class

